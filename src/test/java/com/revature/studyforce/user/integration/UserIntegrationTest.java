package com.revature.studyforce.user.integration;


import com.revature.studyforce.user.controller.UserController;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:test-application.properties")
public class UserIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Test
    void createUser_thenUserRetrieved() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\" : \"LebronJames@revature.net\", \"password\" : \"Pass\", \"name\" : \"Lebron James\"," +
                        "\"authority\" : \"USER\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("LebronJames@revature.net"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lebron James"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authority").value("USER"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
