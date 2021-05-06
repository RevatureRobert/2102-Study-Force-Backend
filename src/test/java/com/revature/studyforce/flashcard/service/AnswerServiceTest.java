package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.AnswerDTO;
import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.AnswerRepository;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AnswerService {@link AnswerService}
 * @author Edson Rodriguez
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
class AnswerServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FlashcardRepository flashcardRepo;

    @MockBean
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerService answerService;

    @Test
    void givenAnswer_whenCreateAnswer_shouldReturnAnswerDTO(){
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Answer a = new Answer(0,user,flashcard,"tcs filename",0,false,false,Timestamp.valueOf(LocalDateTime.now()), null);
        AnswerDTO aDTO = new AnswerDTO(0,0,"tcs filename");

        Mockito.when(flashcardRepo.findById(0)).thenReturn(Optional.of(flashcard));
        Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(answerRepository.save(org.mockito.ArgumentMatchers.isA(Answer.class))).thenReturn(a);

        Answer res = answerService.createAnswer(aDTO);

        assertNotNull(res);
        assertEquals(0,res.getAnswerId());
        assertEquals(user,res.getCreator());
        assertEquals(flashcard,res.getFlashcard());
        assertEquals("tcs filename",res.getAnswerText());
        assertEquals(0,res.getAnswerScore());
        assertFalse(res.isSelectedAnswer());
        assertFalse(res.isTrainerSelected());
        assertNotNull(res.getCreationTime());
        assertNull(res.getResolutionTime());

    }

    @Test
    void givenAnswerId_whenDeleteAnswerById_shouldDeleteAnswer(){
        answerService.deleteAnswerById(1);
        Mockito.verify(answerRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    void givenFlashcardId_whenGetAllByFlashcardId_shouldReturnPageOfAnswersWithPagination(){
        List<Answer> answers = new ArrayList<>();
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        User user2 = new User(1,"edson2@revature.com","password2","Edson2","Rodriguez2",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);

        Answer a = new Answer(0,user,flashcard,"good, thanks",0,false,false,Timestamp.valueOf(LocalDateTime.now()), null);
        Answer a2 = new Answer(1,user2,flashcard,"like hell, to many test cases to write",200,false,false,Timestamp.valueOf(LocalDateTime.now()), null);
        answers.add(a);
        answers.add(a2);

        Page<Answer> answerPage = new PageImpl<>(answers);
        Mockito.when(answerRepository.findByFlashcard_id(org.mockito.ArgumentMatchers.isA(Integer.class),org.mockito.ArgumentMatchers.isA(PageRequest.class))).thenReturn(answerPage);

        Page<Answer> res = answerService.getAllByFlashcardId(0,1,10,"answerscore","DESC");

        assertNotNull(res);
        assertEquals(0,res.getContent().get(0).getAnswerId());
        assertEquals(user,res.getContent().get(0).getCreator());
        assertEquals(flashcard,res.getContent().get(0).getFlashcard());
        assertEquals("good, thanks",res.getContent().get(0).getAnswerText());
        assertEquals(0,res.getContent().get(0).getAnswerScore());
        assertFalse(res.getContent().get(0).isSelectedAnswer());
        assertFalse(res.getContent().get(0).isTrainerSelected());
        assertNotNull(res.getContent().get(0).getCreationTime());
        assertNull(res.getContent().get(0).getResolutionTime());

        assertNotNull(res);
        assertEquals(1,res.getContent().get(1).getAnswerId());
        assertEquals(user2,res.getContent().get(1).getCreator());
        assertEquals(flashcard,res.getContent().get(1).getFlashcard());
        assertEquals("like hell, to many test cases to write",res.getContent().get(1).getAnswerText());
        assertEquals(200,res.getContent().get(1).getAnswerScore());
        assertFalse(res.getContent().get(1).isSelectedAnswer());
        assertFalse(res.getContent().get(1).isTrainerSelected());
        assertNotNull(res.getContent().get(1).getCreationTime());
        assertNull(res.getContent().get(1).getResolutionTime());

    }
}

