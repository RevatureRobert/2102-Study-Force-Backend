package com.revature.studyforce.user.contollers;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User Controller
 * @author Daniel Reyes
 */
@RestController
@CrossOrigin(origins = "*")
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService){
        this.batchService = batchService;
    }

    @GetMapping("/allBatches")
    public List<Batch> getUsers() {
        return batchService.getAllBatches();
    }

}
