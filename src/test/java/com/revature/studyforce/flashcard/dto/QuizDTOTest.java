package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Test class for the QuizDTO
 * @author Nick Zimmerman
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class QuizDTOTest {

    @Test
    void whenConvertingToDTO_DTOFieldsMatchOriginalObject(){
        User nick = new User(0, "mscott@dunder.com","password","Michael","Scott",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard1 = new Flashcard(0,nick,"Whats my favorite color?",1,1,Timestamp.valueOf(LocalDateTime.now()),null);
        Flashcard flashcard2 = new Flashcard(0,nick,"Can I go to the bathroom?",1,1,Timestamp.valueOf(LocalDateTime.now()),null);
        Set<Flashcard> deck = new HashSet<>();
        deck.add(flashcard1);
        deck.add(flashcard2);

        Quiz quiz = new Quiz(0,nick,"testQuiz",deck);
        QuizDTO quizDTO = QuizDTO.quizToDTO().apply(quiz);

        Assertions.assertEquals(0,quizDTO.getQuizId());
        Assertions.assertEquals("mscott@dunder.com",quizDTO.getQuizUser().getEmail());
        Assertions.assertEquals("testQuiz",quizDTO.getQuizName());
        Assertions.assertTrue (quizDTO.getFlashcardSet().contains(flashcard1));
        Assertions.assertTrue (quizDTO.getFlashcardSet().contains(flashcard2));

    }

    @Test
    void whenConvertingToQuiz_QuizFieldsMatchOriginalObject(){
        User nick = new User(0, "mscott@dunder.com","password","Michael","Scott",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard1 = new Flashcard(0,nick,"Whats my favorite color?",1,1,Timestamp.valueOf(LocalDateTime.now()),null);
        Flashcard flashcard2 = new Flashcard(0,nick,"Can I go to the bathroom?",1,1,Timestamp.valueOf(LocalDateTime.now()),null);
        Set<Flashcard> deck = new HashSet<>();
        deck.add(flashcard1);
        deck.add(flashcard2);

        QuizDTO quizDTO = new QuizDTO(0,nick,"testQuiz",deck);
        Quiz quiz = QuizDTO.DTOToQuiz().apply(quizDTO);

        Assertions.assertEquals(0,quiz.getQuizId());
        Assertions.assertEquals("mscott@dunder.com",quiz.getQuizUser().getEmail());
        Assertions.assertEquals("testQuiz",quiz.getQuizName());
        Assertions.assertTrue (quiz.getFlashcards().contains(flashcard1));
        Assertions.assertTrue (quiz.getFlashcards().contains(flashcard2));

    }

}
