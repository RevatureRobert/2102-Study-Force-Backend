package com.revature.StudyForce.flashcard.service;
import com.revature.StudyForce.flashcard.dto.VoteDTO;
import com.revature.StudyForce.flashcard.model.Answer;
import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.repository.AnswerRepository;
import com.revature.StudyForce.flashcard.repository.VoteRepository;
import com.revature.StudyForce.user.model.Authority;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
class VoteServiceTest {

    @MockBean
    private UserRepository userRepo;
    @MockBean
    private AnswerRepository answerRepo;
    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @Test
    void testAddVote() {
        User u = new User(0,"jesus.christ@revature.com","password","Jesus","Christ",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Answer a = new Answer(0,0,0,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);

        Mockito.when(answerRepo.findById(0)).thenReturn(Optional.of(a));
        Mockito.when(userRepo.findById(0)).thenReturn(Optional.of(u));
        Mockito.when(voteRepository.save(org.mockito.ArgumentMatchers.isA(Vote.class))).thenReturn(vote);

        Vote res = voteService.addVote(VoteDTO.functionVoteToDto().apply(vote));
        assertEquals(6, res.getVoteId());
        assertEquals(1, res.getVoteValue());
        assertEquals(a,res.getAnswer());
        assertEquals(u,res.getUser());
    }
}
