package com.revature.studyforce.stacktrace.controller;

import com.google.gson.Gson;
import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import com.revature.studyforce.stacktrace.service.TechnologyService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for TechnologyController
 * @author John Stone
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class TechnologyControllerTest {
    //TODO: Standardize Tests across project
 /*   private MockMvc mockMvc;

    @MockBean
    private TechnologyService technologyService;

    @Autowired
    private TechnologyController technologyController;

    @Autowired
    private TechnologyRepository technologyRepository;

    @BeforeEach
    private void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TechnologyController()).build();
    }

    @Test
    void getAllTechnologiesForTechnologyTest() throws Exception {
        technologyRepository.save(new Technology(0,"TestTech2"));
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
    @Test
    void deleteTechnologyTest() throws Exception{
        Mockito.doNothing().when(technologyService).deleteTechnology(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/stacktrace/technology/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(technologyService, times(1)).deleteTechnology(1);
    }*/
}
