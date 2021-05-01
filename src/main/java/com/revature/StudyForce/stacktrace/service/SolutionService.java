package com.revature.StudyForce.stacktrace.service;

import com.revature.StudyForce.stacktrace.dto.SolutionDTO;
import com.revature.StudyForce.stacktrace.model.Solution;
import com.revature.StudyForce.stacktrace.repository.SolutionRepository;
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

    @Autowired
    SolutionRepository solutionRepository;

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
        return SolutionDTO.solutionToDTO().apply(solutionRepository.save(SolutionDTO.dtoToSolution().apply(solutionDTO)));
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
            Solution newSolution = SolutionDTO.dtoToSolution().apply(solutionDTO);
            solutionRepository.save(newSolution);
            return solutionDTO;
        }
    }

    /**
     * Deletes Solution with the given id
     * @param solutionId Primary id of Solution to be deleted
     */
    public void deleteSolution(int solutionId){
        solutionRepository.deleteBySolutionId(solutionId);
    }
}
