package com.revature.StudyForce.flashcard.controller;

import com.revature.StudyForce.flashcard.dto.VoteDTO;
import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards/vote")
public class VoteController {

    @Autowired
    private final VoteService VOTE_SERVICE;

    @Autowired
    public VoteController(VoteService voteService) {
        this.VOTE_SERVICE = voteService;
    }

    @PostMapping("/")
    public Vote createAnswerVote(@RequestBody VoteDTO vote) {
        return VOTE_SERVICE.addVote(vote);
    }


}
