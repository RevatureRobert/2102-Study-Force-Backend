package com.revature.StudyForce.stacktrace.dto;

import com.revature.StudyForce.stacktrace.model.Solution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SolutionsDTO
 *  There has to be a problem with testing like this. These tests are not
 *  meaningful but we need code coverage
 * @author Joshua Swanson
 */
class SolutionDTOTest {

    Solution testSolution;
    SolutionDTO testSolutionDTO;

    @BeforeEach
    public void setUp(){
        testSolutionDTO = new SolutionDTO(1, null, null, "Test Body", false, null);
        testSolution = new Solution(1, null, null, "Test Body", false, null);
    }

    /**
     * Test that a Solution can correctly return a SolutionDTO
     */
    @Test
    void SolutionToDTOTest(){
        SolutionDTO solutionDTO = SolutionDTO.solutionToDTO().apply(testSolution);
        assertEquals(solutionDTO.getClass(), SolutionDTO.class);
        assertEquals(solutionDTO.getSolutionId(), testSolution.getSolutionId());
        assertEquals(solutionDTO.getBody(), testSolution.getBody());
        assertEquals(solutionDTO.getAdminSelected(), testSolution.getAdminSelected());
        assertEquals(solutionDTO.getCreationTime(), testSolution.getCreationTime());
        assertEquals(solutionDTO.getUserId(), testSolution.getUserId());
        assertEquals(solutionDTO.getStackTraceId(), testSolution.getStackTraceId());
    }

    /**
     * Test that passing a null solution throws an IllegalArgumentException
     */
    @Test
    void SolutionToDTONullSolutionTest(){
        Function<Solution, SolutionDTO> solutionToDTOFunction = SolutionDTO.solutionToDTO();
        assertThrows(IllegalArgumentException.class, () -> solutionToDTOFunction.apply(null));
    }

    /**
     * Test that a SolutionDTO can correctly return a Solution
     */
    @Test
    void DTOToSolutionTest(){
        Solution solution = SolutionDTO.dtoToSolution().apply(testSolutionDTO);
        assertEquals(solution.getClass(), Solution.class);
        assertEquals(solution.getSolutionId(), testSolutionDTO.getSolutionId());
        assertEquals(solution.getBody(), testSolutionDTO.getBody());
        assertEquals(solution.getAdminSelected(), testSolutionDTO.getAdminSelected());
        assertEquals(solution.getCreationTime(), testSolutionDTO.getCreationTime());
        assertEquals(solution.getUserId(), testSolutionDTO.getUserId());
        assertEquals(solution.getStackTraceId(), testSolutionDTO.getStackTraceId());
    }

    /**
     * Test that passing a null SolutionDTO throws an IllegalArgumentException
     */
    @Test
    void DTOToSolutionNullSolutionDTOTest(){
        Function<SolutionDTO, Solution> dtoToSolutionFunction = SolutionDTO.dtoToSolution();
        assertThrows(IllegalArgumentException.class, () -> dtoToSolutionFunction.apply(null));
    }
}