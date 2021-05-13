package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.RatingDTO;
import com.revature.studyforce.flashcard.dto.RatingResponseDTO;
import com.revature.studyforce.flashcard.model.*;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.RatingRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Test class for RatingService {@link RatingService}
 * @author Edson Rodriguez
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
class RatingServiceTest {

    @MockBean
    private RatingRepository ratingRepository;
    @MockBean
    private FlashcardRepository flashcardRepository;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RatingService ratingService;

    @Test
    void givenRatingDTO_whenCreateRating_shouldReturnRatingResponseDTO(){
        List<Rating> rList = new ArrayList<>();
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.ROLE_USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Rating rating = new Rating(0,flashcard,user, Difficulty.EASY);
        rList.add(rating);

        Mockito.when(flashcardRepository.findById(0)).thenReturn(Optional.of(flashcard));
        Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(ratingRepository.findByFlashcardId(0)).thenReturn(rList);
        Mockito.when(ratingRepository.save(org.mockito.ArgumentMatchers.isA(Rating.class))).thenReturn(rating);

        RatingResponseDTO res = ratingService.createRating(RatingDTO.ratingToDTO().apply(rating));
        assertNotNull(res);
        assertEquals(1, res.getRating());
        assertTrue(res.getTotalRatings()>0);

    }

    @Test
    void givenFlashcardIdAndUserId_whenGetRating_shouldReturnRating(){
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.ROLE_USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Rating rating = new Rating(0,flashcard,user, Difficulty.EASY);

        Mockito.when(flashcardRepository.findById(0)).thenReturn(Optional.of(flashcard));
        Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(ratingRepository.findByFlashcard_idAndUser_userId(0,0)).thenReturn(Optional.of(rating));

        RatingDTO ratingDTO = ratingService.getRating(0,0);

        assertNotNull(ratingDTO);
        assertEquals(user.getUserId(),ratingDTO.getUserId());
        assertEquals(flashcard.getId(),ratingDTO.getFlashcardId());
        assertEquals(rating.getRatingValue().difficultyValue,ratingDTO.getRatingScore());
    }

    @Test
    void givenFlashcardId_whenGetAllRatings_shouldReturnListOfRatingDTO() {
        List<Rating> ratings = new ArrayList<>();
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.ROLE_USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        Rating rating = new Rating(0,flashcard,user, Difficulty.EASY);
        ratings.add(rating);

        Mockito.when(flashcardRepository.findById(0)).thenReturn(Optional.of(flashcard));
        Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(ratingRepository.findByFlashcardId(0)).thenReturn(ratings);
        Mockito.when(ratingRepository.save(org.mockito.ArgumentMatchers.isA(Rating.class))).thenReturn(rating);

        List<Rating> res = ratingRepository.findByFlashcardId(flashcard.getId());
        assertNotNull(res);
        assertEquals(ratings, res);
    }

    // (may use in the future)
//    @Test
//    void givenRatingId_whenDelete_shouldDeleteRating() {
//        ratingService.delete(0);
//        Mockito.verify(ratingRepository, Mockito.times(0)).deleteById(0);
//    }
}
