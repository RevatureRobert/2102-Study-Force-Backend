package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * The data transfer object used to update a Quiz {@link com.revature.studyforce.flashcard.model.Quiz}
 * @author Edson Rodriguez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuizDTO {
    @PositiveOrZero
    private int quizId;
    private int quizUserId;
    private String quizName;
    private List<Integer> flashcardsId;
}
