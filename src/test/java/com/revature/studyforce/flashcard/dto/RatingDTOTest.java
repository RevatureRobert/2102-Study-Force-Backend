package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Test class for the RatingDTO method {@link RatingDTO}
 * @author Edson Rodriguez
 */
@SpringBootTest
//@TestPropertySource(locations = "classpath:test-application.properties")
class RatingDTOTest {

    @Test
    void whenConvertingToDTO_DTOFieldsMatchOriginalObject(){
        User edson = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.ROLE_USER,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,edson,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null, false);
        Rating rating = new Rating(0,flashcard,edson, Difficulty.EASY);
        RatingDTO r = RatingDTO.ratingToDTO().apply(rating);

        Assertions.assertEquals(0,r.getFlashcardId());
        Assertions.assertEquals(0,r.getUserId());
        Assertions.assertEquals(1,r.getRatingScore());
    }
}
