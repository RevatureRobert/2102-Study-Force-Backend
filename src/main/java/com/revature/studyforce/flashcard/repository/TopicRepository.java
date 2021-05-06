package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.flashcard.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the Repository for the Topic model {@link Topic}
 *
 * @author Kevin Wang
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
