package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.SolutionVoteRepository;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test class for SolutionVote Service
 * @author Joey Elmblad
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SolutionVoteTest {

    @MockBean
    private SolutionVoteRepository solutionVoteRepository;

    @Autowired
    SolutionVoteService solutionVoteService;

    SolutionVoteDTO testSolutionVoteDTO;
    SolutionVote testSolutionVote;
    SolutionVote testNullSolutionVote;
    List<SolutionVoteDTO> testSolutionVoteDTOList;
    List<SolutionVote> testSolutionVoteList;

    @BeforeEach
    public void setUp(){
        User user = new User(1,"Test@mail.com","Bob",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0));
        Technology technology = new Technology(1, "Java");
        Stacktrace stacktrace = new Stacktrace(1, user, "stuff for title", "stuff for body", technology, new Timestamp(0),null);
        Solution solution = new Solution(1, stacktrace, user, "Test Body", false, false, null, 0, null);
        testSolutionVoteDTO = new SolutionVoteDTO(1, 1, 1, -1);
        testSolutionVote = new SolutionVote(1, user, solution, -1);
        testNullSolutionVote = null;
        testSolutionVoteDTOList = new ArrayList<>();
        testSolutionVoteDTOList.add(testSolutionVoteDTO);
        testSolutionVoteList = new ArrayList<>();
        testSolutionVoteList.add(testSolutionVote);
    }

    @Test
    void givenSolutionId_whenGetAllSolutionsVotesForSolution_returnSolutionVoteListTests(){
        Mockito.doReturn(testSolutionVoteList).when(solutionVoteRepository).findBySolutionId(1);
        List<SolutionVoteDTO> solutionVoteDTOS = solutionVoteService.getAllSolutionsVotesForSolution(1);
        assertEquals(1, solutionVoteDTOS.size());
        assertEquals(solutionVoteDTOS.get(0).getSolutionVoteId(), testSolutionVoteDTO.getSolutionVoteId());
        assertEquals(solutionVoteDTOS.get(0).getSolutionId(), testSolutionVoteDTO.getSolutionId());
        assertEquals(solutionVoteDTOS.get(0).getUserId(), testSolutionVoteDTO.getUserId());
        assertEquals(solutionVoteDTOS.get(0).getValue(), testSolutionVoteDTO.getValue());
    }

    @Test
    void givenSolutionVote_whenSubmitVote_returnSolutionVoteTests(){
        Mockito.doReturn(testSolutionVote).when(solutionVoteRepository).save(any(SolutionVote.class));
        SolutionVoteDTO solutionVoteDTO = solutionVoteService.submitVote(testSolutionVoteDTO);
        assertEquals(solutionVoteDTO.getSolutionVoteId(), testSolutionVoteDTO.getSolutionVoteId());
        assertEquals(solutionVoteDTO.getSolutionId(), testSolutionVoteDTO.getSolutionId());
        assertEquals(solutionVoteDTO.getUserId(), testSolutionVoteDTO.getUserId());
        assertEquals(solutionVoteDTO.getValue(), testSolutionVoteDTO.getValue());
    }

}
