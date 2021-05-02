package com.revature.studyforce.notifcation.repo;

import com.revature.studyforce.notifcation.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription , Integer> {


}
