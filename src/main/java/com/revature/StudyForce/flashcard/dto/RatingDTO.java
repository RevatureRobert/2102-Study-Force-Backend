package com.revature.StudyForce.flashcard.dto;

import com.revature.StudyForce.flashcard.model.Difficulty;
import com.revature.StudyForce.flashcard.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {

    @PositiveOrZero
    private int flashcardId;
    @Positive
    private int userId;
    @Positive
    private Difficulty ratingScore;


    public static Function<Rating,RatingDTO> ratingToDTO(){
        return (rating) -> {
            //TODO:Assert if flashcard is not null
            return new RatingDTO(
                    //TODO:change for the flashcard getId method
                    //rating.getFlashcard()
                    1,
                    rating.getUser().getUserId(),
                    rating.getRatingValue()
            );
        };
    }
}
