package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscriptionId;
import com.revature.studyforce.notification.repository.StacktraceSubscriptionRepository;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StacktraceSubscriptionService {

    private StacktraceSubscriptionRepository stacktraceSubscriptionRepository;
    private SubscriptionService subscriptionService;
    private StacktraceRepository stacktraceRepository;

    @Autowired
    public StacktraceSubscriptionService(StacktraceSubscriptionRepository stacktraceSubscriptionRepository,
                                         SubscriptionService subscriptionService,
                                         StacktraceRepository stacktraceRepository){
        this.stacktraceSubscriptionRepository = stacktraceSubscriptionRepository;
        this.subscriptionService = subscriptionService;
        this.stacktraceRepository = stacktraceRepository;
    }

    public StacktraceSubscription getStacktraceSubscriptionByStacktraceAndUserId(Integer stacktraceId, Integer userId){
        return stacktraceSubscriptionRepository.findAllByStacktraceSubscriptionId(
          new StacktraceSubscriptionId(
                  Objects.requireNonNull(stacktraceRepository.findById(stacktraceId).orElse(null)).getStacktraceId(),
                  subscriptionService.getSubscriptionByUserId(userId).getId()
          )
        );
    }

    public List<StacktraceSubscription> getAllSubscribersByStacktraceId(Integer stacktraceId){
        return stacktraceSubscriptionRepository.findAllByStacktrace_StacktraceId(stacktraceId);
    }

    public List<StacktraceSubscription> getAllSubscriptionsByUserId(Integer userId){
        return stacktraceSubscriptionRepository.findAllBySubscription_User_UserId(userId);
    }

    public StacktraceSubscription createStacktraceSubscription(Integer stacktraceId, Integer userId){
        return stacktraceSubscriptionRepository.save(
          new StacktraceSubscription(
                  Objects.requireNonNull(stacktraceRepository.findById(stacktraceId).orElse(null)),
                  subscriptionService.getSubscriptionByUserId(userId)
          )
        );
    }

    public StacktraceSubscription deleteStacktraceSubscription(Integer stacktraceId, Integer userId){
        StacktraceSubscription subscription = new StacktraceSubscription(
          Objects.requireNonNull(stacktraceRepository.findById(stacktraceId).orElse(null)),
          subscriptionService.getSubscriptionByUserId(userId)
        );
        stacktraceSubscriptionRepository.delete(subscription);
        return subscription;
    }

    public List<StacktraceSubscription> deleteAllStacktraceSubscriptions(List<StacktraceSubscription> subscriptions){
        stacktraceSubscriptionRepository.deleteAll(subscriptions);
        return subscriptions;
    }

    public List<StacktraceSubscription> deleteAllStacktraceSubscriptionByStacktraceId(Integer stacktraceId){
        List<StacktraceSubscription> subscriptions = getAllSubscribersByStacktraceId(stacktraceId);
        return deleteAllStacktraceSubscriptions(subscriptions);
    }

    public List<StacktraceSubscription> deleteAllStacktraceSubscriptionsByUserId(Integer userId){
        List<StacktraceSubscription> subscriptions = getAllSubscriptionsByUserId(userId);
        return deleteAllStacktraceSubscriptions(subscriptions);
    }
}
