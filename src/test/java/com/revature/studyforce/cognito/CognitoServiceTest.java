package com.revature.studyforce.cognito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;


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
    @InjectMocks CognitoService cognitoService;

    @Test
    void whenGetUserEmailFromUserPool_callCognitoClient_retrieveUserEmail() {
        String username = "test";
        String email = "test@test.com";
        AttributeType emailAttribute = AttributeType.builder()
                .name("email")
                .value(email)
                .build();
        AdminGetUserResponse response =  AdminGetUserResponse.builder()
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

        String actual = cognitoService.getAuthorityFromUserPool(username);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("ADMIN",actual);


    }
    @Test
    void whenCallCognitoClient_retrieveUserRole_isNull_returnRoleUser() {
        String username = "test";
        AdminGetUserResponse response =  AdminGetUserResponse.builder()
                .username(username)
                .build();
        Mockito.when(cognitoClient.adminGetUser(org.mockito.ArgumentMatchers.isA(AdminGetUserRequest.class))).thenReturn(response);

        String actual = cognitoService.getAuthorityFromUserPool(username);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("USER",actual);


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
}