package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.VoteDTO;
import com.revature.studyforce.flashcard.model.Vote;
import com.revature.studyforce.flashcard.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling the vote service {@link VoteService}
 * @author Elizabeth Ye
 */
@RestController
@CrossOrigin
@RequestMapping("/flashcards/vote")
public class VoteController {

    @Autowired
    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * Adds a new answer vote to the database
     * @param vote A DTO for a vote object that has the information to be persisted
     * @return The vote that was added
     */
    @PostMapping
    public Vote createAnswerVote(@RequestBody VoteDTO vote) {
        return voteService.addVote(vote);
    }
}
