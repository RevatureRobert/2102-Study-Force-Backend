package com.revature.studyforce.flashcard.repository;


import com.revature.studyforce.flashcard.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultyRepository extends JpaRepository<Flashcard, Integer> {

}
