package com.revature.studyforce.flashcard.integration;

import com.google.gson.Gson;
import com.revature.studyforce.flashcard.controller.QuizController;
import com.revature.studyforce.flashcard.dto.NewQuizDTO;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.QuizRepository;
import com.revature.studyforce.flashcard.repository.TopicRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
    private FlashcardRepository flashcardRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;



    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();
        User mscott = new User(0,"mscott@dunder.com","password","Michael","Scott",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(mscott);

        Topic t = new Topic(0,"java");
        topicRepository.save(t);

        Flashcard flashcard1 = new Flashcard(0,mscott,t,"Whats my favorite color?",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Flashcard flashcard2 = new Flashcard(0,mscott,t,"Can I go to the bathroom?",2,2,Timestamp.valueOf(LocalDateTime.now()),null,false);
        flashcardRepository.save(flashcard1);
        flashcardRepository.save(flashcard2);

        List<Flashcard> deck = new ArrayList<>();
        deck.add(flashcard1);
        deck.add(flashcard2);

        Quiz testingQuiz = new Quiz(0,mscott,"demoQuiz",deck);
        System.out.println(testingQuiz);
        quizRepository.save(testingQuiz);
    }

    @Test
    void givenQuiz_whenGetAll_thenQuizzesRetrievedWithPagination() throws Exception {
//        Optional<User> tester = userRepository.findById(1);
//        System.out.println(tester);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/quiz")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].quizId").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].quizUserId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].quizName").value("demoQuiz"))
                .andReturn();


    System.out.println(result);
    }

    @Test
    void givenQuiz_whenCreateQuiz_NewQuizIsRetrieved() throws Exception {
        User dwight = new User(0,"dshrute@dunder.com","password","Dwight","Schrute",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(dwight);

        Topic t = new Topic(0,"farming");
        topicRepository.save(t);

        Flashcard flashcard3 = new Flashcard(0,dwight,t,"Whats my favorite color?",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Flashcard flashcard4 = new Flashcard(0,dwight,t,"What time is improv?",2,2,Timestamp.valueOf(LocalDateTime.now()),null,false);
        flashcardRepository.save(flashcard3);
        flashcardRepository.save(flashcard4);

        List<Integer> deck = new ArrayList<>();
        deck.add(flashcard3.getId());
        deck.add(flashcard4.getId());


        NewQuizDTO testingQuiz2 = new NewQuizDTO(dwight.getUserId(),"demoQuiz",deck);
        System.out.println(new Gson().toJson(testingQuiz2));


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/flashcards/quiz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testingQuiz2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println(result);
    }

    @Test
    void givenQuiz_whenUpdateQuiz_QuizIsMutated() throws Exception {

    }

    @Test
    void givenQuizId_DeleteQuiz() throws Exception {

    }

}
