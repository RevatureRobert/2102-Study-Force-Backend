package com.revature.studyforce.configuration;

import com.revature.studyforce.cognito.service.CognitoService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolResponse;

import java.util.Map;

@Component
public class CognitoAccessTokenConverter extends JwtAccessTokenConverter {
    private static final String COGNITO_GROUPS = "cognito:groups";
    private static final String SPRING_AUTHORITIES = "authorities";
    private static final String COGNITO_USERNAME = "username";
    private static final String SPRING_USER_NAME = "user_name";



    @SuppressWarnings("unchecked")
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {


        if (claims.containsKey(COGNITO_GROUPS))
            ((Map<String, Object>) claims).put(SPRING_AUTHORITIES, claims.get(COGNITO_GROUPS));
        if (claims.containsKey(COGNITO_USERNAME))
            ((Map<String, Object>) claims).put(SPRING_USER_NAME, claims.get(COGNITO_USERNAME));
        return super.extractAuthentication(claims);
    }
}

