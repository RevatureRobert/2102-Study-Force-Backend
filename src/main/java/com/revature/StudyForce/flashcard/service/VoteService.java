package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.VoteDTO;
import com.revature.StudyForce.flashcard.model.Answer;
import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.repository.AnswerRepo;
import com.revature.StudyForce.flashcard.repository.VoteRepo;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class VoteService {

    private VoteRepo voteRepo;
    private UserRepository userRepo;
    private AnswerRepo answerRepo;

    @Autowired
    public VoteService(VoteRepo voteRepo, UserRepository userRepo, AnswerRepo answerRepo){
        this.voteRepo = voteRepo;
        this.userRepo = userRepo;
        this.answerRepo = answerRepo;
    }

    public VoteDTO addVote(Vote vote) {

        User user = userRepo.findById(vote.getUser().getUserId()).orElse(null);
        Answer answer = answerRepo.findById(vote.getAnswer().getAnswerId()).orElse(null);
        if (user == null) {
            User u = new User();
            u.setUserId(vote.getUser().getUserId());
            userRepo.save(u);
        }
        if (answer == null) {
            Answer a = new Answer();
            a.setAnswerId(vote.getAnswer().getAnswerId());
            answerRepo.save(a);
        }
        Vote v = voteRepo.save(vote);
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setVoteId(v.getVoteId());
        voteDTO.setVoteValue(v.getVoteValue());
        return voteDTO;
    }
}
