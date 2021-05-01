package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.VoteDTO;
import com.revature.StudyForce.flashcard.model.Answer;
import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.repository.AnswerRepository;
import com.revature.StudyForce.flashcard.repository.VoteRepository;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class VoteService {

    private VoteRepository voteRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepo, AnswerRepository answerRepo){
        this.voteRepository = voteRepository;
        this.userRepository = userRepo;
        this.answerRepository = answerRepo;
    }

    public VoteDTO addVote(Vote vote) {
        Optional<Answer> answer = answerRepository.findById(vote.getAnswer().getAnswerId());
        Optional<User> user = userRepository.findById(vote.getUser().getUserId());

        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");
        if(!answer.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Answer not found exception");
        if(vote.getVoteValue() < -1 || vote.getVoteValue() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid vote value exception");
        }

        voteRepository.save(vote);
        return new VoteDTO(vote.getVoteId(),vote.getVoteValue(),answer.get().getAnswerId(),user.get().getUserId());
    }
}
