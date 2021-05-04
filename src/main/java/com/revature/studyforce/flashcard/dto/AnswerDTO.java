package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


/**
 * Data transfer object used to create a new Answer
 * @author Edson Rodriguez and Mikayla Pickett
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

    @NotNull
    @Positive
    private int userId;
    @NotNull
    @Positive
    private int flashcardId;
    @NotNull
    private String answer;

}
