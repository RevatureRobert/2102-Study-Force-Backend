package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

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
