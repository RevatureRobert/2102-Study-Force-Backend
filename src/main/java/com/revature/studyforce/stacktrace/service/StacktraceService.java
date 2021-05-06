package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import com.revature.studyforce.user.dto.UserNameDTO;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The StackService allows for communication with {@link StacktraceRepository}
 * and enforces data constraints on requests to repository
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
     * Gets all Stacktraces using {@link StacktraceRepository#findAll()}
     * @return A list of Stacktraces
     */
    public List<StacktraceDTO> getAllStacktraces() {
        return stacktraceRepo.findAll().stream().map(StacktraceDTO.stacktraceToDTO()).collect(Collectors.toList());
    }

    /**
     * Gets Stacktraces with the given technology name using {@link StacktraceRepository#findById(Object)}
     * @param name the name of the technology to search for
     * @return A list of Stacktraces exactly matching the given technology name
     */
    public List<StacktraceDTO> getAllStacktracesOfTechnologyName(String name) {
        return stacktraceRepo.findByTechnologyTechnologyName(name).stream().map(StacktraceDTO.stacktraceToDTO()).collect(Collectors.toList());
    }

    /**
     * Gets a stacktrace by stacktrace id using {@link StacktraceRepository#findById(Object)}
     * @param stacktraceId The id of the stacktrace being requested
     * @return Data transfer object representation of Stacktrace object converted
     * using {@link StacktraceDTO#stacktraceToDTO()}
     */
    public StacktraceDTO getStackTraceById(int stacktraceId){
        Optional<Stacktrace> requested = stacktraceRepo.findById(stacktraceId);
        return requested.map(stacktrace -> StacktraceDTO.stacktraceToDTO().apply(stacktrace)).orElse(null);
    }

    /**
     * Deletes a Stacktrace by the primary id passed as parameter
     * using {@link StacktraceRepository#findById(Object)}
     * @param stacktraceId primary id of Stacktrace
     * @return Data Transfer Object of the deleted Stacktrace
     */
    public StacktraceDTO deleteStackTraceById(int stacktraceId){
        Stacktrace stacktrace = stacktraceRepo.findById(stacktraceId).orElse(null);
        if(stacktrace == null){
            return null;
        }
        stacktraceRepo.delete(stacktrace);
        return new StacktraceDTO(
                stacktrace.getStacktraceId(),
                new UserNameDTO(stacktrace.getUserId().getUserId(),stacktrace.getUserId().getName()),
                stacktrace.getTitle(),
                stacktrace.getBody(),
                stacktrace.getTechnology(),
                stacktrace.getCreationTime());
    }

  /**
   * Saves a stacktrace, takes in StacktraceDTO as a parameter
   * using {@link StacktraceRepository#findById(Object)}
   * @param stacktraceDTO The DTO representation of stacktrace
   * @return Data Transfer Object of the created Stacktrace
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