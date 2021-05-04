package com.revature.studyforce.cognito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Optional;

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



    public String getUserEmailFromUserPool(String username){
        AdminGetUserResponse response = cognitoClient.adminGetUser(request-> request.username(username).userPoolId(userPool).build());
        Optional<String> email = response
                .userAttributes()
                .stream()
                .filter(attributeType -> attributeType.name().equals("email"))
                .map(AttributeType::value)
                .findFirst();

        return email.orElse("NO_EMAIL_FOUND");
    }

    public String getAuthorityFromUserPool(String username){
        AdminGetUserResponse response = cognitoClient.adminGetUser(request-> request.username(username).userPoolId(userPool).build());
        Optional<String> role = response
                .userAttributes()
                .stream()
                .filter(attributeType -> attributeType.name().equals("custom:role"))
                .map(AttributeType::value)
                .findFirst();


        return role.orElse(grantBasicAuthorityOnFirstLogin(username));

    }

    public String getUserNameFromUserPool(String username) {
        AdminGetUserResponse response = cognitoClient.adminGetUser(request-> request.username(username).userPoolId(userPool).build());
        Optional<String> userName = response
                .userAttributes()
                .stream()
                .filter(attributeType -> attributeType.name().equals("name"))
                .map(AttributeType::value)
                .findFirst();


        return userName.orElse("NO_NAME_FOUND");

    }

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
