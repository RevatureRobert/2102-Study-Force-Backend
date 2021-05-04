package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findByUser_UserId(Integer userId);
}
