package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.flashcard.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.function.Function;

/**
 * Flashcard Data Transfer Object
 *
 * @author Luke
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardDTO {
    private User creator;
    private Topic topic;
    private String question;
    private int questionDifficultyTotal;
    private int questionDifficultyAverage;
    private Timestamp createdTime;
    private Timestamp resolutionTime;

    /**
     * Converts Flashcard to FlashcardDTO
     * @return - FlashcardDTO
     */
    public static Function<Flashcard, FlashcardDTO> convertToDTO() {
        return (flashcard) -> {

            Assert.notNull(flashcard, "Flashcard is null");

            return new FlashcardDTO(
                    flashcard.getCreator(),
                    flashcard.getTopic(),
                    flashcard.getQuestion(),
                    flashcard.getQuestionDifficultyTotal(),
                    flashcard.getQuestionDifficultyAverage(),
                    flashcard.getCreatedTime(),
                    flashcard.getResolutionTime()
            );
        };
    }

    /**
     * Converts FlashcardDTO to Flashcard
     * @return - Flashcard
     */
    public static Function<FlashcardDTO, Flashcard> convertFromDTO() {
        return (flashcardDTO) -> {

            Assert.notNull(flashcardDTO, "Flashcard DTO is null");

            return new Flashcard(
                    0,
                    flashcardDTO.getCreator(),
                    flashcardDTO.getTopic(),
                    flashcardDTO.getQuestion(),
                    flashcardDTO.getQuestionDifficultyTotal(),
                    flashcardDTO.getQuestionDifficultyAverage(),
                    flashcardDTO.getCreatedTime(),
                    flashcardDTO.getResolutionTime()
            );
        };
    }
}
