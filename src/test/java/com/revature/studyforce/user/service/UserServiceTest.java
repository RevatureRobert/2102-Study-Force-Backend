package com.revature.studyforce.user.service;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Layer Testing {@link UserService}
 * @author Daniel Reyes
 * @author Daniel Bernier
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void whenGetAllUsers_callUserRepository_retrieveUserPage(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant i = Instant.now();
        long d = Date.from(i).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(d));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);
        System.out.println(user.toString());
        Mockito.when(userRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getAllUsers(0, 5, "userId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(t2, response.getContent().get(0).getLastLogin());


    }

    @Test
    void whenGetAllUsers_callUserRepository_retrieveUserPage_testingSortBy(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant i = Instant.now();
        long d = Date.from(i).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(d));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getAllUsers(0, 5, "userId", "asc");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(t2, response.getContent().get(0).getLastLogin());

    }


    @Test
    void whenGetUserByIdTest_callUserRepository_retrieveUser(){

        Authority authority = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDTO response = userService.getUserById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getEmail());
        Assertions.assertEquals("Daniel", response.getName());
        Assertions.assertEquals(timestamp, response.getLastLogin());

    }

    @Test
    void whenGetUserByEmail_callUserRepository_retrieveUser(){

        Authority authority = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);

        Mockito.when(userRepository.findByEmail("dan@gmail.com")).thenReturn(Optional.of(user));

        UserDTO response = userService.getUserByEmail("dan@gmail.com");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getEmail());
        Assertions.assertEquals("Daniel", response.getName());
        Assertions.assertEquals(timestamp, response.getLastLogin());
        Assertions.assertTrue(response.isActive());

    }

    @Test
    void whenGetALlUsers_callUserRepository_retrieveUserPage(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findByNameContainingIgnoreCase(org.mockito.ArgumentMatchers.isA(String.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getUserByName("Daniel", 0, 5, "userId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(timestamp, response.getContent().get(0).getLastLogin());

    }

    @Test
    void whenGetALlUsers_callUserRepository_retrieveUserPage_testingSortBy(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findByNameContainingIgnoreCase(org.mockito.ArgumentMatchers.isA(String.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getUserByName("Daniel", 0, 5, "userId", "asc");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(timestamp, response.getContent().get(0).getLastLogin());

    }

    @Test
    void whenGetALlUsersByRegistrationTime_callUserRepository_retrieveUserPage(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findByRegistrationTimeAfter(org.mockito.ArgumentMatchers.isA(Timestamp.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getUserByCreationTime(epochMilli, 0, 5, "userId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(timestamp, response.getContent().get(0).getLastLogin());

    }
    @Test
    void whenGetALlUsersByRegistrationTime_callUserRepository_retrieveUserPage_testingSortBy(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findByRegistrationTimeAfter(org.mockito.ArgumentMatchers.isA(Timestamp.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getUserByCreationTime(epochMilli, 0, 5, "userId", "asc");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(timestamp, response.getContent().get(0).getLastLogin());

    }
}
