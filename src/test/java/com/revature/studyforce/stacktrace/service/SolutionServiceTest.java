package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.user.dto.UserNameDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test class for Solution Service
 * @author Joshua Swanson
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SolutionServiceTest {

    @MockBean
    private SolutionRepository solutionRepository;

    @Autowired
    SolutionService solutionService;

    SolutionDTO testSolutionDTO;
    Solution testSolution;
    Solution testNullSolution;
    List<SolutionDTO> testSolutionDTOList;
    List<Solution> testSolutionList;
    User testUser;
    Stacktrace testStacktrace;

    @BeforeEach
    public void setUp(){
        testUser = new User(1, "test.test.com", "Test", false, true, true, Authority.USER, null, null);
        testStacktrace = new Stacktrace(1, testUser, "Test Title", "Test Body", null, null, null);
        testSolutionDTO = new SolutionDTO(1, 1,1, "Test", "Test Body", false, null);
        testSolution = new Solution(1, testStacktrace, testUser, "Test Body", false, null, null);
        testNullSolution = null;
        testSolutionDTOList = new ArrayList<>();
        testSolutionDTOList.add(testSolutionDTO);
        testSolutionList = new ArrayList<>();
        testSolutionList.add(testSolution);
    }

    /**
     * Tests getAllSolutionsForStackTrace() by checking size and contents of returned SolutionDTO
     */
    @Test
    void getAllSolutionsForStackTraceTest(){
        Mockito.doReturn(testSolutionList).when(solutionRepository).findByStackTraceId(1);
        List<SolutionDTO> solutionDTOS = solutionService.getAllSolutionsForStacktrace(1);
        assertEquals(1, solutionDTOS.size());
        assertEquals(solutionDTOS.get(0).getSolutionId(), testSolutionDTO.getSolutionId());
        assertEquals(solutionDTOS.get(0).getStackTraceId(), testSolutionDTO.getStackTraceId());
        assertEquals(solutionDTOS.get(0).getUserName(), testSolutionDTO.getUserName());
        assertEquals(solutionDTOS.get(0).getBody(), testSolutionDTO.getBody());
        assertEquals(solutionDTOS.get(0).getCreationTime(), testSolutionDTO.getCreationTime());
        assertEquals(solutionDTOS.get(0).getAdminSelected(), testSolutionDTO.getAdminSelected());
        verify(solutionRepository, times(1)).findByStackTraceId(1);
    }

    /**
     * Tests submitFirstSolution()
     */
    @Test
    void submitFirstSolutionTest(){
        Mockito.doReturn(testSolution).when(solutionRepository).save(any(Solution.class));
        SolutionDTO solutionDTO = solutionService.submitFirstSolution(testSolutionDTO);
        assertEquals(solutionDTO.getSolutionId(), testSolutionDTO.getSolutionId());
        assertEquals(solutionDTO.getStackTraceId(), testSolutionDTO.getStackTraceId());
        assertEquals(solutionDTO.getUserName(), testSolutionDTO.getUserName());
        assertEquals(solutionDTO.getBody(), testSolutionDTO.getBody());
        assertEquals(solutionDTO.getCreationTime(), testSolutionDTO.getCreationTime());
        assertEquals(solutionDTO.getAdminSelected(), testSolutionDTO.getAdminSelected());
        verify(solutionRepository, times(1)).save(any(Solution.class));
    }

    /**
     * Tests updateSolution() with a solution that can be found in the data store
     */
    @Test
    void updateSolutionWithSolutionInDBTest(){
        Mockito.doReturn(Optional.of(testSolution)).when(solutionRepository).findById(any(Integer.class));
        Mockito.doReturn(testSolution).when(solutionRepository).save(any(Solution.class));
        SolutionDTO solutionDTO = solutionService.updateSolution(testSolutionDTO);
        assertEquals(solutionDTO.getSolutionId(), testSolutionDTO.getSolutionId());
        assertEquals(solutionDTO.getStackTraceId(), testSolutionDTO.getStackTraceId());
        assertEquals(solutionDTO.getUserName(), testSolutionDTO.getUserName());
        assertEquals(solutionDTO.getBody(), testSolutionDTO.getBody());
        assertEquals(solutionDTO.getCreationTime(), testSolutionDTO.getCreationTime());
        assertEquals(solutionDTO.getAdminSelected(), testSolutionDTO.getAdminSelected());
        verify(solutionRepository, times(1)).findById(any(Integer.class));
        verify(solutionRepository, times(1)).save(any(Solution.class));
    }

    /**
     * Tests updateSolution() with a Solution that is not stored in DB
     *  Should persist new solution
     */
    @Test
    void updateSolutionWithNoSolutionInDBTest(){
        Mockito.doReturn(Optional.ofNullable(testNullSolution)).when(solutionRepository).findById(any(Integer.class));
        Mockito.doReturn(testSolution).when(solutionRepository).save(any(Solution.class));
        SolutionDTO solutionDTO = solutionService.updateSolution(testSolutionDTO);
        assertEquals(solutionDTO.getSolutionId(), testSolutionDTO.getSolutionId());
        assertEquals(solutionDTO.getStackTraceId(), testSolutionDTO.getStackTraceId());
        assertEquals(solutionDTO.getUserName(), testSolutionDTO.getUserName());
        assertEquals(solutionDTO.getBody(), testSolutionDTO.getBody());
        assertEquals(solutionDTO.getCreationTime(), testSolutionDTO.getCreationTime());
        assertEquals(solutionDTO.getAdminSelected(), testSolutionDTO.getAdminSelected());
        verify(solutionRepository, times(1)).findById(any(Integer.class));
        verify(solutionRepository, times(1)).save(any(Solution.class));
    }

    /**
     * Tests deleteSolution() to verify repository invokes correct method
     */
    @Test
    void deleteSolutionTest(){
        Mockito.doNothing().when(solutionRepository).delete(any(Solution.class));
        solutionService.deleteSolution(1);
        verify(solutionRepository, times(1)).delete(any(Solution.class));
    }
}