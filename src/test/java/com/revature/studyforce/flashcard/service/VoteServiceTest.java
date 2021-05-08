package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.VoteDTO;
import com.revature.studyforce.flashcard.model.*;
import com.revature.studyforce.flashcard.repository.AnswerRepository;
import com.revature.studyforce.flashcard.repository.VoteRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VoteService {@link VoteService}
 * @author Nick Zimmerman
 * @author Elizabeth Ye
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
class VoteServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AnswerRepository answerRepository;
    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @Test
    void givenVoteDTOWithMatchingUserAndAnswer_whenAddVote_shouldPersistAndReturnVote() {
        User u = new User(0,"jesus.christ@revature.com","Jesus Christ",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,u,null,"how is your day",1,1,null,null,false);
        Answer a = new Answer(0,u,flashcard,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);

        Mockito.when(answerRepository.findById(0)).thenReturn(Optional.of(a));
        Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(u));
        Mockito.when(voteRepository.save(org.mockito.ArgumentMatchers.isA(Vote.class))).thenReturn(vote);

        Vote res = voteService.addVote(VoteDTO.convertVoteToDto().apply(vote));
        assertEquals(6, res.getVoteId());
        assertEquals(1, res.getVoteValue());
        assertEquals(a,res.getAnswer());
        assertEquals(u,res.getUser());
    }

    @Test
    void givenAnswerIdAndUserId_whenGetVote_shouldReturnVote(){
        User u = new User(4,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,u,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Answer a = new Answer(0,u,flashcard,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);

        Mockito.when(answerRepository.findById(0)).thenReturn(Optional.of(a));
        Mockito.when(userRepository.findById(4)).thenReturn(Optional.of(u));
        Mockito.when(voteRepository.findByAnswer_answerIdAndUser_userId(0,4)).thenReturn(Optional.of(vote));

        VoteDTO this_vote = voteService.getVote(0,4);

        assertNotNull(this_vote);
        assertEquals(u.getUserId(),this_vote.getUserId());
        assertEquals(a.getAnswerId(),this_vote.getAnswerId());
        assertEquals(vote.getVoteValue(),this_vote.getValue());
    }

    @Test
    void givenNoAnswer_whenGetVote_shouldThrowException(){
        User u = new User(4,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,u,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Answer a = new Answer(0,u,flashcard,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);

        Mockito.when(answerRepository.findById(0)).thenReturn(Optional.of(a));
        Mockito.when(userRepository.findById(4)).thenReturn(Optional.of(u));
        Mockito.when(voteRepository.findByAnswer_answerIdAndUser_userId(0,4)).thenReturn(Optional.of(vote));

        assertThrows(ResponseStatusException.class, () -> {
            VoteDTO voteDto = voteService.getVote(10,4);
        });
    }


    @Test
    void givenNoUser_whenGetVote_shouldThrowException(){
        User u = new User(4,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,u,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Answer a = new Answer(0,u,flashcard,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);

        Mockito.when(answerRepository.findById(0)).thenReturn(Optional.of(a));
        Mockito.when(userRepository.findById(4)).thenReturn(Optional.of(u));
        Mockito.when(voteRepository.findByAnswer_answerIdAndUser_userId(0,4)).thenReturn(Optional.of(vote));

        assertThrows(ResponseStatusException.class, () -> {
            VoteDTO voteDto = voteService.getVote(0,10);
        });
    }
}
