package com.revature.studyforce.user.service;

import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * Service Layer for Batch
 * @author Daniel Reyes
 */

@Service
public class BatchService {

    BatchRepository batchRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository){
        this.batchRepository = batchRepository;
    }

    /**
     * GET ALL Batches
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Batches in database
     */
    public Page<Batch> getAllBatches(int page, int offset, String sortBy, String order){
        Page<Batch> batchPage;
        batchPage = batchRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return batchPage;
    }

    /**
     * @param id belonging to batch
     * @return batch
     */
    public Optional<Batch> getBatchById(int id){
        return batchRepository.findById(id);
    }

    /**
     * @param name batch name
     * @return batch
     */
    public Batch getBatchByName(String name){
        return batchRepository.findByNameIgnoreCase(name);
    }

    /**
     * GET ALL Batches after a creation timestamp
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Batches in database with timestamp constraint
     */
    public Page<Batch> getBatchByCreationTime(Long timestamp, int page, int offset, String sortBy, String order){

        Timestamp t = Timestamp.from(Instant.ofEpochMilli(timestamp));
        Page<Batch> batchPage;
        batchPage = batchRepository.findByCreationTimeAfter(t, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return batchPage;
    }


}
