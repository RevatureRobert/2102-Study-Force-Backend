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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

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
@TestPropertySource(locations = "classpath:test-application.properties")
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

        testUser = new User(1, "test.test.com", "TestName", false, true, true, Authority.USER, null, null);
        testStacktrace = new Stacktrace(1, testUser, "Test Title", "Test Body", null, null, null);
        testSolutionDTO = new SolutionDTO(1, 1,1, "TestName", "Test Body", false, false, null, 0);
        testSolution = new Solution(1, testStacktrace, testUser, "Test Body", false, false, null, 0, null);
        testNullSolution = null;
        testSolutionDTOList = new ArrayList<>();
        testSolutionDTOList.add(testSolutionDTO);
        testSolutionList = new ArrayList<>();
        testSolutionList.add(testSolution);
    }

    @Test
    void returnAPageOfSolutionDTOs_WhenAValidStacktraceIdIsPassed(){
        Page<Solution> solutionPage = new PageImpl<>(testSolutionList);
        Mockito.doReturn(solutionPage).when(solutionRepository).findByStackTraceId_stacktraceId(1, PageRequest.of(0,5));
        Page<SolutionDTO> solutionDTOS = solutionPage.map(SolutionDTO.solutionToDTO());
        assertNotNull(solutionService.getAllSolutionsForStacktrace(1, 0, 5));
        assertEquals(1, solutionDTOS.getContent().size());
        assertEquals(solutionDTOS.getContent().get(0).getSolutionId(), testSolutionDTO.getSolutionId());
        assertEquals(solutionDTOS.getContent().get(0).getStackTraceId(), testSolutionDTO.getStackTraceId());
        assertEquals(solutionDTOS.getContent().get(0).getUserName(), testSolutionDTO.getUserName());
        assertEquals(solutionDTOS.getContent().get(0).getBody(), testSolutionDTO.getBody());
        assertEquals(solutionDTOS.getContent().get(0).getCreationTime(), testSolutionDTO.getCreationTime());
        assertEquals(solutionDTOS.getContent().get(0).getAdminSelected(), testSolutionDTO.getAdminSelected());
    }

    @Test
    void whenASolutionDTOIsPassed_SaveAndReturnThatSolution(){
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

    @Test
    void whenDeleteSolutionCalled_ThenDeleteAndReturnSolution(){
        Mockito.doReturn(Optional.of(testSolution)).when(solutionRepository).findById(1);
        Mockito.doNothing().when(solutionRepository).delete(testSolution);
        assertEquals(testSolutionDTO.getSolutionId(), solutionService.deleteSolution(1).getSolutionId());
        assertEquals(testSolutionDTO.getUserId(), solutionService.deleteSolution(1).getUserId());
        assertEquals(testSolutionDTO.getStackTraceId(), solutionService.deleteSolution(1).getStackTraceId());
        assertEquals(testSolutionDTO.getBody(), solutionService.deleteSolution(1).getBody());
        assertEquals(testSolutionDTO.getAdminSelected(), solutionService.deleteSolution(1).getAdminSelected());
        assertEquals(testSolutionDTO.getUserName(), solutionService.deleteSolution(1).getUserName());
        assertEquals(testSolutionDTO.getCreationTime(), solutionService.deleteSolution(1).getCreationTime());
    }
  
    @Test
    void whenDeleteSolutionCalledWithNullParameter_ThenReturnNull(){
        Mockito.doReturn(Optional.ofNullable(null)).when(solutionRepository).findById(1);
        assertNull(solutionService.deleteSolution(1));
    }

    @Test
    void ReturnValidPageSize_WhenInvalidPageSizeIsPassed(){
        assertEquals(5, solutionService.validatePageSize(500));
    }

    @Test
    void ReturnPassedPageSize_WhenValidPageSizeIsPassed(){
        assertEquals(5, solutionService.validatePageSize(5));
    }

    @Test
    void ReturnValidPageNumber_WhenInvalidPageNumberIsPassed(){
        assertEquals(0, solutionService.validatePage(-1));
    }

    @Test
    void ReturnPassedPageNumber_WhenValidPageNumberIsPassed(){
        assertEquals(1, solutionService.validatePage(1));
    }
}