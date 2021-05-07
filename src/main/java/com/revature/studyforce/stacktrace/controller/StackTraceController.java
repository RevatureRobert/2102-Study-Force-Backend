package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller to handle stacktrace requests
 * Requested are processed via {@link StacktraceService}
 * @author John Stone
 * @author Joshua Swanson
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stacktrace")
public class StackTraceController {

    @Autowired
    private StacktraceService stacktraceService;

    /**
     * Gets all Stacktraces using {@link StacktraceService#getAllStacktraces(int, int)}
     * @param page Page number of {@link StacktraceDTO StacktraceDTOs} to be displayed
     * @param pageSize Number of {@link StacktraceDTO StacktraceDTOs} to be returned
     * @return Page of {@link StacktraceDTO StacktraceDTOs} according to pagesize
     */
    @GetMapping()
    public Page<StacktraceDTO> getAllStackTraces(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        return stacktraceService.getAllStacktraces(page, pageSize);
    }

    /**
     * Gets stacktrace who's id matches provided id using {@link StacktraceService#getStackTraceById(int)}
     * @param id The id of the stacktrace requested
     * @return The data transfer representation of the requested Stacktrace
     */
    @GetMapping("/{stacktraceId}")
    public StacktraceDTO getStacktraceById(@PathVariable(name = "stacktraceId") int id){
        return stacktraceService.getStackTraceById(id);
    }

    /**
     * Deletes the stacktrace with the passed stacktraceId using {@link StacktraceService#deleteStackTraceById(int)}
     * @param stacktraceId id of stacktrace to be deleted
     * @return The data transfer representation of the deleted Stacktrace
     */
    @DeleteMapping("/{stacktraceId}")
    public StacktraceDTO deleteStacktraceById(@PathVariable("stacktraceId") int stacktraceId){
        return stacktraceService.deleteStackTraceById(stacktraceId);
    }

    /**
     * Saves a stacktrace using {@link StacktraceService#submitStackTrace(StacktraceDTO)}
     * Uses repo to save it
     * @param stacktraceDTO the DTO of stacktrace as an object
     * @return The data transfer representation of the created Stacktrace
     */
    @PostMapping
    public StacktraceDTO createStacktrace(@RequestBody StacktraceDTO stacktraceDTO) {
        return stacktraceService.submitStackTrace(stacktraceDTO);
    }

    /**
     * Gets stacktraces using {@link StacktraceService#getAllStacktracesByTitleOrBodyOrTechnologyId(String, String, int, int, int)}
     * whose title contains the given title string or whose body contains the given body string
     * or who's technology matches the given technology ID, applies pagination and sorting
     * @param title Title String to search for
     * @param body Body String to search for
     * @param techologyIdString technology ID to search for, will be converted to an integer
     * @param page Page number of {@link StacktraceDTO StacktraceDTOs} to be displayed
     * @param pageSize Number of {@link StacktraceDTO StacktraceDTOs} to be returned
     * @return Page of matching {@link StacktraceDTO StacktraceDTOs} according to pagesize
     */
    @GetMapping("/search")
    public Page<StacktraceDTO> getAllStackTracesMatchingTitleOrBodyOrTechnologyId(@RequestParam(value = "title", defaultValue = "", required = false) String title,
                                                                                  @RequestParam(value = "body", defaultValue = "", required = false) String body,
                                                                                  @RequestParam(value = "technologyId", defaultValue = "-1", required = false) String techologyIdString,
                                                                                  @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        int technologyId;
        try {
            technologyId = Integer.parseInt(techologyIdString);
        } catch (NumberFormatException e) {
            technologyId = -1;
        }
        return stacktraceService.getAllStacktracesByTitleOrBodyOrTechnologyId(title,body,technologyId,page, pageSize);
    }

    /**
     * This method should return the updated stacktrace that picks a solutionId and assigns it to chosenSolution
     * using {@link StacktraceService#updateStacktraceChosenSolutionBySolutionAndStacktraceId(int, int)}
     * @param solutionId The id to be assigned to the stacktrace chosenSolution field
     * @param stacktraceId the primary key for the stacktrace table
     * @return should return the updated stacktrace with a solutionId assigned to the chosenSolution column on the
     * stacktrace table
     */
    @PutMapping("/chosen-solution")
    public StacktraceDTO updateStacktraceChosenSolutionBySolutionIdAndStacktraceId(@RequestParam(value = "solutionId") int solutionId,
                                                                   @RequestParam(value= "stacktraceId") int stacktraceId){
        return stacktraceService.updateStacktraceChosenSolutionBySolutionAndStacktraceId(solutionId,stacktraceId);
    }
}
