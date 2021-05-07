package com.revature.studyforce.notification.service;

import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.FlashcardSubscriptionId;
import com.revature.studyforce.notification.repository.FlashcardSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service Layer for FlashcardSubscriptions {@link FlashcardSubscriptionRepository}
 * @author Brandon Pinkerton
 */
@Service
public class FlashcardSubscriptionService {

    private final FlashcardSubscriptionRepository flashcardSubscriptionRepository;
    private final SubscriptionService subscriptionService;
    private final FlashcardRepository flashcardRepository;

    @Autowired
    public FlashcardSubscriptionService(FlashcardSubscriptionRepository flashcardSubscriptionRepository,
                                        SubscriptionService subscriptionService,
                                        FlashcardRepository flashcardRepository){
        this.flashcardSubscriptionRepository = flashcardSubscriptionRepository;
        this.subscriptionService = subscriptionService;
        this.flashcardRepository = flashcardRepository;
    }

    /**
     * Retrieves a FlashcardSubscription by composite of flashcardId and userId
     * from {@link FlashcardSubscriptionRepository#findByFlashcardSubscriptionId(FlashcardSubscriptionId)}
     * @param flashcardId The flashcardId of the flashcard to be retrieved.
     * @param userId The userId of the subscription to be retrieved.
     * @return The flashcard subscription of that flashcard for that user.
     */
    public FlashcardSubscription getFlashcardSubscriptionByFlashcardAndUserId(Integer flashcardId, Integer userId){
        return flashcardSubscriptionRepository.findByFlashcardSubscriptionId(
            new FlashcardSubscriptionId(
                    Objects.requireNonNull(flashcardRepository.findById(flashcardId).orElse(null)).getId(),
                    subscriptionService.getSubscriptionByUserId(userId).getId()
            )
        );
    }

    /**
     * Retrieves all flashcard subscribers for a given flashcard {@link FlashcardSubscriptionRepository#findAllByFlashcard_Id(Integer)}
     * @param flashcardId The flashcardId of the flashcard to retrieve all of the subscriptions to it
     * @return A list of all flashcard subscriptions to a specific flashcard
     */
    public List<FlashcardSubscription> getAllSubscribersByFlashcardId(Integer flashcardId){
        return flashcardSubscriptionRepository.findAllByFlashcard_Id(flashcardId);
    }

    /**
     * Retrieves all flashcard subscriptions for a given user {@link FlashcardSubscriptionRepository#findAllBySubscription_User_UserId(Integer)}
     * @param userId The userId of the user to retrieve all of their subscriptions
     * @return A list of all flashcard subscriptions to a specific user
     */
    public List<FlashcardSubscription> getAllSubscriptionsByUserId(Integer userId){
        return flashcardSubscriptionRepository.findAllBySubscription_User_UserId(userId);
    }

    /**
     * Creates a flashcard subscription for a given flashcard and user subscription {@link FlashcardSubscriptionRepository#save(Object)}
     * The subscription is built by grabbing the flashcard from {@link FlashcardRepository#findById(Object)}
     * And the user subscription from {@link SubscriptionService#getSubscriptionByUserId(Integer)}
     * @param flashcardId The flashcardId of the flashcard to be subscribed to
     * @param userId The userId of the user to be subscribed
     * @return The newly created flashcard subscription.
     */
    public FlashcardSubscription createFlashcardSubscription(Integer flashcardId, Integer userId){
        return flashcardSubscriptionRepository.save(
            new FlashcardSubscription(
                    Objects.requireNonNull(flashcardRepository.findById(flashcardId).orElse(null)),
                    subscriptionService.getSubscriptionByUserId(userId)
            )
        );
    }

    /**
     * Deletes a flashcard subscription for a given flashcard and user subscription {@link FlashcardSubscriptionRepository#delete(Object)}
     * The subscription is built by grabbing the flashcard from {@link FlashcardRepository#findById(Object)}
     * And the user subscription from {@link SubscriptionService#getSubscriptionByUserId(Integer)}
     * @param flashcardId The flashcardId of the flashcard to be unsubscribed from
     * @param userId The userId of the user to be unsubscribed
     * @return The deleted flashcard subscription.
     */
    public FlashcardSubscription deleteFlashcardSubscription(Integer flashcardId, Integer userId){
        FlashcardSubscription subscription = new FlashcardSubscription(
            Objects.requireNonNull(flashcardRepository.findById(flashcardId).orElse(null)),
            subscriptionService.getSubscriptionByUserId(userId)
        );
        flashcardSubscriptionRepository.delete(subscription);
        return subscription;
    }

    /**
     * Deletes a list of subscriptions {@link FlashcardSubscriptionRepository#deleteAll(Iterable)}
     * @param subscriptions The list of subscriptions to be deleted.
     * @return The list of deleted subscriptions.
     */
    public List<FlashcardSubscription> deleteAllFlashcardSubscriptions(List<FlashcardSubscription> subscriptions){
        flashcardSubscriptionRepository.deleteAll(subscriptions);
        return subscriptions;
    }

    /**
     * Generates a list of subscriptions for a given flashcard to be deleted by /deleteAllFlashcardSubscriptions
     * @param flashcardId The flashcardId of the flashcard to delete all subscribers.
     * @return The list of deleted subscriptions.
     */
    public List<FlashcardSubscription> deleteAllFlashcardSubscriptionsByFlashcardId(Integer flashcardId){
        List<FlashcardSubscription> subscriptions = getAllSubscribersByFlashcardId(flashcardId);
        return deleteAllFlashcardSubscriptions(subscriptions);
    }

    /**
     * Generates a list of subscriptions for a given user to be deleted by /deleteAllFlashcardSubscriptions
     * @param userId The userId of the user to delete all subscriptions.
     * @return The list of deleted subscriptions.
     */
    public List<FlashcardSubscription> deleteAllFlashcardSubscriptionsByUserId(Integer userId){
        List<FlashcardSubscription> subscriptions = getAllSubscriptionsByUserId(userId);
        return deleteAllFlashcardSubscriptions(subscriptions);
    }
}
