package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import javax.validation.constraints.PositiveOrZero;
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
    private User quizUser;
    private String quizName;
    private List<Flashcard> flashcards;


    public static Function<Quiz,QuizDTO> quizToDTO(){
        return (quiz) -> {
            Assert.notNull(quiz.getQuizId(), "Quiz id is null...");
            Assert.notNull(quiz.getQuizUser(), "Quiz has no associated user...");
            Assert.notNull(quiz.getQuizName(), "Quiz has no name...");

            return new QuizDTO(
                    quiz.getQuizId(),
                    quiz.getQuizUser(),
                    quiz.getQuizName(),
                    quiz.getFlashcards()
            );
        };
    }

    /**
     * Supplies a function that converts a quizDTO to a Quiz object
     * @return a function
     */
    public static Function<QuizDTO,Quiz> DTOToQuiz(){
        return (quizDTO) -> {
            Assert.notNull(quizDTO, "quizDTO is null");

            return new Quiz(
                    quizDTO.getQuizId(),
                    quizDTO.getQuizUser(),
                    quizDTO.getQuizName(),
                    quizDTO.getFlashcards());
        };
    }

}
