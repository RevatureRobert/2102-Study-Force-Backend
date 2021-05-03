package com.revature.studyforce.cognito.service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

public class CognitoService {
    private final CognitoIdentityProviderClient cognitoClient;

    private static final String clientId = "${AWS_CLIENT_ID}";
    private static final String userPool = "${COGNITO_USER_POOL}";

    public CognitoService(){
    this.cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public String getUserEmailFromUserPool(CognitoIdentityProviderClient cognitoClient, String userPool, String username){
        AdminGetUserResponse response = cognitoClient.adminGetUser((request)-> request.username(username).userPoolId(userPool).build());
        return response.userAttributes().stream().filter(attributeType -> attributeType.name().equals("email")).map(AttributeType::value).toString();

    }


}
