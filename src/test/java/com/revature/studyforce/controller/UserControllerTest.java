package com.revature.studyforce.controller;


import com.revature.studyforce.user.contollers.UserController;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import com.revature.studyforce.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

/**
 * tests for integration of User Controller
 *
 * @author Daniel Reyes
 */

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class UserControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Test
    void GetAllUsers() throws Exception {
        Authority authority = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);
    System.out.println(userRepository.findAll().toString());
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/User/all")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].authority").value("USER"))
                .andReturn();
    System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void GetUserById() throws Exception {
        Authority authority = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/User/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("USER"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void GetUserByEmail() throws Exception {
        Authority authority = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/User/email?email=dan@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("USER"))
                .andReturn();
        //System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void GetUserByName() throws Exception {
        Authority authority = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User user = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        userRepository.save(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/User/name?name=daniel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value("dan@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Daniel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedFlashcard").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].authority").value("USER"))
                .andReturn();
        //System.out.println(result.getResponse().getContentAsString());
    }



}
