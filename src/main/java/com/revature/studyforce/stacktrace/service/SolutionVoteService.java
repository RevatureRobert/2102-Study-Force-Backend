package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.SolutionVoteRepository;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service used to handle requests for voting on a solution using {@link SolutionVoteRepository}
 * @author Joey Elmblad
 */
@Service
public class SolutionVoteService {
    private final SolutionVoteRepository solutionVoteRepository;
    private final UserRepository userRepository;
    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionVoteService(SolutionVoteRepository solutionVoteRepository,
                               UserRepository userRepository, SolutionRepository solutionRepository){
        this.solutionVoteRepository = solutionVoteRepository;
        this.userRepository = userRepository;
        this.solutionRepository = solutionRepository;
    }

    /**
     *  The controller used to return all votes for a given solution using {@link SolutionVoteRepository#findBySolutionId(int)}
     * @param solutionId the value that will bring back every vote for a given solution.
     * @return will return a list of votes on a given solution.
     */
    public List<SolutionVoteDTO> getAllSolutionsVotesForSolution(int solutionId){
        List<SolutionVote> solutionVotes = solutionVoteRepository.findBySolutionId(solutionId);
        List<SolutionVoteDTO> solutionVoteDTOS = new ArrayList<>();

        for(SolutionVote solutionVote : solutionVotes){
            SolutionVoteDTO solutionVoteDTO = new SolutionVoteDTO();
            solutionVoteDTO.setSolutionVoteId(solutionVote.getSolutionVoteId());
            solutionVoteDTO.setSolutionId(solutionVote.getSolutionId().getSolutionId());
            solutionVoteDTO.setUserId(solutionVote.getUserId().getUserId());
            solutionVoteDTO.setValue(solutionVote.getValue());
            solutionVoteDTOS.add(solutionVoteDTO);
        }

        return solutionVoteDTOS;
    }

    /**
     * This will save a vote for a specific solution, should only be able to vote on a comment once using {@link SolutionRepository#save}
     * @param solutionVoteDTO The solution object that will hold the users vote information.
     * @return will return a solutionVoteDTO for a given user, with a given solution.
     */
    public SolutionVoteDTO submitVote(SolutionVoteDTO solutionVoteDTO){

        SolutionVote solutionVote = new SolutionVote();
        solutionVote.setSolutionId(solutionRepository.findById(solutionVoteDTO.getSolutionId()).orElse(null));
        solutionVote.setUserId(userRepository.findById(solutionVoteDTO.getUserId()).orElse(null));
        solutionVote.setValue(solutionVoteDTO.getValue());
        solutionVoteRepository.save(solutionVote);

        return solutionVoteDTO;
    }

}
