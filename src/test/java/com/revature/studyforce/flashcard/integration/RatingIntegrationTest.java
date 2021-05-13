package com.revature.studyforce.flashcard.integration;

import com.revature.studyforce.flashcard.controller.RatingController;
import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.RatingRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
 * Test class for RatingController {@link RatingController}
 * @author Edson Rodriguez
 */
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(username = "test@test.test",authorities = "ROLE_USER")
class RatingIntegrationTest {

    @Autowired
    private RatingController ratingController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    void givenRating_whenCreateRating_shouldReturnRatingResponseDTO() throws Exception {
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.ROLE_USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,null,null,false);

        userRepository.save(user);
        flashcardRepository.save(flashcard);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/flashcards/ratings/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"flashcardId\":\"2\",\"userId\":\"1\",\"ratingScore\":\"2\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRatings").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").isNumber())
                .andReturn();

    }

    @Test
    void givenFlashcardIdAndUserId_whenGetAll_shouldReturnRatingDTOWithMatchingIds() throws Exception {
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.ROLE_USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,null,null,false);

        userRepository.save(user);
        flashcardRepository.save(flashcard);

        Rating rating = new Rating(0,flashcard,user, Difficulty.EASY);
        ratingRepository.save(rating);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/ratings?flashcardId=2&userId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcardId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ratingScore").value(Difficulty.EASY.difficultyValue))
                .andReturn();

    }
}
