package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
     * Gets all stack traces with pagination and sorting
     * @param page The page to be selected
     * @param offset The number of elements per page
     * @param sortBy The property/field to sort by
     * @param order The order in which the list is displayed ["ASC"|"DESC"]
     * @return The page of data transfer representations of all stack trace objects with pagination and sorting applied
     */
    @GetMapping("/page")
    public Page<StacktraceDTO> getPageStacktraces(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
            @RequestParam(value = "sort_by", required = false, defaultValue = "stacktraceId") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") String order,
            @RequestParam(value = "technology_id", required = false, defaultValue = "Nothing") String technologyIdString){
        if (technologyIdString.equalsIgnoreCase("Nothing")) {
            return stacktraceService.getPageStacktraces(page,offset,sortBy,order);
        } else {
            int technologyId;
            try {
                technologyId = Integer.parseInt(technologyIdString);
            } catch (NumberFormatException e) {
                return null;
            }
            return stacktraceService.getAllStacktracesByTechnologyId(technologyId,page,offset,sortBy,order);
        }
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
}