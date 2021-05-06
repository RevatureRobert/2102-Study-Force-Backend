package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Rest controller used to handle requests for {@link Solution}
 * <p>
 *      Requests and responses will contain {@link SolutionDTO}
 * </p>
 *
 * @author Joshua Swanson
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stacktrace/solution")
public class SolutionController {

    @Autowired
    SolutionService solutionService;

    /**
     * Retrieves all {@link Solution Solutions} matching a Stacktrace id
     *<p>
     *     This method receives a request and all logic is performed by {@link SolutionService#getAllSolutionsForStacktrace(int)}
     *</p>
     * @param page The page to be displayed
     * @param pageSize Size of the page that will be used to calculate offset
     * @param stackTraceId Stacktrace primary id
     * @return List of {@link Solution Solutions} for the given Stacktrace id
     */
    @GetMapping("/{stackTraceId}")
    public List<SolutionDTO> getAllSolutionsForStacktrace(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                          @PathVariable int stackTraceId){
        return solutionService.getAllSolutionsForStacktrace(stackTraceId);
    }

    /**
     * Inserts a {@link Solution} into data store and returns a {@link SolutionDTO}
     * by utilizing {@link SolutionService#submitFirstSolution(SolutionDTO)}
     * @param solutionDTO SolutionDTO to be saved to data store
     * @return {@link SolutionDTO} of the {@link Solution} saved
     */
    @PostMapping
    public SolutionDTO submitFirstSolution(@RequestBody SolutionDTO solutionDTO){
        return solutionService.submitFirstSolution(solutionDTO);
    }


    /**
     * Updates a {@link Solution} in the data store and returns a
     * {@link SolutionDTO} by utilizing {@link SolutionService#updateSolution(SolutionDTO)}
     * @param solutionDTO Solution to be updated to data store
     * @return {@link SolutionDTO} of the {@link Solution} updated
     */
    @PutMapping
    public SolutionDTO updateSolution(@RequestBody SolutionDTO solutionDTO){
        return solutionService.updateSolution(solutionDTO);
    }

    /**
     * Cascade deletes a {@link SolutionDTO} by its primary id by utilizing {@link SolutionService#deleteSolution(int)}
     * @param solutionId Solution to be deleted to data store
     * @return {@link SolutionDTO} of the {@link Solution} that was deleted
     */
    @DeleteMapping("/{solutionId}")
    public SolutionDTO deleteSolution(@PathVariable int solutionId){
        return solutionService.deleteSolution(solutionId);
    }
}
