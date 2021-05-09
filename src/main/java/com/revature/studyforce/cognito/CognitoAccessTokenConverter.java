//package com.revature.studyforce.cognito;
//import com.revature.studyforce.user.model.User;
//import com.revature.studyforce.user.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.stereotype.Component;
//import java.util.Map;
//
///**
// * Maps a valid Cognito access token to the Spring security context.
// * @author Nick Wickham
// */
//@Component
//@AllArgsConstructor
//public class CognitoAccessTokenConverter extends JwtAccessTokenConverter {
//    private static final String SPRING_AUTHORITIES = "authorities";
//    private static final String COGNITO_USERNAME = "username";
//    private static final String SPRING_USER_NAME = "user_name";
//
//    private final CognitoService cognitoService;
//    private final UserService userService;
//
//
//    /**
//     * Extracts token claims and calls Cognito Api to gather needed user information.
//     * then inserts the gathered information back into 'claims' in a form that the parent class {@link JwtAccessTokenConverter}
//     * can insert into the security context.
//     * @param claims extracted claims from valid jwt
//     * @return super.extractAuthentication(claims); to pass modified 'claims' into the next step of the authentication
//     * processing chain.
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
//        if (claims.containsKey(COGNITO_USERNAME)) {
//            String email = cognitoService.getUserEmailFromUserPool((String) claims.get(COGNITO_USERNAME));
//            String name = cognitoService.getUserNameFromUserPool((String) claims.get(COGNITO_USERNAME));
//            ((Map<String, String>) claims).put(SPRING_USER_NAME, email);
//            ((Map<String, String>) claims).put(SPRING_AUTHORITIES, cognitoService.getAuthorityFromUserPool((String) claims.get(COGNITO_USERNAME)));
//
//
//            //Needs to be replaced once RDS is set up.
//            replaceWithLambda(email, name);
//        }
//            return super.extractAuthentication(claims);
//    }
//
//    /**
//     * For every request, check if user is in the database, and if not insert them.
//     * This should be replaced with an AWS Lambda trigger that sends an INSERT query directly to the RDS upon
//     * user signup before production for increased efficiency.
//     * @param email email received from Cognito
//     * @param userName user_name received from Cognito
//     */
//    private void replaceWithLambda(String email,String userName) {
//        if(userService.getUserByEmail(email) ==null) userService.adminCreateUser(new User(email, userName));
//    }
//}
//
