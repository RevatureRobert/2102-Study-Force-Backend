package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Represents the Repository for {@link Technology}
 *
 * @author John Stone
 */
@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Technology where technology_id = :technologyId")
    void deleteSolutionById(@Param("technologyId") int technologyId);
}
