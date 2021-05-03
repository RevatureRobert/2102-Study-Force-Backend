package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.function.Function;

/**
 * Data transfer object for Rating {@link Rating}
 * @author Edson Rodriguez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    @NotNull
    @PositiveOrZero
    private int flashcardId;
    @NotNull
    @Positive
    private int userId;
    @NotNull
    @Positive
    private int ratingScore;

    /**
     * Method used to create a RatingDTO from a Rating object
     * @return a data transfer object that represents the rating object
     */
    public static Function<Rating,RatingDTO> ratingToDTO(){
        return rating -> {
            Assert.notNull(rating.getFlashcard(),"Flashcard object is null");
            Assert.notNull(rating.getUser(),"User object is null");

            return new RatingDTO(
                    rating.getFlashcard().getId(),
                    rating.getUser().getUserId(),
                    rating.getRatingValue().difficultyValue
            );
        };
    }
}
