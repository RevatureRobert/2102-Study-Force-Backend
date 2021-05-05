package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Rest controller to handle stacktrace requests
 * @author John Stone
 * @author Joshua Swanson
 */
@RestController
@RequestMapping("/stacktrace")
@CrossOrigin
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
        return stacktraceService.getStacktraceById(id);
    }


    /**
     * Deletes the stacktrace with the passed stacktraceId
     * @param stacktraceId Primary id of stacktrace
     * @return Response Entity with no content
     */
    @DeleteMapping("/{stacktraceId}")
    public ResponseEntity<Void> deleteStacktraceById(@PathVariable("stacktraceId") int stacktraceId){
        stacktraceService.deleteStackTraceById(stacktraceId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity createStacktrace(@RequestBody StacktraceDTO stacktraceDTO) {
        stacktraceService.save(stacktraceDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
