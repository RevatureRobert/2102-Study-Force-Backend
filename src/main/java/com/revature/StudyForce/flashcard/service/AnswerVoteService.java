package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.AnswerVoteDTO;
import com.revature.StudyForce.flashcard.model.AnswerVote;
import com.revature.StudyForce.flashcard.repository.AnswerVoteRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class AnswerVoteService {

    private AnswerVoteRepo voteRepo;
    private UserRepo userRepo;
    private AnswerRepo answerRepo;

    @Autowired
    public AnswerVoteService(AnswerVoteRepo voteRepo){
        this.voteRepo = voteRepo;
    }

    public AnswerVoteDTO addAnswerVote(AnswerVote vote) {

        User user = userRepo.findById(vote.getUser().getUserId()).orElse(null);
        Answer answer = answerRepo.findById(vote.getAnswer().getAnswerId()).orElse(null);
        if (user == null || answer == null) {
            System.out.println("Invalid request.");
            return null;
        }
        AnswerVote v = voteRepo.save(vote);
        AnswerVoteDTO voteDTO = new AnswerVoteDTO();
        voteDTO.setVoteId(v.getVoteId());
        voteDTO.setVoteValue(v.getVoteValue());
        return voteDTO;
    }
}
