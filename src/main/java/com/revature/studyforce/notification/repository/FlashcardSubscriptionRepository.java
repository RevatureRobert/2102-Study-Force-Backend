package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.FlashcardSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardSubscriptionRepository extends JpaRepository<FlashcardSubscription, Integer> {
    FlashcardSubscription findByFlashcardSubscriptionId(FlashcardSubscriptionId flashcardSubscriptionId);
    List<FlashcardSubscription> findAllByFlashcard_Id(Integer flashcardId);
    List<FlashcardSubscription> findAllBySubscription_User_UserId(Integer userId);
}
