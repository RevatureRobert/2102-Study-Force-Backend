package com.revature.studyforce.cognito;
import com.revature.studyforce.user.dto.BulkCreateUsersDTO;
import com.revature.studyforce.user.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static software.amazon.awssdk.services.cognitoidentityprovider.model.UserImportJobStatusType.*;

/**
 * Service Layer for Cognito API calls
 * @author Nick Wickham
 */

@Service
public class CognitoService {


    private final CognitoIdentityProviderClient cognitoClient;

    private static final String userPool = "us-east-1_RVt7o8200";
    private static final String cognitoLoggingArn = "arn:aws:iam::967240801169:role/enterprise-cognito-log-role";
    private static final String cognitoUserRole = "custom:role";

    @Autowired
    public CognitoService(@Lazy CognitoIdentityProviderClient cognitoIdentityProviderClient) {
        this.cognitoClient = cognitoIdentityProviderClient;
    }


    /**
     * Retrieves email from UserPool
     * @param username UserPool username
     * @return email of user which is username of Spring
     */
    public String getUserEmailFromUserPool(String username){
        AdminGetUserResponse response = cognitoClient.adminGetUser(AdminGetUserRequest.builder().userPoolId(userPool).username(username).build());
        Optional<String> email = response
                .userAttributes()
                .stream()
                .filter(attributeType -> attributeType.name().equals("email"))
                .map(AttributeType::value)
                .findFirst();

        return email.orElse("NO_EMAIL_FOUND");
    }

    /**
     * Retrieves authority level from UserPool
     * @param username UserPool username
     * @return Authority level as String
     */
    public Authority getAuthorityFromUserPool(String username){
        AdminGetUserResponse response = cognitoClient.adminGetUser(
                AdminGetUserRequest
                        .builder()
                        .userPoolId(userPool)
                        .username(username)
                        .build()
        );
        Optional<String> role = response
                .userAttributes()
                .stream()
                .filter(attributeType -> attributeType.name().equals(cognitoUserRole))
                .map(AttributeType::value)
                .findFirst();

        return Authority.valueOf(role.orElse(updateAuthority(username,Authority.USER)));

    }
    /**
     *Retrieves User name from UserPool
     * @param username UserPool username
     * @return User name as String
     */
    public String getUserNameFromUserPool(String username) {
        AdminGetUserResponse response = cognitoClient.adminGetUser(
                AdminGetUserRequest
                .builder()
                .userPoolId(userPool)
                .username(username)
                .build()
        );

        Optional<String> userName = response
                .userAttributes()
                .stream()
                .filter(attributeType -> attributeType.name().equals("name"))
                .map(AttributeType::value)
                .findFirst();


        return userName.orElse("NO_NAME_FOUND");

    }

    /**
     * Updates a user's authority
     * @param email The email of the user being updated
     * @param authority The user's new authority
     * @return Returns the user's new authority
     */
    public String updateAuthority(String email, Authority authority){
        cognitoClient.adminUpdateUserAttributes(AdminUpdateUserAttributesRequest.builder()
                .userPoolId(userPool)
                .userAttributes(AttributeType
                        .builder().name(cognitoUserRole)
                        .value(authority.authorityName).build())
                .username(email).build());
        return authority.authorityName;
    }

