package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Basic repo for  {@link com.revature.studyforce.stacktrace.model.Stacktrace}
 * author: Noel Shaji
 */
@Repository
public interface StacktraceRepository extends JpaRepository<Stacktrace, Integer> {
    List<Stacktrace> findByTechnologyTechnologyName(String technologyName);

    @Modifying
    @Query(value = "select * from stacktrace where stacktrace_id = :stacktraceId ;", nativeQuery = true)
    List<Stacktrace> findByStacktraceId(@Param("stacktraceId") int stacktraceId);

    @Transactional
    @Modifying
    @Query("delete from Stacktrace where stacktrace_id = :stacktraceId")
    void deleteByStackTraceId(@Param("stacktraceId") int stacktraceId);
}
