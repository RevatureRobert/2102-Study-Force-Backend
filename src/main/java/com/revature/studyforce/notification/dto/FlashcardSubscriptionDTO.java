package com.revature.studyforce.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardSubscriptionDTO {
    private Integer flashcardId;
    private Integer userId;
}
