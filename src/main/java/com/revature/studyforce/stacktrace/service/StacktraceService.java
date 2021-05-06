package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * The StackService allows for communication with {@link StacktraceRepository} and enforces data constraints on requests to repository
 * @author John Stone
 * @author Joshua Swanson
 */
@Service
public class StacktraceService {
    private final StacktraceRepository stacktraceRepo;
    private final UserRepository userRepository;
    private final TechnologyRepository technologyRepository;

    @Autowired
    public StacktraceService(StacktraceRepository stacktraceRepo,
                             UserRepository userRepository, TechnologyRepository technologyRepository){
        this.stacktraceRepo = stacktraceRepo;
        this.userRepository = userRepository;
        this.technologyRepository =technologyRepository;
    }

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
        return stacktraceRepo.findByTechnologyTechnologyName(name).stream().map(StacktraceDTO.stacktraceToDTO()).collect(Collectors.toList());
    }

    /**
     * Gets a stacktrace by stacktrace id using {@link StacktraceRepository#findById(Object)}
     * @param stacktraceId The customer id of the stacktrace being requested
     * @return Data transfer object representation of Stacktrace object converted using {@link StacktraceDTO#stacktraceToDTO()}
     */
    public StacktraceDTO getStackTraceById(int stacktraceId){
        Optional<Stacktrace> requested = stacktraceRepo.findById(stacktraceId);
        return requested.map(stacktrace -> StacktraceDTO.stacktraceToDTO().apply(stacktrace)).orElse(null);
    }

    /**
     * Deletes a Stacktrace by the primary id passed as parameter
     * @param stacktraceId primary id of Stacktrace
     */
    public Stacktrace deleteStackTraceById(int stacktraceId){
        Stacktrace stacktrace = stacktraceRepo.findById(stacktraceId).orElse(null);
        stacktraceRepo.delete(stacktrace);
        return stacktrace;
    }

  /**
   * Saves a stacktrace, takes in StacktraceDTO as a parameter * Uses repo to save it
   *
   * @param stacktraceDTO The DTO representation of stacktrace
   */
  public StacktraceDTO submitStackTrace(StacktraceDTO stacktraceDTO) {
      Stacktrace stacktrace = new Stacktrace(
              stacktraceDTO.getStacktraceId(),
              userRepository.findById(stacktraceDTO.getCreator().getUserId()).orElse(null),
              stacktraceDTO.getTitle(),
              stacktraceDTO.getBody(),
              technologyRepository.findById(stacktraceDTO.getTechnology().getTechnologyId()).orElse(null),
              stacktraceDTO.getCreationTime(),
              null);
      stacktraceRepo.save(stacktrace);
        return stacktraceDTO;
    }

}