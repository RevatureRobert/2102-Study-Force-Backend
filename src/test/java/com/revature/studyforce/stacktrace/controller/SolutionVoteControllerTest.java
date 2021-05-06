package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.SolutionVoteRepository;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import com.revature.studyforce.stacktrace.service.SolutionVoteService;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class SolutionVoteControllerTest {

    @Autowired
    private SolutionVoteController solutionVoteController;

    @Autowired
    private SolutionVoteRepository solutionVoteRepository;

    @Autowired
    private SolutionVoteService solutionVoteService;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StacktraceRepository stacktraceRepository;

    @Autowired
    private SolutionRepository solutionRepository;

//    @Test
//    void givenSolutionId_whenGetSolutionVoteBySolutionId() throws Exception {
//        User user = new User(1,"Test@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0));
//        user = userRepository.save(user);
//        User user2 = new User(2,"Test2@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0));
//        user2 = userRepository.save(user2);
//        User user3 = new User(3,"Test3@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0));
//        user3 = userRepository.save(user3);
//        Technology technology = new Technology(1, "Java");
//        technology = technologyRepository.save(technology);
//        Stacktrace stacktrace = new Stacktrace(1, user, "stuff for title", "stuff for body", technology, new Timestamp(0),null);
//        stacktrace = stacktraceRepository.save(stacktrace);
//        Solution solution = new Solution(1, stacktrace, user2, "Test Body", false, null,null);
//        solution = solutionRepository.save(solution);
//        SolutionVoteDTO solutionVoteDTO = new SolutionVoteDTO(1, 3, 1, -1);
//        solutionVoteService.getAllSolutionsVotesForSolution(solution.getSolutionId());
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(solutionVoteController).build();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/stacktrace/solution-vote/1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].solutionVoteId").value(solutionVoteDTO.getSolutionVoteId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").value(solutionVoteDTO.getUserId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].solutionId").value(solutionVoteDTO.getSolutionId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].value").value(solutionVoteDTO.getValue()))
//
//                .andReturn();
//
//    System.out.println(result.getResponse().getContentAsString());
//    System.out.println(result.getResponse().getStatus());
//    }
}
