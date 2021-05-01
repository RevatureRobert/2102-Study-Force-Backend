package com.revature.StudyForce.flashcard.controller;

import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards/vote")
public class VoteController {

    private final VoteService VOTE_SERVICE;

    @Autowired
    public VoteController(VoteService voteService) {
        this.VOTE_SERVICE = voteService;
    }

    @PostMapping
    public ResponseEntity<Vote> createAnswerVote(@RequestBody Vote vote) {
        if (VOTE_SERVICE.addVote(vote) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(vote);
    }
}
