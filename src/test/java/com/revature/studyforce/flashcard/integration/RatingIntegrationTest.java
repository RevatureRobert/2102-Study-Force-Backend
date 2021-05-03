package com.revature.studyforce.flashcard.integration;

import com.revature.studyforce.flashcard.controller.RatingController;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.FlashcardRepo;
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
import java.time.LocalDateTime;

/**
 * @author Edson Rodriguez
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class RatingIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private RatingController ratingController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlashcardRepo flashcardRepo;

    @Test
    void givenRating_whenCreateRating() throws Exception {
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null);

        userRepository.save(user);
        flashcardRepo.save(flashcard);

        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/flashcards/rate/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"flashcardId\":\"2\",\"userId\":\"1\",\"ratingScore\":\"2\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRatings").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").isNumber())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());

    }
}
