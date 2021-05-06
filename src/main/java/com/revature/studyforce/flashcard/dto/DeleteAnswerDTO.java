package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Data transfer object used to delete a existing answer
 * @author Edson Rodriguez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAnswerDTO {
    @Positive
    private int answerId;
}
