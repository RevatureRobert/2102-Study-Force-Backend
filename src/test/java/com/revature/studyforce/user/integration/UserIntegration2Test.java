package com.revature.studyforce.user.integration;

import com.revature.studyforce.user.controller.UserController;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.bouncycastle.tsp.TSPUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.time.Instant;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * testing several PUT methods from {@link UserController}
 * @author Daniel Bernier
 */

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(username = "test@test.test",authorities = "ROLE_ADMIN")
class UserIntegration2Test {

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void userInRepo_whenUpdateUserName_thenUpdatedUserDTORetrieved() throws Exception {
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(0, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.ROLE_USER, timestamp, timestamp);

        System.out.println(userRepository.save(user));

        mockMvc.perform(MockMvcRequestBuilders.put("/users/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\" :"+user.getUserId()+ " , \"name\" : \"New Name\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
    void userInRepo_whenUpdateUserIsActive_thenUpdatedUserDTORetrieved() throws Exception {
        Timestamp timestamp = Timestamp.from(Instant.now());
        User user = new User(0, "cool@gmail.com",
                "John Doe", true, true, false,
                Authority.ROLE_USER, timestamp, timestamp);

        System.out.println(userRepository.save(user));

        mockMvc.perform(MockMvcRequestBuilders.put("/users/active")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\" : 1, \"active\" : \"false\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                Authority.ROLE_USER, timestamp, timestamp);

        System.out.println(userRepository.save(user));

        mockMvc.perform(MockMvcRequestBuilders.put("/users/subscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userId\" : 1, \"subscribedFlashcard\" : true, \"subscribedStacktrace\" : true }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
