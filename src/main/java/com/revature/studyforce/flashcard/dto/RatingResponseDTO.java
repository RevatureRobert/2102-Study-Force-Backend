package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Positive;

/**
 * Data transfer object used as response for the creation of a new rating
 * It returns the new Rating information
 * @author Edson Rodriguez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDTO {
    @Positive
    private int totalRatings;
    @Positive
    private int rating;
}
