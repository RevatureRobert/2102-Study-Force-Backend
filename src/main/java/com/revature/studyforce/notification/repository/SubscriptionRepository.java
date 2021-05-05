package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Reposiotry for Subscriptiosns {@link Subscription}
 * @author Brandon Pinkerton
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    /**
     * A query used to a subscription linked a user by their user ID
     * @param userId
     * @return
     */
    Subscription findByUser_UserId(Integer userId);
}
