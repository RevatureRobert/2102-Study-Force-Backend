package com.revature.StudyForce.stacktrace.repository;

import com.revature.StudyForce.stacktrace.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author John Stone
 * Represents the Repository for Technology Model
 */

public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
}
