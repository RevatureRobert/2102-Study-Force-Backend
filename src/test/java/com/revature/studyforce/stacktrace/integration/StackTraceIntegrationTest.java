package com.revature.studyforce.stacktrace.integration;

import com.revature.studyforce.stacktrace.controller.StackTraceController;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Time;
import java.sql.Timestamp;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class StackTraceIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private StacktraceRepository stacktraceRepository;

    @Autowired
    private StackTraceController stacktraceController;

    @Test
    public void givenStacktrace_whenGetAll_thenStacktraceRetrieved() throws Exception {
        stacktraceRepository.save(new Stacktrace(1,
                new User(1,"Test@mail.com","Pass","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(1, "TestTech"), new Timestamp(0)));
        mockMvc = MockMvcBuilders.standaloneSetup(stacktraceController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/stacktrace/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.email").value("Test@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.password").value("Pass"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.firstName").value("Bob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.isActive").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.isSubscribedFlashcard").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.isSubscribedStacktrace").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.Authority").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.registrationTime").value(new Time(0).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.lastLogin").value(new Time(0).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].tile").value("TestTitle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].body").value("TestBody"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].Technology.technologyId").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].Technology.technologyName").value("TestTech"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creationTime").value(new Time(0).toString()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
