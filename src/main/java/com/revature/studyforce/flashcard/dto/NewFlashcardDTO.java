package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object used to receive the necessary information to create a new Flashcard {@link com.revature.studyforce.flashcard.model.Flashcard}
 * @author Edson Rodriguez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFlashcardDTO {
    private int userId;
    private int topicId;
    private String question;
    private int difficulty;
}
