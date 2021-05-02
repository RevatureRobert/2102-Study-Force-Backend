package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.SolutionDTO;
import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.service.SolutionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller used to handle updates for votes on a solution.
 * @author Joey Elmblad
 */
@RestController
@CrossOrigin
@RequestMapping("stacktrace/solution-vote")
public class SolutionVoteController {

    @Autowired
    SolutionVoteService solutionVoteService;

    /**
     * For a given solutionId returns all of the votes for that specific solution
     * @param solutionId the id that ties the votes with the solution
     * @return
     */
    @GetMapping("/{solutionId}")
//    public List<SolutionVote> getSolutionVoteBySolutionId(@PathVariable(name = "solutionId") int solutionId){
//        return solutionVoteService.getAllSolutionsVotesForSolution(solutionId);
//    }
    public List<SolutionVoteDTO> getSolutionVoteBySolutionId(@PathVariable(name = "solutionId") int solutionId){
        return solutionVoteService.getAllSolutionsVotesForSolution(solutionId);
    }

    /**
     * When a user votes they send their preference to the database and can't submit another vote for that solution
     * @param solutionVoteDTO
     * @return
     */
    @PostMapping("/{solutionId}")
    public SolutionVote submitVoteForSolutionId(@RequestBody SolutionVote solutionVote){
        return solutionVoteService.sumbitVote(solutionVote);
    }
//    public SolutionVoteDTO submitVoteForSolutionId(@RequestBody SolutionVoteDTO solutionVoteDTO){
//        return solutionVoteService.sumbitVote(solutionVoteDTO);
//    }

    /**
     * This should delete every vote on a specific solution.
     * @param solutionId the id that each vote will have for given solution
     */
    @DeleteMapping("/{solutionId}")
    public void deleteSolutionVotes(@PathVariable int solutionId){
        solutionVoteService.deleteSolutionVote(solutionId);
    }

}
