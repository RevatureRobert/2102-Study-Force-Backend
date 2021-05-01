package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;
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
    @Positive
    private int userId;
    private String quizName;
    private Set<Flashcard> flashcardSet;


    public static Function<Quiz,QuizDTO> quizToDTO(){
        return (quiz) -> {
            Assert.notNull(quiz.getQuizId(), "Quiz id is null...");
            Assert.notNull(quiz.getUserId(), "Quiz has no user Id...");
            Assert.notNull(quiz.getQuizName(), "Quiz has no name...");

            return new QuizDTO(
                    quiz.getQuizId(),
                    quiz.getUserId(),
                    quiz.getQuizName(),
                    quiz.getFlashcards()
            );
        };
    }

}
