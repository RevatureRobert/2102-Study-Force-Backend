package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author John Stone
 * Represents the Repository for Technology Model
 */
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
}
