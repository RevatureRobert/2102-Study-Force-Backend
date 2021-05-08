package com.revature.studyforce.user.controller;

import com.revature.studyforce.user.dto.CreateUpdateBatchDTO;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Batch Controller for Batch Repo {@link BatchService}
 * @author Daniel Reyes
 * @author Daniel Bernier
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/batches")
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService){
        this.batchService = batchService;
    }

    /**
     * GET mapping for '/allBatches' uses {@link BatchService#getAllBatches(int, int, String, String)}
     * @param sortBy field to be sorted by [id | time | name] case insensitive defaults to id
     * @param order type of order to sort batches [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 5
     * @param offset number of batches displayed per page [5/ 10/ 25/ 50] defaults to 5 if invalid
     * @return a page of batches dependent on provided page, offset, sort, and order params.
     */
    @GetMapping
    public Page<Batch> getAllBatches(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                @RequestParam(value = "sort", required = false, defaultValue = "batchId") String sortBy,
                                @RequestParam(value = "order", required = false, defaultValue = "Desc") String order) {
        return batchService.getAllBatches(page, offset, sortBy, order);
    }

    /**
     * GET mapping for 'getBatchByCreationTime' in {@link BatchService#getBatchByCreationTime(long, int, int, String, String)}
     * @param timestamp timestamp in long format
     * @param sortBy field to be sorted by [id | time | name]  case insensitive defaults to batchId
     * @param order type of order to sort batches [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 5
     * @param offset number of batches displayed per page [5 | 10 | 25| 50] defaults to 5 if invalid
     * @return a page of batches dependent on provided page, offset, sort, and order params.
     */
    @GetMapping("/time/{epochMilliseconds}")
    public Page<Batch> getBatchByCreationTime(@PathVariable("epochMilliseconds") long timestamp,
                                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                              @RequestParam(value = "sort", required = false, defaultValue = "batchId") String sortBy,
                                              @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return batchService.getBatchByCreationTime(timestamp, page,offset,sortBy,order);
    }

    /**
     * GET request for 'getBatchByName' in {@link BatchService#getBatchByName(String)}
     * @param name String input to represent a batch name
     * @return single batch that matches that name
     */
    @GetMapping("/name")
    public Batch getBatchByBatchName(@RequestParam(name = "name") String name){
        return batchService.getBatchByName(name);
    }

    /**
     * GET request for 'getBatchById' in {@link BatchService#getBatchById(int)}
     * @param batchId int input to represent a batch id
     * @return single batch return that matches batchId param.
     */
    @GetMapping("/{batchId}")
    public Batch getBatchByBatchId(@PathVariable(name = "batchId") int batchId){
        return batchService.getBatchById(batchId);
    }

    /**
     * Post request for 'createBatch' in {@link BatchService#createBatch(CreateUpdateBatchDTO)}
     * @param createDTO Data Transfer Object with batchId, name, array of instructors and array of users
     * @return Batch
     */
    @PostMapping("/create")
    public @ResponseBody Batch createBatch(@RequestBody CreateUpdateBatchDTO createDTO){
        
        return batchService.createBatch(createDTO);

    }

    /**
     * Put request for 'updateBatch' in {@link BatchService#updateBatch(CreateUpdateBatchDTO)}
     * @param updateDTO Data Transfer Object with batchId, name, array of instructors and array of users
     * @return Batch
     */
    @PutMapping("/update")
    public @ResponseBody Batch updateBatch(@RequestBody CreateUpdateBatchDTO updateDTO){
        
        return batchService.updateBatch(updateDTO);

    }

    /**
     * @param
     * @return Batch
     */
    @PutMapping("/deactivateBatch")
    public @ResponseBody Batch deactivateBatch(@RequestBody int batchId){

        return batchService.deactivateBatch(batchId);

    }
    /*
    Select * from Users, Batches where user.id = batch.
     */

    @DeleteMapping("/{batchId}")
    public @ResponseBody Batch deleteBatch(@PathVariable int id){
        return batchService.deleteBatch(id);
    }

}
