package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardAdminViewDTO {
    private int flashcardId;
    private int userId;

}
