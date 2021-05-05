package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.repository.AnswerRepository;
import com.revature.studyforce.flashcard.repository.VoteRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import com.revature.studyforce.flashcard.dto.VoteDTO;
import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Service class for the VoteRepository {@link VoteRepository}
 * @author Elizabeth Ye
 */
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

    /**
     * function to persist a vote to the database if there is a connected user and answer
     * else, throws appropriate exception
     * @param vote a DTO for the Vote model that transfers the query from the front end
     * @return a Vote object that is saved to the database
     */
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
        return voteRepository.save(v);
    }
}
