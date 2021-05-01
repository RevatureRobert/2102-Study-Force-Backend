package com.revature.StudyForce.service;

import com.revature.StudyForce.flashcard.dto.VoteDTO;
import com.revature.StudyForce.flashcard.model.Answer;
import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.repository.AnswerRepository;
import com.revature.StudyForce.flashcard.repository.VoteRepo;
import com.revature.StudyForce.flashcard.service.RatingService;
import com.revature.StudyForce.flashcard.service.VoteService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
public class TestVoteService {

    @MockBean
    private UserRepository userRepo;
    @MockBean
    private AnswerRepository answerRepo;
    @MockBean
    private VoteRepo voteRepo;

    @Autowired
    private VoteService voteService;

    User u = new User(0,"jesus.christ@revature.com","password","Jesus","Christ",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
    Answer a = new Answer(0,0,0,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
    Vote vote = new Vote(6,1,a,u);

    @Test
    public void testAddVote() throws Exception{
        userRepo = mock(UserRepository.class);
        answerRepo = mock(AnswerRepository.class);
        voteRepo = mock(VoteRepo.class);
        voteService = mock(VoteService.class);
        VoteDTO dto = new VoteDTO();
        dto.setVoteId(6);
        dto.setVoteValue(1);
        Mockito.when(userRepo.findById(0)).thenReturn(Optional.of(u));
        Mockito.when(answerRepo.findById(0)).thenReturn(Optional.of(a));
        Mockito.when(voteRepo.save(vote)).thenReturn(vote);
        VoteDTO res = voteService.addVote(vote);
        assertTrue(res.getVoteId() == 6);
        assertTrue(res.getVoteValue() == 1);
        System.out.println(res.getVoteId() + ", " + res.getVoteValue());
    }
}
