package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardAllDTO {
    private int flashcardId;
    private int creatorId;
    private String topicName;
    private String question;
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
