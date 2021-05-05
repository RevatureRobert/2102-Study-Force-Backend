package com.revature.studyforce.notification.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Basic Model for a flashcard subscription compositeKey
 * @author Brandon Pinkerton
 */
@Embeddable
@NoArgsConstructor
@Data
public class FlashcardSubscriptionId implements Serializable {

    /**
     * this is the composite key holding a reference to
     * the subscription Id and a FlashCardId
     */
    private Integer subscription;
    private Integer flashcard;

    public FlashcardSubscriptionId(Integer flashcard, Integer subscription){
        this.flashcard = flashcard;
        this.subscription = subscription;
    }
}
