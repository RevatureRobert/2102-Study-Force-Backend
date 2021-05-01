package com.revature.StudyForce.flashcard.dto;

import com.revature.StudyForce.flashcard.model.Difficulty;
import com.revature.StudyForce.flashcard.model.Flashcard;
import com.revature.StudyForce.flashcard.model.Rating;
import com.revature.StudyForce.user.model.Authority;
import com.revature.StudyForce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class RatingDTOTest {

    @Test
    void whenConvertingToDTO_DTOFieldsMatchOriginalObject(){
        User edson = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,edson,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null);
        Rating rating = new Rating(0,flashcard,edson, Difficulty.EASY);
        RatingDTO r = RatingDTO.ratingToDTO().apply(rating);

        Assertions.assertEquals(0,r.getFlashcardId());
        Assertions.assertEquals(0,r.getUserId());
        Assertions.assertEquals(1,r.getRatingScore());
    }
}
