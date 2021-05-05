package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The StackService allows for communication with {@link StacktraceRepository} and enforces data constraints on requests to repository
 * @author John Stone
 * @author Joshua Swanson
 * @author Noel Shaji
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
        return stacktraceRepo.findByTechnologyIdTechnologyName(name).stream().map(StacktraceDTO.stacktraceToDTO()).collect(Collectors.toList());
    }

    /**
     * Gets a stacktrace by stacktrace id using {@link StacktraceRepository#findById(Object)}
     * @param stacktraceId The customer id of the stacktrace being requested
     * @return Data transfer object representation of Stacktrace object converted using {@link StacktraceDTO#stacktraceToDTO()}
     */
    public StacktraceDTO getStacktraceById(int stacktraceId){
        Optional<Stacktrace> requested = stacktraceRepo.findById(stacktraceId);
        return requested.map(stacktrace -> StacktraceDTO.stacktraceToDTO().apply(stacktrace)).orElse(null);
    }

    /**
     * Deletes a Stacktrace by the primary id passed as parameter
     * @param stacktraceId primary id of Stacktrace
     */
    public void deleteStackTraceById(int stacktraceId){
        stacktraceRepo.deleteById(stacktraceId);
    }


    /**
     * Saves a stacktrace, takes in StacktraceDTO as a parameter
     * Uses repo to save it
     */
    @Transactional
    public void save(StacktraceDTO stacktraceDTO){
        Stacktrace stacktrace = mapFromDtoToStacktrace(stacktraceDTO);
        stacktraceRepo.save(stacktrace);
    }

    /**
     * Maps from the DTO to a Stacktrace , takes the fiels of DTO to Stacktrace
     * Needed for saving
     */

    private Stacktrace mapFromDtoToStacktrace(StacktraceDTO stacktraceDTO) {
        Stacktrace stacktrace= new Stacktrace();
        stacktrace.setStacktraceId(stacktraceDTO.getStacktraceId());
        stacktrace.setUserId(stacktraceDTO.getUser());
        stacktrace.setTitle(stacktraceDTO.getTitle());
        stacktrace.setBody(stacktraceDTO.getBody());
        stacktrace.setTechnologyId(stacktraceDTO.getTechnologyId());
        stacktrace.setCreationTime(Timestamp.from(Instant.now()));
        return  stacktrace;


    }
}