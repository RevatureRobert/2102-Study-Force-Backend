package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stacktrace")
@CrossOrigin
public class StackTraceController {
    private final StacktraceService STACKTRACE_SERVICE;

    @Autowired
    public StackTraceController(StacktraceService stacktraceService){
        this.STACKTRACE_SERVICE = stacktraceService;
    }
    /**
     * Gets all stack traces with pagination and sorting
     * @param page The page to be selected
     * @param offset The number of elements per page
     * @param sortBy The property/field to sort by
     * @param order The order in which the list is displayed ["ASC"|"DESC"]
     * @return The page of data transfer representations of all stack trace objects with pagination and sorting applied
     */
    @GetMapping()
    public Page<StacktraceDTO> getAllStackTraces(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
            @RequestParam(value = "sortby", required = false, defaultValue = "stacktraceId") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
            @RequestParam(value = "technologyid", required = false, defaultValue = "Nothing") String technologyIdString){
        if (technologyIdString.equalsIgnoreCase("Nothing")) {
            return STACKTRACE_SERVICE.getAllStacktraces(page,offset,sortBy,order);
        } else {
            int technologyId;
            try {
                technologyId = Integer.parseInt(technologyIdString);
            } catch (NumberFormatException e) {
                return null;
            }
            return STACKTRACE_SERVICE.getAllStacktracesByTechnologyId(technologyId,page,offset,sortBy,order);
        }
    }
    /**
     * Gets stacktrace who's id matches provided id
     * @param id The id of the customer
     * @return The data transfer representation of the requested Stacktrace
     */
    @GetMapping("/{stacktraceId}")
    public StacktraceDTO getCustomerById(@PathVariable(name = "stacktraceId") int id){
        return STACKTRACE_SERVICE.getStacktraceById(id);
    }
}
