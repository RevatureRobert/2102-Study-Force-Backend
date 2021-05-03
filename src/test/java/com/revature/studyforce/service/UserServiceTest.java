package com.revature.studyforce.service;


import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import com.revature.studyforce.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Daniel Reyes
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void GetALlUsers_returnUsers(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant i = Instant.now();
        long d = Date.from(i).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(d));
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, t2, t2);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getAllUsers(0, 5, "userId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        //Assertions.assertEquals("pass", response.getContent().get(0).getPassword()); //DTO Returns Empty String by default
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(t2, response.getContent().get(0).getLastLogin());

    }


    @Test
    void GetUserByIDTest(){

        Authority authority = Authority.USER;
        Instant i = Instant.now();
        long d = Date.from(i).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(d));
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, t2, t2);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDTO response = userService.getUserById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getEmail());
        //Assertions.assertEquals("pass", response.getContent().get(0).getPassword()); //DTO Returns Empty String by default
        Assertions.assertEquals("Daniel", response.getName());
        Assertions.assertEquals(t2, response.getLastLogin());

    }

    @Test
    void GetUserByEmail(){

        Authority authority = Authority.USER;
        Instant i = Instant.now();
        long d = Date.from(i).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(d));
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, t2, t2);

        Mockito.when(userRepository.findByEmail("dan@gmail.com")).thenReturn(Optional.of(user));

        UserDTO response = userService.getUserByEmail("dan@gmail.com");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getEmail());
        //Assertions.assertEquals("pass", response.getContent().get(0).getPassword()); //DTO Returns Empty String by default
        Assertions.assertEquals("Daniel", response.getName());
        Assertions.assertEquals(t2, response.getLastLogin());
        Assertions.assertTrue(response.isActive());

    }
    @Test
    void GetALlUsers_MatchingName(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant i = Instant.now();
        long d = Date.from(i).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(d));
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, t2, t2);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findByNameIgnoreCase(org.mockito.ArgumentMatchers.isA(String.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getUserByName("Daniel", 0, 5, "userId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        //Assertions.assertEquals("pass", response.getContent().get(0).getPassword()); //DTO Returns Empty String by default
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(t2, response.getContent().get(0).getLastLogin());

    }

    @Test
    void GetALlUsers_MatchingTimeStamp(){
        List<User> userList = new ArrayList<>();
        Authority authority = Authority.USER;
        Instant i = Instant.now();
        long d = Date.from(i).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(d));
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, t2, t2);
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);

        Mockito.when(userRepository.findByRegistrationTimeAfter(org.mockito.ArgumentMatchers.isA(Timestamp.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> response = userService.getUserByCreationTime(d, 0, 5, "userId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getUserId());
        Assertions.assertEquals("dan@gmail.com", response.getContent().get(0).getEmail());
        //Assertions.assertEquals("pass", response.getContent().get(0).getPassword()); //DTO Returns Empty String by default
        Assertions.assertEquals("Daniel", response.getContent().get(0).getName());
        Assertions.assertEquals(t2, response.getContent().get(0).getLastLogin());

    }


}
