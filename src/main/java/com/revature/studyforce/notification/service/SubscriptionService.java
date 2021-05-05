package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription createSubscription(Subscription subscription){
        return subscriptionRepository.save(subscription);
    }

    public Subscription getSubscriptionByUserId(Integer userId){
        return subscriptionRepository.findByUser_UserId(userId);
    }

    public Subscription deleteSubscriptionByUserId(Integer userId) {
        Subscription subscription = subscriptionRepository.findByUser_UserId(userId);
        subscriptionRepository.delete(subscription);
        return subscription;
    }
}
