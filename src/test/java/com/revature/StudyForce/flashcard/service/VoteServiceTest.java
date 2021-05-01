package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.VoteDTO;
import com.revature.StudyForce.flashcard.model.Answer;
import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.repository.AnswerRepository;
import com.revature.StudyForce.flashcard.repository.VoteRepository;
import com.revature.StudyForce.user.model.Authority;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.repository.UserRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
public class VoteServiceTest {

    @MockBean
    private UserRepository userRepo;
    @MockBean
    private AnswerRepository answerRepo;
    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @Test
    public void testAddVote() throws Exception{
        User u = new User(0,"jesus.christ@revature.com","password","Jesus","Christ",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Answer a = new Answer(0,0,0,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);

        userRepo = mock(UserRepository.class);
        answerRepo = mock(AnswerRepository.class);
        voteRepository = mock(VoteRepository.class);

        Mockito.when(answerRepo.findById(0)).thenReturn(Optional.of(a));
        Mockito.when(userRepo.findById(0)).thenReturn(Optional.of(u));
        Mockito.when(voteRepository.save(org.mockito.ArgumentMatchers.isA(Vote.class))).thenReturn(vote);

        VoteDTO res = voteService.addVote(vote);
        assertTrue(res.getVoteId() == 6);
        assertTrue(res.getVoteValue() == 1);
        System.out.println(res.getVoteId() + ", " + res.getVoteValue());
    }
}
