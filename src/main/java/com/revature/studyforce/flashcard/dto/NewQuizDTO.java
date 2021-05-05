package com.revature.studyforce.flashcard.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewQuizDTO {
    @NotNull
    private int quizUserId;
    @NotNull
    private String quizName;
    @NotNull
    private List<Integer> flashcardsId;
}
