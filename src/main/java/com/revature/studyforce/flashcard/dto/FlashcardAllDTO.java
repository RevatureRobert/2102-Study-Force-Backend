package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.function.Function;

/**
 * Data transfer object with the minimum information to display a flashcard on the front-end
 * @author Edson Rodriguez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardAllDTO {
    @Positive
    private int flashcardId;
    @Positive
    private int creatorId;
    @NotNull
    private String topicName;
    @NotNull
    private String question;
    @Positive
    private int difficulty;
    private boolean isResolved;

    /**
     * Converts Flashcard to FlashcardDTO
     * @return - FlashcardDTO
     */
    public static Function<Flashcard, FlashcardAllDTO> convertToDTO() {
        return flashcard -> {

            Assert.notNull(flashcard, "Flashcard is null");

            return new FlashcardAllDTO(
                    flashcard.getId(),
                    flashcard.getCreator().getUserId(),
                    flashcard.getTopic().getTopicName(),
                    flashcard.getQuestion(),
                    flashcard.getQuestionDifficultyAverage(),
                    flashcard.isResolved()
            );
        };
    }
}
