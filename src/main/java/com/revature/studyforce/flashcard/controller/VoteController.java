package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.VoteDTO;
import com.revature.studyforce.flashcard.model.Vote;
import com.revature.studyforce.flashcard.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling the vote service {@link VoteService}
 * @author Elizabeth Ye
 */
@RestController
@CrossOrigin
@RequestMapping("/flashcards/votes")
public class VoteController {

    @Autowired
    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * POST mapping for '/votes' in {@link VoteService#addVote(VoteDTO)}
     * Adds a new answer vote to the database
     * @param voteDTO A DTO for a vote object that has the information to be persisted
     * @return The vote that was added
     */
    @PostMapping
    public Vote createAnswerVote(@RequestBody VoteDTO voteDTO) {
        return voteService.addVote(voteDTO);
    }

    /**
     * GET request for 'getVote' in VoteService {@link VoteService#getVote(int, int)}
     * @param answerId The answer id you want to get the vote from
     * @param userId The user id of the user who submitted the vote
     * @return The vote if there is a match, BAD.REQUEST if it wasn't found.
     */
    @GetMapping
    public VoteDTO getVote(@RequestParam int answerId, @RequestParam int userId){
        return voteService.getVote(answerId,userId);
    }

    /**
     * GET request for 'getVote' in VoteService {@link VoteService#getAll(int)}
     * @param answerId The answer id you want to get the rating from
     * @return The vote if there is a match, BAD.REQUEST if it wasn't found.
     */
    @GetMapping("/all")
    public List<VoteDTO> getAllVotes(@RequestParam int answerId){
        return voteService.getAll(answerId);
    }
}
