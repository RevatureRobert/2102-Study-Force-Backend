package com.revature.studyforce.notification.model;

import com.revature.studyforce.flashcard.model.Flashcard;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The stack Trace subscription model that holds a link to both the subscription and flashcard
 * @author Brandon Pinkerton
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "flashcard_subscriptions")
public class FlashcardSubscription {


    /**
     * The composite Key
     */
    @EmbeddedId
    private FlashcardSubscriptionId flashcardSubscriptionId;

    @OneToOne
    private Flashcard flashcard;
    @OneToOne
    private Subscription subscription;

    public FlashcardSubscription(Flashcard flashcard, Subscription subscription){
        this.flashcardSubscriptionId = new FlashcardSubscriptionId(flashcard.getId(), subscription.getId());

        this.flashcard = flashcard;
        this.subscription = subscription;
    }
}
