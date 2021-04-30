package com.revature.studyforce.user.repository;

import com.revature.studyforce.user.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Timestamp;
import java.util.List;


/**
 * Basic repository for {@link Batch}
 * @author Daniel Bernier
 * @author  Daniel Reyes
 */
public interface BatchRepository extends JpaRepository<Batch,Integer> {
    Batch findByName(String name);
    Batch findByCreationTime(Timestamp creation);

    Batch findByNameIgnoreCase(String name);

    //List<Batch> findByCreationDateGreater(Timestamp timestamp);

}
