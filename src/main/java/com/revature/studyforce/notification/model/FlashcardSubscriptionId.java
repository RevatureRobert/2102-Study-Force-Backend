package com.revature.studyforce.notification.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Data
public class FlashcardSubscriptionId implements Serializable {

    private Integer subscription;
    private Integer flashcard;

    public FlashcardSubscriptionId(Integer flashcard, Integer subscription){
        this.flashcard = flashcard;
        this.subscription = subscription;
    }
}
