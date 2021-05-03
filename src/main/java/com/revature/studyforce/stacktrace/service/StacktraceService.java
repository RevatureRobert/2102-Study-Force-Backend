package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The StackService allows for communication with {@link StacktraceRepository} and enforces data constraints on requests to repository
 * @author John Stone
 */
@Service
public class StacktraceService {

    @Autowired
    private  StacktraceRepository stacktraceRepo;

    /**
     * Gets all Stacktraces
     * @return A list of Stacktraces
     */
    public List<StacktraceDTO> getAllStacktraces() {
        return stacktraceRepo.findAll().stream().map(StacktraceDTO.stacktraceToDTO()).collect(Collectors.toList());
    }

    /**
     * Gets all Stacktraces
     * @return A list of Stacktraces
     */
    public List<StacktraceDTO> getAllStacktracesOfTechnologyName(String name) {
        return stacktraceRepo.findByTechnologyId_technologyName(name).stream().map(StacktraceDTO.stacktraceToDTO()).collect(Collectors.toList());
    }

    /**
     * Gets all Stacktraces, applying pagination and sorting
     * @param page The page to be selected defaults to 0 if page could not be understood
     * @param offset The number of elements per page [5|10|25|50|100] defaults to 25 if offset could not be understood
     * @param sortBy The property/field to sort by ["firstname"|"lastname"|"email"|"phonenumber"] defaults to tacktraceId if sortby could not be understood
     * @param order The order in which the list is displayed ["ASC"|"DESC"]
     * @return The page of data transfer representations of all stacktrace objects with pagination and sorting applied
     */
/*    public Page<StacktraceDTO> getPageStacktraces(int page, int offset, String sortBy, String order){

        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Stacktrace> stacktraces;
        if(order.equalsIgnoreCase("DESC"))
            stacktraces = stacktraceRepo.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            stacktraces = stacktraceRepo.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));

        return stacktraces.map(StacktraceDTO.stacktraceToDTO());
    }

    *//**
     * Gets stacktraces with the given technology, applies pagination and sorting
     * @param id The technology id being searched from
     * @param page The page to be selected defaults to 0 if page could not be understood
     * @param offset The number of elements per page [5|10|25|50|100] defaults to 25 if offset could not be understood
     * @param sortBy The property/field to sort by ["firstname"|"lastname"|"email"|"phonenumber"] defaults to stacktraceId if sortby could not be understood
     * @param order The order in which the list is displayed ["ASC"|"DESC"]
     * @return The page of data transfer representations of all stack trace objects who's technology matches the given technology Id with pagination and sorting applied
     *//*
    public Page<StacktraceDTO> getAllStacktracesByTechnologyId(int id, int page, int offset, String sortBy, String order){
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Stacktrace> stacktraces;
        if(order.equalsIgnoreCase("DESC"))
            stacktraces = stacktraceRepo.findByTechnologyId_technologyId(id, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            stacktraces = stacktraceRepo.findByTechnologyId_technologyId(id, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));

        return stacktraces.map(StacktraceDTO.stacktraceToDTO());
    }*/

    /**
     * Gets a stacktrace by stacktrace id using {@link StacktraceRepository#findById(Object)}
     * @param stacktraceId The customer id of the stacktrace being requested
     * @return Data transfer object representation of Stacktrace object converted using {@link StacktraceDTO#stacktraceToDTO()}
     */
    public StacktraceDTO getStacktraceById(int stacktraceId){
        Optional<Stacktrace> requested = stacktraceRepo.findById(stacktraceId);
        return requested.map(stacktrace -> StacktraceDTO.stacktraceToDTO().apply(stacktrace)).orElse(null);
    }

/*    *//**
     * Ensures permitted page format
     * @param page The page number value being validated
     * @return A valid page number value
     *//*
    private int validatePage(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    *//**
     *  Ensures a permitted offset valye
     * @param offset The offset number being validated
     * @return A valid offset value
     *//*
    private int validateOffset(int offset){
        if(offset != 5 && offset != 10 && offset != 25 && offset != 50 && offset != 100)
            offset = 25;
        return offset;
    }

    *//**
     * Ensures permitted sortby format
     * @param sortBy The sortby value being validated
     * @return A valid sortby value
     *//*
    private String validateSortBy(String sortBy){
        switch (sortBy.toLowerCase(Locale.ROOT)){
            case "creator_id":
                return "creatorId";
            case "title":
                return "title";
            case "technology_id":
                return "technologyId";
            case "creation_time":
                return "creationTime";
            default:
                return "stacktraceId";
        }
    }*/
}
