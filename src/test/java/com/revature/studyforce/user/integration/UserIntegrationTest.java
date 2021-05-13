package com.revature.studyforce.user.integration;


import com.revature.studyforce.user.controller.UserController;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * tests for integration of User Controller {@link UserController}
 * @author Daniel Reyes
 * @author Daniel Bernier
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(username = "test@test.test",authorities = "ROLE_USER")
@AutoConfigureMockMvc
class UserIntegrationTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void givenBatch_whenGetAllUsers_theUserRetrieved() throws Exception {
        Authority authority = Authority.ROLE_USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);
        System.out.println(userRepository.findAll().toString());
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].authority").value("ROLE_USER"))
                .andReturn();
    }

    @Test
    void givenBatch_whenGetUserById_theUserRetrieved() throws Exception {
        Authority authority = Authority.ROLE_USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("ROLE_USER"))
                .andReturn();

    }

    @Test
    void givenBatch_whenGetUserByEmail_theUserRetrieved() throws Exception {
        Authority authority = Authority.ROLE_USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/email?email=dan@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("ROLE_USER"))
                .andReturn();

    }

    @Test
    void givenBatch_whenGetUserByName_theUserRetrieved() throws Exception {
        Authority authority = Authority.ROLE_USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(0 , "dan@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User user2 = new User(0 , "danrey@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);
        userRepository.save(user2);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/name?name=daniel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].authority").value("ROLE_USER"))


        .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].userId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].email").value("danrey@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].authority").value("ROLE_USER"))
                .andReturn();
    }

    @Test
    void givenBatch_whenGetUserByTimeStamp_theUserRetrieved() throws Exception {
        Authority authority = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User user = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/time/1619996684739")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].authority").value("ROLE_USER"))
                .andReturn();
    }
}
