package com.revature.studyforce.flashcard.integration;

import com.revature.studyforce.flashcard.controller.QuizController;
import com.revature.studyforce.flashcard.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for Quiz model
 * @author Nick Zimmerman
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class QuizIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private QuizController quizController;

    @Autowired
    private QuizRepository quizRepository;


    @Test
    void givenQuiz_whenGetAll_thenQuizzesRetrievedWithPagination() throws Exception {

    }

    @Test
    void givenQuiz_whenCreateQuiz_NewQuizIsRetrieved() throws Exception {

    }

    @Test
    void givenQuiz_whenUpdateQuiz_QuizIsMutated() throws Exception {

    }

    @Test
    void givenQuizId_DeleteQuiz() throws Exception {

    }

}
