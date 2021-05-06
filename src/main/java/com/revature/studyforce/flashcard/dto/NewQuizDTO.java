package com.revature.studyforce.flashcard.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Additional QuizDTO specifically for when a new Quiz is being created
 * @author Edson Rodriguez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewQuizDTO {
    @PositiveOrZero
    private int quizUserId;
    @NotNull
    private String quizName;
    @NotNull
    private List<Integer> flashcardsId;
}
