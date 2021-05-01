package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
