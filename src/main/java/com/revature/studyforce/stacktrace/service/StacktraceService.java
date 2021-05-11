package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Optional;

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
    private final int defaultPageSize;

    @Autowired
    public StacktraceService(StacktraceRepository stacktraceRepo,
                             UserRepository userRepository, TechnologyRepository technologyRepository){
        this.stacktraceRepo = stacktraceRepo;
        this.userRepository = userRepository;
        this.technologyRepository = technologyRepository;
        defaultPageSize = 5;
    }


    /**
     * Retrieves a page of {@link Stacktrace Stacktraces}
     * @param page The page to be displayed
     * @param pageSize Number of {@link Stacktrace Stacktraces} to be displayed
     * @return Page of {@link Stacktrace Stacktraces} dependent on provided page, pageSize
     */
    public Page<StacktraceDTO> getAllStacktraces(int page, int pageSize) {
        pageSize = validatePageSize(pageSize);
        page = validatePage(page);

        return stacktraceRepo.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "creationTime"))).map(StacktraceDTO.stacktraceToDTO());
    }

    /**
     * Gets all stack traces by {@link StacktraceRepository#findByTitleContainingIgnoreCaseAndTechnologyTechnologyId(String, int, Pageable)}
     * who's name contains the given name or who's body contains the given body or
     * who's technology id matches the given technology id
     * @param title the title substring to search for
     * @param technologyId the technology id to search for
     * @param page the page to be displayed
     * @param pageSize Number of {@link Stacktrace Stacktraces} to be displayed
     * @return Page of matching {@link Stacktrace Stacktraces} dependent on provided page, pageSize
     */

    public Page<StacktraceDTO> getAllStacktracesByTitleOrBodyOrTechnologyId(String title, int technologyId, int page, int pageSize) {
        pageSize = validatePageSize(pageSize);
        page = validatePage(page);
        if (technologyId < 0) {
            return stacktraceRepo.findByTitleContainingIgnoreCase(title,PageRequest.of(page,pageSize, Sort.by(Sort.Direction.DESC, "creationTime"))).map(StacktraceDTO.stacktraceToDTO());
        }
        return stacktraceRepo.findByTitleContainingIgnoreCaseAndTechnologyTechnologyId(title, technologyId,PageRequest.of(page,pageSize, Sort.by(Sort.Direction.DESC, "creationTime"))).map(StacktraceDTO.stacktraceToDTO());
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
        stacktraceRepo.deleteById(stacktraceId);
        return new StacktraceDTO(
                stacktrace.getStacktraceId(),
                stacktrace.getUserId().getUserId(),
                stacktrace.getUserId().getName(),
                stacktrace.getTitle(),
                stacktrace.getBody(),
                stacktrace.getTechnology().getTechnologyId(),
                stacktrace.getTechnology().getTechnologyName(),
                stacktrace.getChosenSolution(),
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
              userRepository.findById(stacktraceDTO.getUserId()).orElse(null),
              stacktraceDTO.getTitle(),
              stacktraceDTO.getBody(),
              technologyRepository.findById(stacktraceDTO.getTechnologyId()).orElse(null),
              stacktraceDTO.getCreationTime(),
              stacktraceDTO.getChosenSolution(),
              null);
      stacktraceRepo.save(stacktrace);
        return stacktraceDTO;
    }

    /**
     * validates page is not negative
     * @param page page number
     * @return valid page number
     */
    int validatePage(int page){
      if(page < 0)
          return 0;
      return page;
    }

    /**
     * Validates the size of pages
     * @param pageSize size of a page
     * @return valid page size
     */
    int validatePageSize(int pageSize){
      if(pageSize == 5 || pageSize == 10 || pageSize == 15)
          return pageSize;
      return defaultPageSize;
    }

    /**

//     * {@link SolutionRepository#updateSolutionSelectedByAdminBySolutionId(int)}
//     * @param solutionId
//     * @return will return a solutionDTO with updated adminSelected as true
//     */

    /**
     * Update the Stacktrace chosenSolution with targeted SolutionId, which should be displayed above
     * every other comment unless admin picks a solution.
     * using {@link StacktraceRepository#updateStacktraceUserSelectedSolution(int, int)}
     * @param solutionId primary key for the solution table
     * @param stacktraceId primary key for the stacktrace table
     * @return will return a updated stacktrace with a chosenSolution solutionId
     */
    public StacktraceDTO updateStacktraceChosenSolutionBySolutionAndStacktraceId(int solutionId, int stacktraceId){
        stacktraceRepo.updateStacktraceUserSelectedSolution(solutionId, stacktraceId);
        Stacktrace stacktrace = stacktraceRepo.findById(stacktraceId).orElse(null);
        return StacktraceDTO.stacktraceToDTO().apply(stacktrace);
    }
}