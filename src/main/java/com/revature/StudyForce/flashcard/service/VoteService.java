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

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, AnswerRepository answerRepository){
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    public Vote addVote(VoteDTO vote) {
        Optional<Answer> answer = answerRepository.findById(vote.getAnswerId());
        Optional<User> user = userRepository.findById(vote.getUserId());

        if(!answer.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Answer not found exception");
        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");
        if(vote.getValue() < -1 || vote.getValue() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid vote value exception");
        }

        Vote v = new Vote(0,vote.getValue(),answer.get(),user.get());
        System.out.println(v.toString());
        return voteRepository.save(v);
    }
}
