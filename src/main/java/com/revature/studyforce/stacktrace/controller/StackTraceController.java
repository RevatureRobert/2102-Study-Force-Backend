package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller to handle stacktrace requests
 * Requested are processed via {@link StacktraceService}
 * @author John Stone
 * @author Joshua Swanson
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
public class StackTraceController {

    @Autowired
    private StacktraceService stacktraceService;

    @Autowired
    private StacktraceRepository stacktraceRepository;

    /**
     * Gets all Stacktraces using {@link StacktraceService#getAllStacktraces(int, int)}
     * @param page Page number of {@link StacktraceDTO StacktraceDTOs} to be displayed
     * @param pageSize Number of {@link StacktraceDTO StacktraceDTOs} to be returned
     * @return Page of {@link StacktraceDTO StacktraceDTOs} according to pagesize
     */
    /*@GetMapping("/stacktrace")
    public Page<StacktraceDTO> getAllStackTraces(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize) {
        return stacktraceService.getAllStacktraces(page, pageSize);
    }*/


    @GetMapping("/stacktrace")
    public List<Stacktrace> getStacktraces() {
        return (List<Stacktrace>) stacktraceRepository.findAll();
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
    @PostMapping("/stacktrace")
    public StacktraceDTO createStacktrace(@RequestBody StacktraceDTO stacktraceDTO) {
        return stacktraceService.submitStackTrace(stacktraceDTO);
    }
}
