package com.revature.studyforce.notification.service;

import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.notification.model.FlashcardSubscriptionId;
import com.revature.studyforce.notification.repository.FlashcardSubscriptionRepository;
import com.revature.studyforce.notification.repository.StacktraceSubscriptionRepository;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscriptionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service Layer for StacktraceSubscriptions {@link StacktraceSubscriptionRepository}
 * @author Brandon Pinkerton
 */
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


    /**
     * Retrieves a stacktraceSubscription by composite of stacktraceId and userId
     * from {@link StacktraceSubscriptionRepository#findAllByStacktraceSubscriptionId(StacktraceSubscriptionId)}
     * @param stacktraceId The stacktraceId of the flashcard to be retrieved.
     * @param userId The userId of the subscription to be retrieved.
     * @return The stacktrace subscription of that stacktrace for that user.
     */
    public StacktraceSubscription getStacktraceSubscriptionByStacktraceAndUserId(Integer stacktraceId, Integer userId){
        return stacktraceSubscriptionRepository.findAllByStacktraceSubscriptionId(
          new StacktraceSubscriptionId(
                  Objects.requireNonNull(stacktraceRepository.findById(stacktraceId).orElse(null)).getStacktraceId(),
                  subscriptionService.getSubscriptionByUserId(userId).getId()
          )
        );
    }

    /**
     * Retrieves all stacktrace subscribers for a given stacktrace {@link StacktraceSubscriptionRepository#findAllByStacktrace_StacktraceId(Integer)}
     * @param stacktraceId The stacktraceId of the stacktrace to retrieve all of the subscriptions to it
     * @return A list of all stacktrace subscriptions to a specific stacktrace
     */
    public List<StacktraceSubscription> getAllSubscribersByStacktraceId(Integer stacktraceId){
        return stacktraceSubscriptionRepository.findAllByStacktrace_StacktraceId(stacktraceId);
    }

    /**
     * Retrieves all stacktraces subscriptions for a given user {@link StacktraceSubscriptionRepository#findAllBySubscription_User_UserId(Integer)}
     * @param userId The userId of the user to retrieve all of their subscriptions
     * @return A list of all stacktraces subscriptions to a specific user
     */
    public List<StacktraceSubscription> getAllSubscriptionsByUserId(Integer userId){
        return stacktraceSubscriptionRepository.findAllBySubscription_User_UserId(userId);
    }

    /**
     * Creates a stacktrace subscription for a given stacktrace and user subscription {@link StacktraceSubscriptionRepository#save(Object)}
     * The subscription is built by grabbing the stacktrace from {@link StacktraceRepository#findById(Object)}
     * And the user subscription from {@link SubscriptionService#getSubscriptionByUserId(Integer)}
     * @param stacktraceId The stacktraceId of the stacktrace to be subscribed to
     * @param userId The userId of the user to be subscribed
     * @return The newly created stacktrace subscription.
     */
    public StacktraceSubscription createStacktraceSubscription(Integer stacktraceId, Integer userId){
        return stacktraceSubscriptionRepository.save(
          new StacktraceSubscription(
                  Objects.requireNonNull(stacktraceRepository.findById(stacktraceId).orElse(null)),
                  subscriptionService.getSubscriptionByUserId(userId)
          )
        );
    }

    /**
     * Deletes a stacktrace subscription for a given stacktrace and user subscription {@link StacktraceSubscriptionRepository#delete(Object)}
     * The subscription is built by grabbing the stacktrace from {@link StacktraceSubscriptionRepository#findById(Object)}
     * And the user subscription from {@link SubscriptionService#getSubscriptionByUserId(Integer)}
     * @param stacktraceId The stacktraceId of the flashcard to be unsubscribed from
     * @param userId The userId of the user to be unsubscribed
     * @return The deleted stacktrace subscription.
     */
    public StacktraceSubscription deleteStacktraceSubscription(Integer stacktraceId, Integer userId){
        StacktraceSubscription subscription = new StacktraceSubscription(
          Objects.requireNonNull(stacktraceRepository.findById(stacktraceId).orElse(null)),
          subscriptionService.getSubscriptionByUserId(userId)
        );
        stacktraceSubscriptionRepository.delete(subscription);
        return subscription;
    }

    /**
     * Deletes a list of subscriptions {@link StacktraceSubscriptionRepository#deleteAll(Iterable)}
     * @param subscriptions The list of subscriptions to be deleted.
     * @return The list of deleted subscriptions.
     */
    public List<StacktraceSubscription> deleteAllStacktraceSubscriptions(List<StacktraceSubscription> subscriptions){
        stacktraceSubscriptionRepository.deleteAll(subscriptions);
        return subscriptions;
    }

    /**
     * Generates a list of subscriptions for a given stacktrace to be deleted by /deleteAllStacktraceSubscriptions
     * @param stacktraceId The stacktraceId of the stacktrace to delete all subscribers.
     * @return The list of deleted subscriptions.
     */
    public List<StacktraceSubscription> deleteAllStacktraceSubscriptionByStacktraceId(Integer stacktraceId){
        List<StacktraceSubscription> subscriptions = getAllSubscribersByStacktraceId(stacktraceId);
        return deleteAllStacktraceSubscriptions(subscriptions);
    }

    /**
     * Generates a list of subscriptions for a given user to be deleted by /deleteAllStacktraceSubscriptions
     * @param userId The userId of the user to delete all subscriptions.
     * @return The list of deleted subscriptions.
     */
    public List<StacktraceSubscription> deleteAllStacktraceSubscriptionsByUserId(Integer userId){
        List<StacktraceSubscription> subscriptions = getAllSubscriptionsByUserId(userId);
        return deleteAllStacktraceSubscriptions(subscriptions);
    }
}
