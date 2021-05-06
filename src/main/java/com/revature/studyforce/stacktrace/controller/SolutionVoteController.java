package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.SolutionVoteDTO;
import com.revature.studyforce.stacktrace.service.SolutionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller used to handle updates for votes on a solution.
 * @author Joey Elmblad
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("stacktrace/solution-vote")
public class SolutionVoteController {

    @Autowired
    SolutionVoteService solutionVoteService;

    /**
     * For a given solutionId returns all of the votes for that specific solution
     * these shouldn't be pageable because they are only used to be added together
     * using {@link SolutionVoteService#getAllSolutionsVotesForSolution(int)}
     *
     * @param solutionId the id that ties the votes with the solution
     * @return returns a list of votes for a specific solutionId
     */
    @GetMapping("/{solutionId}")
    public List<SolutionVoteDTO> getSolutionVoteBySolutionId(@PathVariable(name = "solutionId") int solutionId){
        return solutionVoteService.getAllSolutionsVotesForSolution(solutionId);
    }

  /**
   * When a user votes they send their preference to the database and can't submit another vote for
   * that solution using {@link SolutionVoteService#submitVote(SolutionVoteDTO)}
   *
   * @param solutionVoteDTO A data transfer object containing the solutionVoteId, userId, solutionId, and value.
   * @return returns a data transfer object containing the solutionVoteId, userId, solutionId, and value.
   */
  @PostMapping
  public SolutionVoteDTO submitVoteForSolutionId(@RequestBody SolutionVoteDTO solutionVoteDTO) {
        return solutionVoteService.submitVote(solutionVoteDTO);
    }

}
