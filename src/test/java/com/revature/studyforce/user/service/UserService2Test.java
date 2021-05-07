package com.revature.studyforce.user.service;

import com.revature.studyforce.user.dto.*;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * testing several update methods from {@link UserService}
 *
 * @author Daniel Bernier
 */
@TestPropertySource(locations = "classpath:test-application.properties")
@SpringBootTest
class UserService2Test {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void whenUpdateUserName_thenReturnUpdatedUserDTO (){
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(1, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.USER, timestamp, timestamp);

        UserNameDTO userNameDTO = new UserNameDTO(1, "New Name");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenAnswer(u -> u.getArguments()[0]);

        UserDTO serviceUserDTO = userService.updateUserName(userNameDTO);

        Assertions.assertNotNull(serviceUserDTO);
        Assertions.assertEquals(1, serviceUserDTO.getUserId());
        Assertions.assertEquals("cool@gmail.com",serviceUserDTO.getEmail());
        Assertions.assertEquals("New Name", serviceUserDTO.getName());
        Assertions.assertTrue(serviceUserDTO.isActive());
        Assertions.assertTrue(serviceUserDTO.isSubscribedFlashcard());
        Assertions.assertFalse(serviceUserDTO.isSubscribedStacktrace());
        Assertions.assertEquals(Authority.USER, serviceUserDTO.getAuthority());
        Assertions.assertEquals(timestamp, serviceUserDTO.getRegistrationTime());
        Assertions.assertEquals(timestamp, serviceUserDTO.getLastLogin());
    }

    @Test
    void whenUpdateUserNameWithNull_thenThrowResponseStatusException (){
        UserNameDTO userNameDTO = new UserNameDTO(1, null);
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.updateUserName(userNameDTO));
    }

    @Test
    void whenUpdateUserNameWithInvalidId_thenThrowResponseStatusException (){
        UserNameDTO userNameDTO = new UserNameDTO(-1, "New Name");
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.updateUserName(userNameDTO));
    }

    @Test
    void whenUpdateUserAuthority_thenReturnUpdatedUserDTO (){
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(1, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.USER, timestamp, timestamp);

        UserAuthorityDTO userAuthorityDTO = new UserAuthorityDTO(1, Authority.ADMIN);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenAnswer(u -> u.getArguments()[0]);

        UserDTO serviceUserDTO = userService.updateUserAuthority(userAuthorityDTO);

        Assertions.assertNotNull(serviceUserDTO);
        Assertions.assertEquals(1, serviceUserDTO.getUserId());
        Assertions.assertEquals("cool@gmail.com",serviceUserDTO.getEmail());
        Assertions.assertEquals("John Doe", serviceUserDTO.getName());
        Assertions.assertTrue(serviceUserDTO.isActive());
        Assertions.assertTrue(serviceUserDTO.isSubscribedFlashcard());
        Assertions.assertFalse(serviceUserDTO.isSubscribedStacktrace());
        Assertions.assertEquals(Authority.ADMIN, serviceUserDTO.getAuthority());
        Assertions.assertEquals(timestamp, serviceUserDTO.getRegistrationTime());
        Assertions.assertEquals(timestamp, serviceUserDTO.getLastLogin());
    }

    @Test
    void whenUpdateUserAuthorityWithNull_thenThrowResponseStatusException (){
        UserAuthorityDTO userAuthorityDTO = new UserAuthorityDTO(1, null);
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.updateUserAuthority(userAuthorityDTO));
    }

    @Test
    void whenUpdateUserAuthorityWithInvalidId_thenThrowResponseStatusException (){
        UserAuthorityDTO userAuthorityDTO = new UserAuthorityDTO(-1, Authority.ADMIN);
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.updateUserAuthority(userAuthorityDTO));
    }

    @Test
    void whenUpdateUserIsActive_thenReturnUpdatedUserDTO (){
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(1, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.USER, timestamp, timestamp);

        UserIsActiveDTO userIsActiveDTO = new UserIsActiveDTO(1, false);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenAnswer(u -> u.getArguments()[0]);

        UserDTO serviceUserDTO = userService.updateUserIsActive(userIsActiveDTO);

        Assertions.assertNotNull(serviceUserDTO);
        Assertions.assertEquals(1, serviceUserDTO.getUserId());
        Assertions.assertEquals("cool@gmail.com",serviceUserDTO.getEmail());
        Assertions.assertEquals("John Doe", serviceUserDTO.getName());
        Assertions.assertFalse(serviceUserDTO.isActive());
        Assertions.assertTrue(serviceUserDTO.isSubscribedFlashcard());
        Assertions.assertFalse(serviceUserDTO.isSubscribedStacktrace());
        Assertions.assertEquals(Authority.USER, serviceUserDTO.getAuthority());
        Assertions.assertEquals(timestamp, serviceUserDTO.getRegistrationTime());
        Assertions.assertEquals(timestamp, serviceUserDTO.getLastLogin());
    }

    @Test
    void whenUpdateUserIsActiveWithInvalidId_thenThrowResponseStatusException (){
        UserIsActiveDTO userIsActiveDTO = new UserIsActiveDTO(-1, false);
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.updateUserIsActive(userIsActiveDTO));
    }

    @Test
    void whenUpdateUserSubscriptionStatus_thenReturnUpdatedUserDTO (){
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(1, "cool@gmail.com",
                "John Doe", true, false, false,
                Authority.USER, timestamp, timestamp);

        UserSubscriptionsDTO userSubscriptionsDTO = new UserSubscriptionsDTO(1, true, true);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenAnswer(u -> u.getArguments()[0]);

        UserDTO serviceUserDTO = userService.updateUserSubscriptionStatus(userSubscriptionsDTO);

        Assertions.assertNotNull(serviceUserDTO);
        Assertions.assertEquals(1, serviceUserDTO.getUserId());
        Assertions.assertEquals("cool@gmail.com",serviceUserDTO.getEmail());
        Assertions.assertEquals("John Doe", serviceUserDTO.getName());
        Assertions.assertTrue(serviceUserDTO.isActive());
        Assertions.assertTrue(serviceUserDTO.isSubscribedFlashcard());
        Assertions.assertTrue(serviceUserDTO.isSubscribedStacktrace());
        Assertions.assertEquals(Authority.USER, serviceUserDTO.getAuthority());
        Assertions.assertEquals(timestamp, serviceUserDTO.getRegistrationTime());
        Assertions.assertEquals(timestamp, serviceUserDTO.getLastLogin());
    }

    @Test
    void whenUpdateUserSubscriptionStatusWithInvalidId_thenThrowResponseStatusException (){
        UserSubscriptionsDTO userSubscriptionsDTO = new UserSubscriptionsDTO(-11, false, false);
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.updateUserSubscriptionStatus(userSubscriptionsDTO));
    }
}
