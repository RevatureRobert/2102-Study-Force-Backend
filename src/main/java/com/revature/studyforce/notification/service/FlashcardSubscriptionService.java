package com.revature.studyforce.notification.service;

import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.FlashcardSubscriptionId;
import com.revature.studyforce.notification.repository.FlashcardSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FlashcardSubscriptionService {

    private FlashcardSubscriptionRepository flashcardSubscriptionRepository;
    private SubscriptionService subscriptionService;
    private FlashcardRepository flashcardRepository;

    @Autowired
    public FlashcardSubscriptionService(FlashcardSubscriptionRepository flashcardSubscriptionRepository,
                                        SubscriptionService subscriptionService,
                                        FlashcardRepository flashcardRepository){
        this.flashcardSubscriptionRepository = flashcardSubscriptionRepository;
        this.subscriptionService = subscriptionService;
        this.flashcardRepository = flashcardRepository;
    }

    public FlashcardSubscription getFlashcardSubscriptionByFlashcardAndUserId(Integer flashcardId, Integer userId){
        return flashcardSubscriptionRepository.findByFlashcardSubscriptionId(
            new FlashcardSubscriptionId(
                    Objects.requireNonNull(flashcardRepository.findById(flashcardId).orElse(null)).getId(),
                    subscriptionService.getSubscriptionByUserId(userId).getId()
            )
        );
    }

    public List<FlashcardSubscription> getAllSubscribersByFlashcardId(Integer flashcardId){
        return flashcardSubscriptionRepository.findAllByFlashcard_Id(flashcardId);
    }

    public List<FlashcardSubscription> getAllSubscriptionsByUserId(Integer userId){
        return flashcardSubscriptionRepository.findAllBySubscription_User_UserId(userId);
    }

    public FlashcardSubscription createFlashcardSubscription(Integer flashcardId, Integer userId){
        return flashcardSubscriptionRepository.save(
            new FlashcardSubscription(
                    Objects.requireNonNull(flashcardRepository.findById(flashcardId).orElse(null)),
                    subscriptionService.getSubscriptionByUserId(userId)
            )
        );
    }

    public FlashcardSubscription deleteFlashcardSubscription(Integer flashcardId, Integer userId){
        FlashcardSubscription subscription = new FlashcardSubscription(
            Objects.requireNonNull(flashcardRepository.findById(flashcardId).orElse(null)),
            subscriptionService.getSubscriptionByUserId(userId)
        );
        flashcardSubscriptionRepository.delete(subscription);
        return subscription;
    }

    public List<FlashcardSubscription> deleteAllFlashcardSubscriptions(List<FlashcardSubscription> subscriptions){
        flashcardSubscriptionRepository.deleteAll(subscriptions);
        return subscriptions;
    }

    public List<FlashcardSubscription> deleteAllFlashcardSubscriptionsByFlashcardId(Integer flashcardId){
        List<FlashcardSubscription> subscriptions = getAllSubscribersByFlashcardId(flashcardId);
        return deleteAllFlashcardSubscriptions(subscriptions);
    }

    public List<FlashcardSubscription> deleteAllFlashcardSubscriptionsByUserId(Integer userId){
        List<FlashcardSubscription> subscriptions = getAllSubscriptionsByUserId(userId);
        return deleteAllFlashcardSubscriptions(subscriptions);
    }
}
