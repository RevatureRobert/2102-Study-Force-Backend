package com.revature.studyforce.cognito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import java.util.Optional;

/**
* Service Layer for Cognito API calls
* @author Nick Wickham
*/

@Service
public class CognitoService {

   @Autowired
   private final CognitoIdentityProviderClient cognitoClient = cognitoIdentityProviderClient();
   private static final String userPool = "us-east-1_RVt7o8200";

   @Bean
   CognitoIdentityProviderClient cognitoIdentityProviderClient(){
       return CognitoIdentityProviderClient.builder()
               .region(Region.US_EAST_1)
               .build();
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
   public String getAuthorityFromUserPool(String username){
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
               .filter(attributeType -> attributeType.name().equals("custom:role"))
               .map(AttributeType::value)
               .findFirst();


       return role.orElse(grantBasicAuthorityOnFirstLogin(username));

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
    *Called when no role attribute (custom:role) is found in UserPool
    * and assigns lowest authority role to that user.
    * @param username UserPool username
    * @return "User"
    */
   private String grantBasicAuthorityOnFirstLogin(String username) {
       cognitoClient.adminUpdateUserAttributes(AdminUpdateUserAttributesRequest.builder()
               .userPoolId(userPool)
               .userAttributes(AttributeType
                       .builder().name("custom:role")
                       .value("USER").build())
               .username(username).build());

       return "USER";

   }
}
