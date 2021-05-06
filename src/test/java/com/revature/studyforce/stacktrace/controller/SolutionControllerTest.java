package com.revature.studyforce.stacktrace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.StacktraceUserDTO;
import com.revature.studyforce.stacktrace.service.SolutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test class for SolutionController. Uses MockMvc to build requests and match responses
 * @author Joshua Swanson
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class SolutionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SolutionService solutionService;

    @Autowired
    SolutionController solutionController;

    List<SolutionDTO> testSolutionDTOList;
    SolutionDTO testSolutionDTO;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(solutionController).build();
        testSolutionDTO = new SolutionDTO(1, 1, new StacktraceUserDTO(1, "Test", "Test"), "Test Body", false, null);
        testSolutionDTOList = new ArrayList<>();
        testSolutionDTOList.add(testSolutionDTO);
        objectMapper = new ObjectMapper();
    }

    /**
     * Test for getAllSolutionsForStacktrace
     * @throws Exception (for mockMvc.perform())
     */
    @Test
    void getAllSolutionsForStacktraceTest() throws Exception {
        Mockito.doReturn(testSolutionDTOList).when(solutionService).getAllSolutionsForStacktrace(1, 1, 1);
        mockMvc.perform(MockMvcRequestBuilders.get("/stacktrace/solution/{stackTraceId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(solutionService, times(1)).getAllSolutionsForStacktrace(1, 1, 1);
    }

    /**
     * Test for submitFirstSolution
     * @throws Exception (for mockMvc.perform())
     */
    @Test
    void submitFirstSolutionTest() throws Exception{
        Mockito.doReturn(testSolutionDTO).when(solutionService).submitFirstSolution(testSolutionDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/stacktrace/solution")
                .content(objectMapper.writeValueAsString(testSolutionDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for updateSolutionTest
     * @throws Exception (for mockMvc.perform())
     */
    @Test
    void updateSolutionTest() throws Exception{
        Mockito.doReturn(testSolutionDTO).when(solutionService).updateSolution(testSolutionDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/stacktrace/solution")
                .content(objectMapper.writeValueAsString(testSolutionDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test for deleteSolutionTest
     * @throws Exception (for mockMvc.perform())
     */
    @Test
    void deleteSolutionTest() throws Exception{
        Mockito.doNothing().when(solutionService).deleteSolution(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/stacktrace/solution/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(solutionService, times(1)).deleteSolution(1);
    }
}