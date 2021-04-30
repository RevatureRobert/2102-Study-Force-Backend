package com.revature.StudyForce.flashcard.controller;

import com.revature.StudyForce.flashcard.dto.AnswerVoteDTO;
import com.revature.StudyForce.flashcard.model.AnswerVote;
import com.revature.StudyForce.flashcard.service.AnswerVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards/vote")
public class AnswerVoteController {

    private final AnswerVoteService VOTE_SERVICE;

    @Autowired
    public AnswerVoteController(AnswerVoteService voteService) {
        this.VOTE_SERVICE = voteService;
    }

    @PostMapping
    public ResponseEntity<AnswerVote> createAnswerVote(@RequestBody AnswerVote answerVote) {
        if (VOTE_SERVICE.addAnswerVote(answerVote) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(answerVote);
    }
}
