package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.StacktraceUserDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service used to handle requests for solution resources
 * @author Joshua Swanson
 */
@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;
    private final StacktraceRepository stacktraceRepository;
    private final UserRepository userRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository,
                           StacktraceRepository stacktraceRepository, UserRepository userRepository){
        this.solutionRepository =solutionRepository;
        this.stacktraceRepository = stacktraceRepository;
        this.userRepository = userRepository;
    }

    /**
     * Given a stacktrace id, returns all solutions posted on that stacktrace
     * There must be a better way to handle mapping Solutions to SolutionDTOs
     * @param stackTraceId Primary id of stacktrace
     * @return All solutions posted for the given stacktrace
     */
    public List<SolutionDTO> getAllSolutionsForStacktrace(int stackTraceId){
        List<Solution> solutions = solutionRepository.findByStackTraceId(stackTraceId);
        List<SolutionDTO> solutionDTOS = new ArrayList<>();

        for(Solution solution : solutions){
            solutionDTOS.add(SolutionDTO.solutionToDTO().apply(solution));
        }

        return solutionDTOS;
    }

    /**
     * If a user has not previously posted a solution to a stacktrace, this
     *  endpoint should be used. For updating a solution, use put method.
     * @param solutionDTO Solution to be saved to data store
     * @return solution
     */
    public SolutionDTO submitFirstSolution(SolutionDTO solutionDTO){
        Solution solution = new Solution(
                solutionDTO.getSolutionId(),
                stacktraceRepository.findById(solutionDTO.getStackTraceId()).orElse(null),
                userRepository.findById(solutionDTO.getUser().getUserId()).orElse(null),
                solutionDTO.getBody(),
                solutionDTO.getAdminSelected(),
                solutionDTO.getCreationTime(),
                null);

        return SolutionDTO.solutionToDTO().apply(solutionRepository.save(solution));
    }

    /**
     * If a user has previously posted a solution to a stacktrace, this
     *  endpoint should be used. Previous solution will be updated with
     *  the body of the SolutionDTO in the request body or a new Solution
     *  will be created and saved to data store
     * @param solutionDTO Solution to be updated to data store
     * @return solution
     */
    public SolutionDTO updateSolution(SolutionDTO solutionDTO){
        Optional<Solution> solution = solutionRepository.findById(solutionDTO.getSolutionId());
        if(solution.isPresent()){
            solution.get().setBody(solutionDTO.getBody());
            return SolutionDTO.solutionToDTO().apply(solutionRepository.save(solution.get()));
        }else{
            Solution newSolution = new Solution(
                    solutionDTO.getSolutionId(),
                    stacktraceRepository.findById(solutionDTO.getStackTraceId()).orElse(null),
                    userRepository.findById(solutionDTO.getUser().getUserId()).orElse(null),
                    solutionDTO.getBody(),
                    solutionDTO.getAdminSelected(),
                    solutionDTO.getCreationTime(),
                    null);

            return SolutionDTO.solutionToDTO().apply(solutionRepository.save(newSolution));
        }
    }

    /**
     * Deletes Solution with the given id
     * @param solutionId Primary id of Solution to be deleted
     */
    public SolutionDTO deleteSolution(int solutionId){
        Solution solution = solutionRepository.findById(solutionId).orElse(null);
        if(solution == null)
            return null;
        solutionRepository.delete(solution);
        return new SolutionDTO(
                solution.getSolutionId(),
                solution.getStackTraceId().getStacktraceId(),
                StacktraceUserDTO.userToDTO().apply(solution.getUserId()),
                solution.getBody(),
                solution.getAdminSelected(),
                solution.getCreationTime()
        );
    }
}
