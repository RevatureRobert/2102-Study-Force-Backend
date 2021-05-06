package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Rest controller to handle stacktrace requests
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
     * Gets all Stacktraces
     * @return A list of Stacktraces
     */
    @GetMapping()
    public List<StacktraceDTO> getAllStackTraces() {
        return stacktraceService.getAllStacktraces();
    }

    /**
     * Gets stacktrace who's id matches provided id
     * @param id The id of the customer
     * @return The data transfer representation of the requested Stacktrace
     */
    @GetMapping("/{stacktraceId}")
    public StacktraceDTO getStacktraceById(@PathVariable(name = "stacktraceId") int id){
        return stacktraceService.getStackTraceById(id);
    }

    /**
     * Deletes the stacktrace with the passed stacktraceId
     * @param stacktraceId Primary id of stacktrace
     * @return Response Entity with no content
     */
    @DeleteMapping("/{stacktraceId}")
    public StacktraceDTO deleteStacktraceById(@PathVariable("stacktraceId") int stacktraceId){
        return stacktraceService.deleteStackTraceById(stacktraceId);
    }

    /**
     * Saves a stacktrace, takes in StacktraceDTO as a parameter
     * Uses repo to save it
     * @param stacktraceDTO the DTO of stacktrace as an object
     * @return Http status for the Response Entity
     */
    @PostMapping
    public StacktraceDTO createStacktrace(@RequestBody StacktraceDTO stacktraceDTO) {
        return stacktraceService.submitStackTrace(stacktraceDTO);
    }
}
