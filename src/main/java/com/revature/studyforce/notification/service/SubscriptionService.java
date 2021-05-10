package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.dto.SubscriptionDTO;
import com.revature.studyforce.notification.repository.SubscriptionRepository;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * Service Layer for Subscriptions {@link SubscriptionRepository}
 * @author Brandon Pinkerton
 */
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserRepository userRepository){
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a subscription using {@link SubscriptionRepository#save(Object)}
     * @param subscriptionDTO A subscription to be created.
     * @return The subscription that was created.
     */
    public Subscription createSubscription(SubscriptionDTO subscriptionDTO){
    Subscription subscription =
        new Subscription(
            subscriptionDTO.getId(),
            userRepository.findById(subscriptionDTO.getUserId()).orElse(null),
            subscriptionDTO.getEndpoint(),
            subscriptionDTO.getKey(),
            subscriptionDTO.getAuth());
        System.out.println(subscription);
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
     * Retrieves a subscription by subscriptionId from {@link SubscriptionRepository#findById(Object)} }
     * @param subscriptionId the userId of the subscription to be retrieved.
     * @return The subscription of the user.
     */
    public Optional<Subscription> getSubscriptionById(Integer subscriptionId){
        return subscriptionRepository.findById(subscriptionId);
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
