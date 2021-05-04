package com.revature.studyforce.cognito;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class CognitoAccessTokenConverter extends JwtAccessTokenConverter {
    private static final String SPRING_AUTHORITIES = "authorities";
    private static final String COGNITO_USERNAME = "username";
    private static final String SPRING_USER_NAME = "user_name";

    private final CognitoService cognitoService;
    private final UserService userService;



    @SuppressWarnings("unchecked")
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        if (claims.containsKey(COGNITO_USERNAME)) {
            String email = cognitoService.getUserEmailFromUserPool((String) claims.get(COGNITO_USERNAME));
            String name = cognitoService.getUserNameFromUserPool((String) claims.get(COGNITO_USERNAME));
            ((Map<String, String>) claims).put(SPRING_USER_NAME, email);
            ((Map<String, String>) claims).put(SPRING_AUTHORITIES, cognitoService.getAuthorityFromUserPool((String) claims.get(COGNITO_USERNAME)));


            //Needs to be replaced once RDS is set up.
            replaceWithLambda(email, name);
        }
            return super.extractAuthentication(claims);
    }


    private void replaceWithLambda(String email,String userName) {
        if(userService.getUserByEmail(email) ==null) userService.adminCreateUser(new User(email, userName));
    }
}

