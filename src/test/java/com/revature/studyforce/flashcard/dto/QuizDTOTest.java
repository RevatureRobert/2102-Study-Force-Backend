package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Test class for the QuizDTO
 * @author Nick Zimmerman
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class QuizDTOTest {

    @Test
    void givenQuiz_whenQuizToDTO_shouldReturnQuizDTO(){
        User user = new User(0, "mscott@dunder.com","password","Michael","Scott",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Topic topic = new Topic(0,"java");
        Flashcard flashcard1 = new Flashcard(0,user,topic,"Whats my favorite color?",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Flashcard flashcard2 = new Flashcard(0,user,topic,"Can I go to the bathroom?",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);

        List<Flashcard> deck = new ArrayList<>();

        deck.add(flashcard1);
        deck.add(flashcard2);

        Quiz quiz = new Quiz(0,user,"testQuiz",deck);
        QuizDTO quizDTO = QuizDTO.quizToDTO().apply(quiz);

        Assertions.assertEquals(0,quizDTO.getQuizId());
        Assertions.assertEquals(quiz.getQuizUser().getUserId(),quizDTO.getQuizUserId());
        Assertions.assertEquals(quiz.getQuizName(),quizDTO.getQuizName());
        Assertions.assertTrue (quizDTO.getFlashcards().contains(FlashcardAllDTO.convertToDTO().apply(flashcard1)));
        Assertions.assertTrue (quizDTO.getFlashcards().contains(FlashcardAllDTO.convertToDTO().apply(flashcard2)));

    }

}
