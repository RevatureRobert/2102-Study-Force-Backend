package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Data transfer object for Quiz {@link Quiz}
 * @author Nick Zimmerman
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {

    @PositiveOrZero
    private int quizId;
    private int quizUserId;
    private String quizName;
    private List<FlashcardAllDTO> flashcards;

    /**
     * Creates a function that will convert a Quiz into a QuizDTO
     * @return the function that will convert a Quiz into a QuizDTO
     */
    public static Function<Quiz,QuizDTO> quizToDTO(){
        return quiz -> {
            Assert.notNull(quiz.getQuizUser(), "Quiz has no associated user...");
            Assert.notNull(quiz.getQuizName(), "Quiz has no name...");

            List<FlashcardAllDTO> newList = new ArrayList<>();
            quiz.getFlashcards().forEach((flashcard -> newList.add(FlashcardAllDTO.convertToDTO().apply(flashcard))));

            return new QuizDTO(
                    quiz.getQuizId(),
                    quiz.getQuizUser().getUserId(),
                    quiz.getQuizName(),
                    newList
            );
        };
    }
}
