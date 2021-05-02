package com.revature.studyforce.flashcard.service;



import static org.junit.jupiter.api.Assertions.*;



import com.revature.studyforce.flashcard.dto.RatingDTO;
import com.revature.studyforce.flashcard.dto.RatingResponseDTO;
import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.RatingRepository;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Edson Rodriguez
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
class RatingServiceTest {

    @MockBean
    private RatingRepository ratingRepository;
    @MockBean
    private FlashcardRepository flashcardRepo;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RatingService ratingService;

    @Test
    void whenCreateRating_returnsRatingResponseDTO(){
        List<Rating> rList = new ArrayList<>();
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null);
        Rating rating = new Rating(0,flashcard,user, Difficulty.EASY);
        rList.add(rating);

        Mockito.when(flashcardRepo.findById(0)).thenReturn(Optional.of(flashcard));
        Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user));
        Mockito.when(ratingRepository.findByFlashcard_id(0)).thenReturn(rList);
        Mockito.when(ratingRepository.save(org.mockito.ArgumentMatchers.isA(Rating.class))).thenReturn(rating);

        RatingResponseDTO res = ratingService.createRating(RatingDTO.ratingToDTO().apply(rating));
        assertNotNull(res);
        assertTrue(res.getRating()>0);
        assertTrue(res.getTotalRatings()>0);

        System.out.println(res);
    }
}