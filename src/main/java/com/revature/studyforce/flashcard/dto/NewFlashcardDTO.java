package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewFlashcardDTO {
    private int userId;
    private int topicID;
    private String question;
    private int difficulty;
}
