package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Rest controller to handle stacktrace requests
 * Requested are processed via {@link StacktraceService}
 * @author John Stone
 * @author Joshua Swanson
 */
@RestController
@CrossOrigin
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
    public ResponseEntity<Map<String, Object>> getAllStacktraces(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            List<Stacktrace> stacktraces = new ArrayList<Stacktrace>();
            Pageable paging = PageRequest.of(page, size);

            Page<Stacktrace> pageStack;
            if (title == null)
                pageStack = stacktraceRepository.findAll(paging);
            else
                pageStack = stacktraceRepository.findByTitleContaining(title, paging);

            stacktraces = pageStack.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("stacktraces",stacktraces);
            response.put("currentPage", pageStack.getNumber());
            response.put("totalItems", pageStack.getTotalElements());
            response.put("totalPages", pageStack.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






    /**
     * Gets stacktrace who's id matches provided id using {@link StacktraceService#getStackTraceById(int)}
     * @param id The id of the stacktrace requested
     * @return The data transfer representation of the requested Stacktrace
     */
    @CrossOrigin
    @GetMapping("/stacktrace/{stacktraceId}")
    public StacktraceDTO getStacktraceById(@PathVariable(name = "stacktraceId") int id){
        return stacktraceService.getStackTraceById(id);
    }

    /**
     * Deletes the stacktrace with the passed stacktraceId using {@link StacktraceService#deleteStackTraceById(int)}
     * @param stacktraceId id of stacktrace to be deleted
     * @return The data transfer representation of the deleted Stacktrace
     */
    @CrossOrigin
    @DeleteMapping("/stacktrace/{stacktraceId}")
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
