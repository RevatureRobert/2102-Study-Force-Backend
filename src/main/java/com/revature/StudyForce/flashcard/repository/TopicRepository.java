package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
