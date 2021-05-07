package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Basic repo for  {@link Stacktrace}
 * @author Noel Shaji
 * @author Joey Elmblad
 */
@Repository
public interface StacktraceRepository extends JpaRepository<Stacktrace, Integer> {
    List<Stacktrace> findByTechnologyTechnologyName(String technologyName);

    Page<Stacktrace> findAll(Pageable pageable);

    Page<Stacktrace> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCaseOrTechnology_TechnologyId(String title, String body, int technologyId, Pageable pageable);
}
