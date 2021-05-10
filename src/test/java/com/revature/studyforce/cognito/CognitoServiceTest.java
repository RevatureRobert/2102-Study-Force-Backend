package com.revature.studyforce.cognito;

import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.io.IOException;


/**
 * Service Layer Testing {@link CognitoService}
 * @author Nick Wickham
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class CognitoServiceTest {

    @MockBean
    private CognitoIdentityProviderClient cognitoClient;


    @Autowired
    @InjectMocks
    CognitoService cognitoService;

    @Test
    void whenGetUserEmailFromUserPool_callCognitoClient_retrieveUserEmail() {
        String username = "test";
        String email = "test@test.com";
        AttributeType emailAttribute = AttributeType.builder()
                .name("email")
                .value(email)
                .build();
        AdminGetUserResponse response = AdminGetUserResponse.builder()
                .username(username)
                .userAttributes(emailAttribute)
                .build();
        Mockito.when(cognitoClient.adminGetUser(org.mockito.ArgumentMatchers.isA(AdminGetUserRequest.class))).thenReturn(response);

        String actual = cognitoService.getUserEmailFromUserPool("test");
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(email,actual);


    }

    @Test
    void whenGetAuthorityFromUserPool_callCognitoClient_retrieveUserRole() {
        String username = "test";
        String role = "ADMIN";
        AttributeType roleAttribute = AttributeType.builder()
                .name("custom:role")
                .value(role)
                .build();
        AdminGetUserResponse response =  AdminGetUserResponse.builder()
                .username(username)
                .userAttributes(roleAttribute)
                .build();
        Mockito.when(cognitoClient.adminGetUser(org.mockito.ArgumentMatchers.isA(AdminGetUserRequest.class))).thenReturn(response);

        Authority actual = cognitoService.getAuthorityFromUserPool(username);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(Authority.ADMIN,actual);


    }
    @Test
    void whenCallCognitoClient_retrieveUserRole_isNull_returnRoleUser() {
        String username = "test";
        AdminUpdateUserAttributesResponse innerResponse = AdminUpdateUserAttributesResponse.builder().build();
        AdminGetUserResponse response =  AdminGetUserResponse.builder()
                .username(username)
                .build();
        Mockito.when(cognitoClient.adminGetUser(org.mockito.ArgumentMatchers.isA(AdminGetUserRequest.class))).thenReturn(response);
        Mockito.when(cognitoClient.adminUpdateUserAttributes(ArgumentMatchers.isA(AdminUpdateUserAttributesRequest.class))).thenReturn(innerResponse);
        Authority actual = cognitoService.getAuthorityFromUserPool(username);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(Authority.USER,actual);


    }

    @Test
    void whenGetUserNameFromUserPool_callCognitoClient_retrieveUserName() {
        String username = "test";
        String name = "TEST";
        AttributeType nameAttribute = AttributeType.builder()
                .name("name")
                .value(name)
                .build();
        AdminGetUserResponse response =  AdminGetUserResponse.builder()
                .username(username)
                .userAttributes(nameAttribute)
                .build();
        Mockito.when(cognitoClient.adminGetUser(org.mockito.ArgumentMatchers.isA(AdminGetUserRequest.class))).thenReturn(response);

        String actual = cognitoService.getUserNameFromUserPool(username);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("TEST",actual);

    }
//    @Test
//    void whenBulkCreateUsers_hasInvalidInput_receiveFailedMessage() throws IOException {
//        String message = "Status: Succeeded\n" +
//                "Message: Import Job Completed Successfully.";
//        UserImportJobType testJob = UserImportJobType.builder()
//                .jobId("1234").cloudWatchLogsRoleArn("ARNie").status("Failed").preSignedUrl("/presignedurl").build();
//        CreateUserImportJobResponse testJobResponse = CreateUserImportJobResponse.builder().userImportJob(testJob).build();
//        Mockito.when(cognitoClient.createUserImportJob(ArgumentMatchers.isA(CreateUserImportJobRequest.class))).thenReturn(testJobResponse);
//        Mockito.when(cognitoService.postCsvToCognito(ArgumentMatchers.isA(String.class),ArgumentMatchers.isA(String.class))).thenReturn(true);
//
//
//
//
//    }

    @Test
    void whenCreateUserImportJob_callsCognitoClient_createUserJob(){
        UserImportJobType testJob = UserImportJobType.builder()
                .jobId("123").cloudWatchLogsRoleArn("ARNie").status("Created").preSignedUrl("/presignedurl").build();
        CreateUserImportJobResponse testJobResponse = CreateUserImportJobResponse.builder().userImportJob(testJob).build();
        Mockito.when(cognitoClient.createUserImportJob(ArgumentMatchers.isA(CreateUserImportJobRequest.class))).thenReturn(testJobResponse);
        UserImportJobType actual = cognitoService.createUserImportJob();
        Assertions.assertEquals("123",actual.jobId());
        Assertions.assertEquals("ARNie",actual.cloudWatchLogsRoleArn());
        Assertions.assertEquals(UserImportJobStatusType.CREATED,actual.status());
        Assertions.assertEquals("/presignedurl",actual.preSignedUrl());
        Assertions.assertNull(actual.completionDate());

    }
    @Test
    void whenGetExistingUserImportJobDetails_callsCogntioClient_returnJobDetails()
    {
        UserImportJobType testJob = UserImportJobType.builder()
                .jobId("123").cloudWatchLogsRoleArn("ARNie").status("Stopped").preSignedUrl("/expiredurl").build();
        DescribeUserImportJobResponse testJobResponse = DescribeUserImportJobResponse.builder().userImportJob(testJob).build();
        Mockito.when(cognitoClient.describeUserImportJob(ArgumentMatchers.isA(DescribeUserImportJobRequest.class))).thenReturn(testJobResponse);
        UserImportJobType actual = cognitoService.getExistingUserImportJobDetails("123");
        Assertions.assertEquals("123",actual.jobId());
        Assertions.assertEquals("ARNie",actual.cloudWatchLogsRoleArn());
        Assertions.assertEquals(UserImportJobStatusType.STOPPED,actual.status());
        Assertions.assertEquals("/expiredurl",actual.preSignedUrl());
        Assertions.assertNull(actual.completionDate());
    }

}
