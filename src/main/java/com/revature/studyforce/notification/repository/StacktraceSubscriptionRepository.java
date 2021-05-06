package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Reposiotry for StackTrace Subscription {@link StacktraceSubscription}
 * @author Brandon Pinkerton
 */
@Repository
public interface StacktraceSubscriptionRepository extends JpaRepository<StacktraceSubscription, Integer> {
    /**
     * Find a specfic stacktrace subscription by its ID(the compiste key {@link StacktraceSubscriptionId})
     * @param stacktraceSubscriptionId
     * @return
     */
    StacktraceSubscription findAllByStacktraceSubscriptionId(StacktraceSubscriptionId stacktraceSubscriptionId);

    /**
     * find all the stack traces subscriptions linked to a single stack trace
     * @param stacktraceId
     * @return
     */
    List<StacktraceSubscription> findAllByStacktrace_StacktraceId(Integer stacktraceId);

    /**
     * Find all the subscriptions for a single user
     * @param userId
     * @return
     */
    List<StacktraceSubscription> findAllBySubscription_User_UserId(Integer userId);
}