    /**
     * Creates a group of new users
     * @param usersFromCsv list of emails to create accounts for.
     * @return message describing outcome status (SUCCEEDED, FAILED, PENDING, STOPPED)
     *          TODO: A list of failed lines can be viewed in CloudWatch and the relevant API calls should be brought in
     *                on a second sprint to improve the bulk creation process.
     *
     * @throws IOException If the received emails list is malformed.
     * @throws InterruptedException If the bulk creation process is interrupted, possible with poor internet/large jobs.
     */
    public String bulkCreateUsers(List<BulkCreateUsersDTO> usersFromCsv) throws IOException, InterruptedException {
        UserImportJobType userImportJob = createUserImportJob();
        final String activeJobId = userImportJob.jobId();
        final String activeJobFilePath = "./import-" + activeJobId + ".csv";

        convertStudyForceCsvToCognitoCsv(usersFromCsv,activeJobFilePath);
        final boolean isUploaded = postCsvToCognito(userImportJob.preSignedUrl(),activeJobFilePath);
        if(!isUploaded)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Failure to upload Csv to Cognito, contact your AWS services administrator for further info");
        else
            userImportJob = runBulkJobAndAwaitCompletion(activeJobId);

        if(userImportJob.status() != SUCCEEDED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, userJobMessage(userImportJob));

        if(!Files.deleteIfExists(Paths.get(activeJobFilePath)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FILE CLEANUP FAILURE, CONTACT ADMIN IMMEDIATELY");

      return userJobMessage(userImportJob);
    }

    /**
     * Starts the bulk job, awaits it's completion, and returns an updated reference to the completed job.
     * @param activeJobId the job id of the job to run.
     * @return a UserImportJobType (AWS SDK DTO) containing information about the completed job.
     * @throws InterruptedException if the task is interrupted
     */
    protected UserImportJobType runBulkJobAndAwaitCompletion(String activeJobId) throws InterruptedException {
        startUserImportJob(activeJobId);
        UserImportJobType activeImportJob = getExistingUserImportJobDetails(activeJobId);
        if (activeImportJob.completionMessage() == null)
             waitUntilCompleted(activeImportJob);

        return getExistingUserImportJobDetails(activeJobId);
    }

    /**
     * Makes an external call to AWS every two seconds to check job status.
     * @param activeImportJob the job being inspected.
     * @throws InterruptedException if the task is interrupted
     */
    protected void waitUntilCompleted(UserImportJobType activeImportJob) throws InterruptedException {
        activeImportJob = getExistingUserImportJobDetails(activeImportJob.jobId());
        if (activeImportJob.completionMessage() == null)
        {
            Thread.sleep(2000);
            waitUntilCompleted(activeImportJob);
        }

    }

    /**
     * Extracts and simply formats job completion message.
     * @param userImportJob the job from which the message is extracted
     * @return the formatted message.
     */
    protected String userJobMessage(UserImportJobType userImportJob) {
        return "Status: " + userImportJob.status() +"\nMessage: " +userImportJob.completionMessage();
    }

    /**
     * Send a request to AWS to begin an import job
     * @param activeJobId the id of the job to start.
     */
    protected void startUserImportJob(String activeJobId) {
        try {
            cognitoClient.startUserImportJob(
                    StartUserImportJobRequest.builder()
                    .userPoolId(userPool)
                    .jobId(activeJobId)
                    .build()
            );
        } catch (AwsServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error reported from AWS services, contact your administrator");
        } catch (SdkClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error with credentials, contact your administrator.");
        }
    }

    /**
     * Makes an external call to AWS to get user import job details.
     * @param activeJobId the id of the job.
     * @return a UserImportJobType (AWS SDK DTO) containing information about the specified job.
     */
    protected UserImportJobType getExistingUserImportJobDetails(String activeJobId) {
        return cognitoClient.describeUserImportJob(
                DescribeUserImportJobRequest.builder()
                        .userPoolId(userPool)
                        .jobId(activeJobId)
                        .build()
        ).userImportJob();
    }

    /**
     * Converts a list of emails to a Cognito complaint csv.
     * @param usersFromCsv the list of users to be processed
     * @param fileName output path for completed csv.
     */
    private void convertStudyForceCsvToCognitoCsv(List<BulkCreateUsersDTO> usersFromCsv, String fileName) {
        try(FileWriter writer = new FileWriter(fileName)){
            writer.append("name,email,email_verified,phone_number_verified,given_name,family_name,profile,picture,website,middle_name,nickname,gender,birthdate,zoneinfo,locale,phone_number,preferred_username,address,updated_at,custom:userId,custom:role,cognito:mfa_enabled,cognito:username\n");
            usersFromCsv.forEach(email -> {
                try {
                    writer.append(createCognitoCsvLine(email.getEmail()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            writer.flush();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Converts a single email to a single line in the Cognito csv.
     * @param email email being processed.
     * @return string to be inserted into the csv.
     */
    private String createCognitoCsvLine(String email){
        return "Default Name,"+email+",TRUE,FALSE,,,,,,,,,,,,,,,,,USER,FALSE,"+email;
    }

    /**
     * Uploads user csv to AWS.
     * @param preSignedUrl url for the csv to be posted to, 15 minute lifetime.
     * @param filePath location of the csv to be sent.
     * @return true if AWS reports upload as successful, else false.
     * @throws IOException if system is restricted from performing curl.
     */
    protected boolean postCsvToCognito(String preSignedUrl, String filePath) throws IOException{
        String curl = "curl.exe -v -T " + filePath + " -H x-amz-server-side-encryption:aws:kms " + preSignedUrl;
        boolean isWeAreFineReceived = false;
        Process curlAttempt = Runtime.getRuntime().exec(curl);
        InputStream in = curlAttempt.getInputStream();
        InputStream err = curlAttempt.getErrorStream();
        try(
                BufferedReader input
                        = new BufferedReader(new InputStreamReader(in));
                BufferedReader error
                        = new BufferedReader(new InputStreamReader(err))
        ) {
            while (curlAttempt.isAlive()) {
                while (input.ready()) {
                    if(input.readLine().contains("We are completely uploaded and fine"))
                        isWeAreFineReceived = true;
                }
                while (error.ready()) {
                    if(error.readLine().contains("We are completely uploaded and fine"))
                        isWeAreFineReceived = true;
                }
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to send CSV to Cognito.");
        }
        return isWeAreFineReceived;
    }

    /**
     * Sends request preparing AWS for a user import job.
     * @return a UserImportJobType (AWS SDK DTO) containing information about the created job.
     */
    public UserImportJobType createUserImportJob() {
        return cognitoClient.createUserImportJob(
                CreateUserImportJobRequest.builder()
                        .userPoolId(userPool)
                        .jobName("descriptiveJobName" + System.currentTimeMillis())
                        .cloudWatchLogsRoleArn(cognitoLoggingArn)
                        .build()
        ).userImportJob();
    }
}
