package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.SolutionVoteRepository;
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
public class SolutionVoteService {

    @Autowired
    SolutionVoteRepository solutionVoteRepository;

    public List<SolutionVoteDTO> getAllSolutionsVotesForSolution(int stackTraceId){
        List<SolutionVote> solutionVotes = solutionVoteRepository.findBySolutionId(stackTraceId);
        List<SolutionVoteDTO> solutionDTOS = new ArrayList<>();

        for(SolutionVote solutionVote : solutionVotes){
            solutionDTOS.add(SolutionVoteDTO.solutionVoteToDTO().apply(solutionVote));
        }

        return solutionDTOS;
    }

    /**
     * This will save a vote for a specific solution, should only be able to vote on a comment once.
     * @param solutionVoteDTO
     * @return
     */
    public SolutionVoteDTO sumbitVote(SolutionVoteDTO solutionVoteDTO){
        return SolutionVoteDTO.solutionVoteToDTO().apply(solutionVoteRepository.save(SolutionVoteDTO.dtoToSolutionVote().apply(solutionVoteDTO)));
    }

    /**
     * Deletes every vote on a solution.
     * @param solutionId
     */
    public void deleteSolutionVote(int solutionId){
        solutionVoteRepository.deleteBySolutionId(solutionId);
    }
}
