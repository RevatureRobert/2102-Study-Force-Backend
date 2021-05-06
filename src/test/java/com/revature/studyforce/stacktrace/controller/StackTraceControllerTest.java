package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.controller.StackTraceController;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Time;
import java.sql.Timestamp;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for StackTraceController
 * @author John Stone
 * @author Joshua Swanson
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class StackTraceControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private StacktraceService stacktraceService;

    @Autowired
    private StacktraceRepository stacktraceRepository;

    @Autowired
    private StackTraceController stacktraceController;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(stacktraceController).build();
    }

    @Test
    void givenStacktrace_whenGetAll_thenStacktraceRetrieved() throws Exception {
        User u = userRepository.save(new User(1,"Test@mail.com","Bob",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)));
        Technology t = new Technology(2, "TestTech");
        stacktraceRepository.save(new Stacktrace(1,
                new User(1,"Test@mail.com","Bob",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(2, "TestTech"), new Timestamp(0), null));
        mockMvc = MockMvcBuilders.standaloneSetup(stacktraceController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/stacktrace")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stacktraceId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").value("Bob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("TestTitle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].body").value("TestBody"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].technologyId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].technologyName").value("TestTech"))
                .andReturn();
    }


    /**
     * Test for deleteStackTraceById()
     * @throws Exception Thrown by MockMvc.perform()
     */
    @Test
    void whenStackTraceDeleted_thenCorrectResponseReturned() throws Exception {
        User u = userRepository.save(new User(1,"Test@mail.com","Bob",true,true,true, Authority.USER,Timestamp.valueOf("2007-09-23 10:10:10.0"),Timestamp.valueOf("2007-09-23 10:10:10.0")));
        Technology t = new Technology(2, "TestTech");
        stacktraceRepository.save(new Stacktrace(1,
                u,
                "TestTitle", "TestBody", t, new Timestamp(0), null));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/stacktrace/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stacktraceId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Bob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TestTitle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("TestBody"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.technologyId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.technologyName").value("TestTech"))
                .andReturn();
    }
}
