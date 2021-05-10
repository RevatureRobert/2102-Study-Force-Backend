package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.user.dto.UserNameDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SolutionsDTO
 *  There has to be a problem with testing like this. These tests are not
 *  meaningful but we need code coverage
 *  {@link SolutionDTO#solutionToDTO()}
 * @author Joshua Swanson
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")

class SolutionDTOTest {

    Solution testSolution;
    SolutionDTO testSolutionDTO;
    User testUser;
    Stacktrace testStacktrace;

    @BeforeEach
    public void setUp(){
        testUser = new User(1, "test.test.com", "Test", false, true, true, Authority.USER, null, null);
        testStacktrace = new Stacktrace(1, testUser, "Test Title", "Test Body", null, null, 0, null);
        testSolutionDTO = new SolutionDTO(1, 1,1,"Test", "Test Body", false, null, 0);
        testSolution = new Solution(1, testStacktrace, testUser, "Test Body", false, null, 0, null);
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
        assertEquals(solutionDTO.getUserName(), testSolution.getUserId().getName());
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