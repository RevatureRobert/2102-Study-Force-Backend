package com.revature.studyforce.stacktrace.controller;

import com.google.gson.Gson;
import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import com.revature.studyforce.stacktrace.service.TechnologyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

/**
 * Test class for TechnologyController
 * @author John Stone
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TechnologyControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private TechnologyService technologyService;

    @Autowired
    private TechnologyController technologyController;

    @Autowired
    private TechnologyRepository technologyRepository;

/*    @BeforeEach
    private void beforeEach() {
            technologyRepository.deleteAll();
    }*/

    @Test
    void getAllTechnologiesForTechnologyTest() throws Exception {
        technologyRepository.save(new Technology(0,"TestTech2"));
        mockMvc = MockMvcBuilders.standaloneSetup(technologyController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/stacktrace/technology")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].technologyName").value("TestTech2"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    void AddTechnologyTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(technologyController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/stacktrace/technology")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new TechnologyDTO(0,"TestTechAdd"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.technologyName").value("TestTechAdd"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
