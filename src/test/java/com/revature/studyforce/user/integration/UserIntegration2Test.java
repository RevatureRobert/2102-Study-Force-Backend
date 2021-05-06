package com.revature.studyforce.user.integration;

import com.revature.studyforce.user.controller.UserController;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * testing several PUT methods from {@link UserController}
 * @author Daniel Bernier
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class UserIntegration2Test {

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Test
    void userInRepo_whenUpdateUserName_thenUpdatedUserDTORetrieved() throws Exception {
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(0, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.USER, timestamp, timestamp);

        userRepository.save(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\" : 1, \"name\" : \"New Name\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cool@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.registrationTime").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastLogin").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true));
    }

    @Test
    void userInRepo_whenUpdateUserAuthority_thenUpdatedUserDTORetrieved() throws Exception {
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(0, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.USER, timestamp, timestamp);

        userRepository.save(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\" : 1, \"authority\" : \"ADMIN\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cool@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("ADMIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.registrationTime").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastLogin").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true));
    }

    @Test
    void userInRepo_whenUpdateUserIsActive_thenUpdatedUserDTORetrieved() throws Exception {
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(0, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.USER, timestamp, timestamp);

        userRepository.save(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/active")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\" : 1, \"active\" : \"false\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cool@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.registrationTime").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastLogin").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true));
    }

    @Test
    void userInRepo_whenUpdateUserSubscriptionStatus_thenUpdatedUserDTORetrieved() throws Exception {
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(0, "cool@gmail.com",
                "John Doe", true, false, false,
                Authority.USER, timestamp, timestamp);

        userRepository.save(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/subscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\" : 1, \"subscribedFlashcard\" : true, \"subscribedStacktrace\" : true }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("cool@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.registrationTime").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastLogin").value(Matchers.greaterThanOrEqualTo(timestamp.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedStacktrace").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscribedFlashcard").value(true));
    }
}
