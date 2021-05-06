package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Rest controller used to handle request for solutions to stacktrace questions
 * @author Joshua Swanson
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stacktrace/solution")
public class SolutionController {

    @Autowired
    SolutionService solutionService;

    /**
     * Given a stacktrace id, returns all solutions posted on that stacktrace
     * @param stackTraceId Stacktrace primary id
     * @return  List of solutions for the given stacktrace id
     */
    @GetMapping("/{stackTraceId}")
    public Page<SolutionDTO> getAllSolutionsForStacktrace(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                          @PathVariable int stackTraceId){
        return solutionService.getAllSolutionsForStacktrace(stackTraceId, page, pageSize);
    }

    /**
     * If a user has not previously posted a solution to a stacktrace, this
     *  endpoint should be used. For updating a solution, use put method.
     * @param solutionDTO Solution to be saved to data store
     * @return solution
     */
    @PostMapping
    public SolutionDTO submitFirstSolution(@RequestBody SolutionDTO solutionDTO){
        return solutionService.submitFirstSolution(solutionDTO);
    }


    /**
     * If a user has previously posted a solution to a stacktrace, this
     *  endpoint should be used. Previous solution will be updated with
     *  the body of the SolutionDTO in the request body
     * @param solutionDTO Solution to be updated to data store
     * @return solution
     */
    @PutMapping
    public SolutionDTO updateSolution(@RequestBody SolutionDTO solutionDTO){
        return solutionService.updateSolution(solutionDTO);
    }

    /**
     * If a user has previously posted a solution to a stacktrace, this
     *  endpoint should be used. Previous solution will be updated with
     *  the body of the SolutionDTO in the request body
     * @param solutionId Solution to be deleted to data store
     * @return solution Response Entity with 204 status(No Content success status)
     */
    @DeleteMapping("/{solutionId}")
    public SolutionDTO deleteSolution(@PathVariable int solutionId){
        return solutionService.deleteSolution(solutionId);
    }
}
