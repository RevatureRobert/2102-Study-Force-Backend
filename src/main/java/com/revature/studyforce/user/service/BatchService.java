package com.revature.studyforce.user.service;

import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Layer for Batch
 * @author Daniel Reyes
 */

@Service
public class BatchService {

    private final BatchRepository batchRepository;

    @Autowired
    public BatchService(BatchRepository batchRepository){
        this.batchRepository = batchRepository;
    }

    public List<Batch> getAllBatches(){
        return batchRepository.findAll();
    }

    public Optional<Batch> getBatchById(int id){
        return batchRepository.findById(id);
    }

    public Batch getBatchByName(String name){
        return batchRepository.findByNameIgnoreCase(name);
    }

//    public List<Batch> getBatchByCreationTime(Timestamp timestamp){
//        return batchRepository.findByCreationDateGreater(timestamp);
//    }


}
