package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.repository.SubscriptionRepository;
import com.revature.studyforce.notification.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Layer for Subscriptions {@link SubscriptionRepository}
 * @author Brandon Pinkerton
 */
@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Creates a subscription using {@link SubscriptionRepository#save(Object)}
     * @param subscription A subscription to be created.
     * @return The subscription that was created.
     */
    public Subscription createSubscription(Subscription subscription){
        return subscriptionRepository.save(subscription);
    }

    /**
     * Retrieves a subscription by userId from {@link SubscriptionRepository#findByUser_UserId(Integer)}
     * @param userId the userId of the subscription to be retrieved.
     * @return The subscription of the user.
     */
    public Subscription getSubscriptionByUserId(Integer userId){
        return subscriptionRepository.findByUser_UserId(userId);
    }

    /**
     * Deletes a subscription by userId from {@link SubscriptionRepository#delete(Object)}
     * @param userId The userId to retrieve and delete their subscription
     * @return The subscription that was deleted.
     */
    public Subscription deleteSubscriptionByUserId(Integer userId) {
        Subscription subscription = subscriptionRepository.findByUser_UserId(userId);
        subscriptionRepository.delete(subscription);
        return subscription;
    }
}
