package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used to handle requests for {@link SolutionRepository}
 *
 * @author Joshua Swanson
 * @author Joey Elmblad
 */
@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final StacktraceRepository stacktraceRepository;
    private final UserRepository userRepository;
    private final int defaultPageSize;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository,
                           StacktraceRepository stacktraceRepository, UserRepository userRepository){
        this.solutionRepository =solutionRepository;
        this.stacktraceRepository = stacktraceRepository;
        this.userRepository = userRepository;
        this.defaultPageSize = 5;
    }

    /**
     * Given a stacktrace id, returns all solutions posted on that stacktrace
     * There must be a better way to handle mapping Solutions to SolutionDTOs
     * @param stackTraceId Primary id of stacktrace
     * @return All solutions posted for the given stacktrace
     */
    public Page<SolutionDTO> getAllSolutionsForStacktrace(int stackTraceId, int page, int pageSize){
        page = validatePage(page);
        pageSize = validatePageSize(pageSize);
        Page<Solution> solutions = solutionRepository.findByStackTraceId_stacktraceId(stackTraceId, PageRequest.of(page, pageSize));
        return solutions.map(SolutionDTO.solutionToDTO());
    }

    /**
     * Submits a solution from {@link SolutionRepository#save}
     *  endpoint should be used. For updating a solution, use put method.
     * @param solutionDTO A data transfer object containing the solutionId,
     * stackTraceId, StacktraceUserDTO, body, adminSelected, and creationTime.
     * @return returns the solutionDTO after saving the actual solution with the
     * user of {@link SolutionDTO#solutionToDTO()}
     */
//    public SolutionDTO submitFirstSolution(SolutionDTO solutionDTO){
//        Solution solution = new Solution(
//                solutionDTO.getSolutionId(),
//                stacktraceRepository.findById(solutionDTO.getStackTraceId()).orElse(null),
//                userRepository.findById(solutionDTO.getUserId()).orElse(null),
//                solutionDTO.getBody(),
//                solutionDTO.getAdminSelected(),
//                solutionDTO.getCreationTime(),
//                solutionDTO.getTotalVote(),
//                null);
//
//        return SolutionDTO.solutionToDTO().apply(solutionRepository.save(solution));
//    }

    /**
     * If a user has previously posted a solution to a stacktrace, this
     *  endpoint should be used. Previous solution will be updated with
     *  the body of the SolutionDTO in the request body or a new Solution
     *  will be created and saved to data store using {@link SolutionRepository#save}
     * @param solutionDTO A data transfer object containing the solutionId,
     * stackTraceId, StacktraceUserDTO, body, adminSelected, and creationTime.
     * @return returns the solutionDTO after saving the actual solution with the
     * user of {@link SolutionDTO#solutionToDTO()}
     */
//    public SolutionDTO updateSolution(SolutionDTO solutionDTO){
//        Optional<Solution> solution = solutionRepository.findById(solutionDTO.getSolutionId());
//        if(solution.isPresent()){
//            solution.get().setBody(solutionDTO.getBody());
//            return SolutionDTO.solutionToDTO().apply(solutionRepository.save(solution.get()));
//        }else{
//            Solution newSolution = new Solution(
//                    solutionDTO.getSolutionId(),
//                    stacktraceRepository.findById(solutionDTO.getStackTraceId()).orElse(null),
//                    userRepository.findById(solutionDTO.getUserId()).orElse(null),
//                    solutionDTO.getBody(),
//                    solutionDTO.getAdminSelected(),
//                    solutionDTO.getCreationTime(),
//                    solutionDTO.getTotalVote(),
//                    null);
//
//            return SolutionDTO.solutionToDTO().apply(solutionRepository.save(newSolution));
//        }
//    }

  /**
   * Deletes Solution with the given id with {@link SolutionRepository#delete}
   *
   * @param solutionId The primary key of a Solution used as a unique identifier
   * @return returns a data transfer object containing the solutionId, stackTraceId,
   * StacktraceUserDTO, body, adminSelected, and creationTime.
   */
  public SolutionDTO deleteSolution(int solutionId) {
        Solution solution = solutionRepository.findById(solutionId).orElse(null);
        if(solution == null)
            return null;
        solutionRepository.deleteSolutionById(solutionId);
        return new SolutionDTO(
                solution.getSolutionId(),
                solution.getUserId().getUserId(),
                solution.getStackTraceId().getStacktraceId(),
                solution.getUserId().getName(),
                solution.getBody(),
                solution.getAdminSelected(),
                solution.getCreationTime(),
                solution.getTotalVote()
        );
    }

    /**
     * Validates the size of pages
     * @param pageSize size of a page
     * @return valid page size
     */
    public int validatePageSize(int pageSize){
        if(pageSize == 5 || pageSize == 10 || pageSize == 20)
            return pageSize;
        return defaultPageSize;
    }

    /**
     * validates page is not negative
     * @param page page number
     * @return valid page number
     */
    public int validatePage(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    /**
     * Update total votes for any given solution using
     * {@link SolutionRepository#updateSolutionTotalVotesBySolutionId(int)}
     * @param solutionId The solutionId used to update any given solution total votes
     * @return will return a solutionDTO with updated total votes on solution table
     */
    public SolutionDTO updateSolutionTotalVotes(int solutionId){
        solutionRepository.updateSolutionTotalVotesBySolutionId(solutionId);
        Solution solution = solutionRepository.findById(solutionId).orElse(null);
        return SolutionDTO.solutionToDTO().apply(solution);
    }

//    /**
//     * Update the solution adminSelected to true, which should be displayed above
//     * every other comment.
//     * {@link SolutionRepository#updateSolutionSelectedByAdminBySolutionId(int)}
//     * @param solutionId The solutionId used to update any given solution
//     * @return will return a solutionDTO with updated adminSelected as true
//     */
//    public SolutionDTO updateSolutionSelectedByAdmin(int solutionId){
//        solutionRepository.updateSolutionSelectedByAdminBySolutionId(solutionId);
//        Solution solution = solutionRepository.findById(solutionId).orElse(null);
//        return SolutionDTO.solutionToDTO().apply(solution);
//    }
}
