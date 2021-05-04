package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.SolutionRepository;
import com.revature.studyforce.stacktrace.repository.SolutionVoteRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    SolutionRepository solutionRepository;

    /**
     *  The controller used to return all votes for a given solution.
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
            BeanUtils.copyProperties(solutionVote,solutionVoteDTO);
            solutionVoteDTOS.add(solutionVoteDTO);
        }

        return solutionVoteDTOS;
    }

    /**
     * This will save a vote for a specific solution, should only be able to vote on a comment once.
     * @param solutionVoteDTO The solution object that will hold the users vote information.
     * @return will return a solutionVote for a given user, with a given solution.
     */
    public SolutionVoteDTO submitVote(SolutionVoteDTO solutionVoteDTO){
        SolutionVote solutionVote = new SolutionVote();

        //need a user and a solution
        int solutionIdValue = solutionVoteDTO.getSolutionId();
        Solution solutionEdit = new Solution();
        solutionEdit = solutionRepository.findBySolutionId(solutionIdValue);
        solutionVote.setSolutionId(solutionEdit);

        int userIdValue = solutionVoteDTO.getUserId();
        User user = new User();
        user = userRepository.findByUserId(userIdValue);
        solutionVote.setUserId(user);

        solutionVote.setValue(solutionVoteDTO.getValue());

        solutionVoteRepository.save(solutionVote);

        return solutionVoteDTO;
    }

}
