package com.revature.studyforce.user.controller;

import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;

/**
 * Batch Controller
 * @author Daniel Reyes
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Batch")
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService){
        this.batchService = batchService;
    }

    /**
     * GET ALL Batches
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Batches in database
     */
    @GetMapping("/allBatches")
    public Page<Batch> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "offset", required = false, defaultValue = "5") int offset,
                                   @RequestParam(value = "sortby", required = false, defaultValue = "batchId") String sortBy,
                                   @RequestParam(value = "order", required = false, defaultValue = "ASC") String order) {
        return batchService.getAllBatches(page, offset, sortBy, order);
    }

    /**
     * GET ALL Batches after a creation timestamp
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Batches in database with timestamp constraint
     */
    @GetMapping("/{timestamp}")
    public Page<Batch> getBatchByCreationTime(@PathVariable("time") Timestamp timestamp,
                                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value = "offset", required = false, defaultValue = "5") int offset,
                                              @RequestParam(value = "sortby", required = false, defaultValue = "batchId") String sortBy,
                                              @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return batchService.getBatchByCreationTime(timestamp.getTime(), page,offset,sortBy,order);
    }

    /**
     * @param name batch name
     * @return batch
     */
    @GetMapping("/{name}")
    public Batch getUserByBatchName(@PathVariable(name = "name") String name){
        return batchService.getBatchByName(name);
    }

    /**
     * @param batchId belonging to batch
     * @return batch
     */
    @GetMapping("/{batchId}")
    public Batch getUserByBatchId(@PathVariable(name = "batchId") int batchId){
        return batchService.getBatchById(batchId);
    }
}
