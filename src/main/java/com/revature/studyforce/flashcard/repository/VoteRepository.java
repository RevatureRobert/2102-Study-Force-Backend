package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Represents the repository for the Vote model {@link Vote}
 * @author Elizabeth Ye
 */
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findByUser_userId(final int id);
    List<Vote> findByAnswerAnswerId(final int id);
    Optional<Vote> findByAnswer_answerIdAndUser_userId(final int answerId, final int userId);
}
