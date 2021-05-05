package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SolutionsDTO
 *  There has to be a problem with testing like this. These tests are not
 *  meaningful but we need code coverage
 * @author Joshua Swanson
 */
@SpringBootTest
class SolutionDTOTest {

    Solution testSolution;
    SolutionDTO testSolutionDTO;
    User testUser;
    Stacktrace testStacktrace;

    @BeforeEach
    public void setUp(){
        testUser = new User(1, "test.test.com", "TestPassword", "Test", "Test", false, true, true, Authority.USER, null, null);
        testStacktrace = new Stacktrace(1, testUser, "Test Title", "Test Body", null, null, null);
        testSolutionDTO = new SolutionDTO(1, 1, 1, "Test Body", false, null);
        testSolution = new Solution(1, testStacktrace, testUser, "Test Body", false, null, null);
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
        assertEquals(solutionDTO.getUserId(), testSolution.getUserId().getUserId());
        assertEquals(solutionDTO.getStackTraceId(), testSolution.getStackTraceId().getStacktraceId());
    }

    /**
     * Test that passing a null solution throws an IllegalArgumentException
     */
    @Test
    void SolutionToDTONullSolutionTest(){
        Function<Solution, SolutionDTO> solutionToDTOFunction = SolutionDTO.solutionToDTO();
        assertThrows(IllegalArgumentException.class, () -> solutionToDTOFunction.apply(null));
    }
}