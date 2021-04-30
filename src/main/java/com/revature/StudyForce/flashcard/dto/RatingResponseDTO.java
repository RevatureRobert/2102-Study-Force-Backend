package com.revature.StudyForce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.validation.constraints.Positive;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDTO {
    @Positive
    private int totalRatings;
    @Positive
    private int rating;
}
