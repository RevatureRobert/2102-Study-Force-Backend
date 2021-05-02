package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.SolutionVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service used to handle requests for voting on a solution.
 * @author Joey Elmblad
 */
@Service
public class SolutionVoteService {

    @Autowired
    SolutionVoteRepository solutionVoteRepository;

    /**
     *  The controller used to return all votes for a given solution.
     * @param solutionId the value that will bring back every vote for a given solution.
     * @return will return a list of votes on a given solution.
     */
//    public List<SolutionVote> getAllSolutionsVotesForSolution(int solutionId){
//        List<SolutionVote> solutionVotes = solutionVoteRepository.findBySolutionId(solutionId);
//        List<SolutionVote> solutionVoteDTOS = new ArrayList<>();
//
//        for(SolutionVote solutionVote : solutionVotes){
//            solutionVoteDTOS.add(solutionVote);
//        }
//
//        return solutionVoteDTOS;
//    }
    public List<SolutionVoteDTO> getAllSolutionsVotesForSolution(int solutionId){
        List<SolutionVote> solutionVotes = solutionVoteRepository.findBySolutionId(solutionId);
        List<SolutionVoteDTO> solutionVoteDTOS = new ArrayList<>();

        for(SolutionVote solutionVote : solutionVotes){
            solutionVoteDTOS.add(SolutionVoteDTO.solutionVoteToDTO().apply(solutionVote));
        }

        return solutionVoteDTOS;
    }

    /**
     * This will save a vote for a specific solution, should only be able to vote on a comment once.
     * @param solutionVoteDTO The solution object that will hold the users vote information.
     * @return will return a soltuionVote for a given user, with a given solution.
     */
    public SolutionVote submitVote(SolutionVote solutionVote){
        return solutionVoteRepository.save(solutionVote);
    }
//    public SolutionVoteDTO sumbitVote(SolutionVoteDTO solutionVoteDTO){
//        return SolutionVoteDTO.solutionVoteToDTO().apply(solutionVoteRepository.save(SolutionVoteDTO.dtoToSolutionVote().apply(solutionVoteDTO)));
//    }

    /**
     * Deletes every vote on a solution.
     * @param solutionId the identifier used to track every vote for a solution
     */
    public void deleteSolutionVote(int solutionId){
        solutionVoteRepository.deleteBySolutionId(solutionId);
    }
}
