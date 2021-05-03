package com.revature.studyforce.user.service;

import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;

/**
 * Service Layer for Batch using {@link BatchRepository}
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
     * GET mapping for '/allBatches' uses {@link BatchRepository#findAll()}
     * @param sortBy field to be sorted by [batchId | creationTime | name] case insensitive defaults to batchId
     * @param order type of order to sort batches [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 5
     * @param offset number of batches displayed per page [5/ 10/ 25/ 50] defaults to 5 if invalid
     * @return a page of batches dependent on provided page, offset, sort, and order params.
     */
    public Page<Batch> getAllBatches(int page, int offset, String sortBy, String order){
        page = pageValidation(page);
        sortBy = sortByValidation(sortBy);
        offset = offsetValidation(offset);

        Page<Batch> batchPage;
        if(order.equalsIgnoreCase("DESC"))
            batchPage = batchRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            batchPage = batchRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        return batchPage;
    }

    /**
     * GET request for 'getBatchById' in {@link BatchRepository#findById(Object)}
     * @param id int input to represent a batch id
     * @return single batch return that matches batchId param.
     */
    public Batch getBatchById(int id){
        return batchRepository.findById(id).orElse(null);
    }

    /**
     * GET request for 'getBatchByName' in {@link BatchRepository#findByNameContainingIgnoreCase(String)}
     * @param name String input to represent a batch name
     * @return single batch that matches that name
     */
    public Batch getBatchByName(String name){
        return batchRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * GET mapping for 'getBatchByCreationTime' in {@link BatchRepository#findByCreationTimeAfter(Timestamp, Pageable)}
     * @param epochTime timestamp in long format
     * @param sortBy field to be sorted by [batchId | creationTime | name] case insensitive defaults to batchId
     * @param order type of order to sort batches [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 5
     * @param offset number of batches displayed per page [5/ 10/ 25/ 50] defaults to 5 if invalid
     * @return a page of batches dependent on provided page, offset, sort, and order params.
     */
    public Page<Batch> getBatchByCreationTime(long epochTime, int page, int offset, String sortBy, String order){
        page = pageValidation(page);
        sortBy = sortByValidation(sortBy);
        offset = offsetValidation(offset);

        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochTime));
        Page<Batch> batchPage;
        if(order.equalsIgnoreCase("DESC"))
            batchPage = batchRepository.findByCreationTimeAfter(timestamp, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            batchPage = batchRepository.findByCreationTimeAfter(timestamp, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        return batchPage;
    }

    /**
     * guarantees a sort field is selected if user provides one, userID as default ofr invalid inputs
     * @param sort field to sort by
     * @return field to sort by, default userID
     */
    private String sortByValidation(String sort){
        switch(sort.toLowerCase(Locale.ROOT)){
            case "name":
                return "name";
            case "time":
                return "creationTime";
            default:
                return "batchId";
        }
    }

    /**
     * guarantees a page to start off if incorrect input provided by user
     * @param page number of the page available
     * @return number of page to start on
     */
    private int pageValidation(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    /**
     * Ensures the number of elements a page can fit, 10 by default
     * @param offset number of elements desired in each page
     * @return returns 10 as default unless other option selected.
     */
    private int offsetValidation(int offset){
        if(offset < 10 || offset > 100){
            offset = 10;
        }
        return offset;
    }


}
