package com.revature.studyforce.user.repository;

import com.revature.studyforce.user.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Basic repository for {@link Batch}
 * @author Daniel Bernier
 * @author  Daniel Reyes
 */
@Repository
public interface BatchRepository extends JpaRepository<Batch,Integer> {

    Page<Batch> findByCreationTimeAfter(Timestamp creation, Pageable pageable);

    Batch findByNameContainingIgnoreCase(String name);

}
