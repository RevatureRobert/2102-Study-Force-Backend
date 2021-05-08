package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.VoteDTO;
import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.model.Vote;
import com.revature.studyforce.flashcard.repository.AnswerRepository;
import com.revature.studyforce.flashcard.repository.VoteRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for the VoteRepository {@link VoteRepository}
 * @author Elizabeth Ye
 */
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private static final String ANSWER_EXCEPTION = "Answer not found exception";
    private static final String USER_EXCEPTION = "User not found exception";

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, AnswerRepository answerRepository){
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * method to persist a vote to the database if there is a connected user and answer
     * else, throws appropriate exception
     * @param vote a DTO for the Vote model that transfers the query from the front end
     * @return a Vote object that is saved to the database
     */
    public Vote addVote(VoteDTO vote) {
        Optional<Answer> answer = answerRepository.findById(vote.getAnswerId());
        Optional<User> user = userRepository.findById(vote.getUserId());

        if(!answer.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ANSWER_EXCEPTION);
        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,USER_EXCEPTION);
        if(vote.getValue() < -1 || vote.getValue() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid vote value exception");
        }

        Vote v = new Vote(0,vote.getValue(),answer.get(),user.get());
        return voteRepository.save(v);
    }

  /**
   * method to get the vote of an optional user if found else, throws appropriate exception
   *
   * @param answerId id of the answer of the vote we want
   * @param userId id of the user of the vote we want
   * @return the vote associated with the user and answer
   */
  public VoteDTO getVote(int answerId, int userId) {
    Optional<Answer> answer = answerRepository.findById(answerId);
    Optional<User> user = userRepository.findById(userId);
    if (!user.isPresent()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EXCEPTION);
    }
    if (!answer.isPresent()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ANSWER_EXCEPTION);
    }

    Optional<Vote> vote =
        voteRepository.findByAnswer_answerIdAndUser_userId(
            answer.get().getAnswerId(), user.get().getUserId());
    if (vote.isPresent()) {
      return VoteDTO.convertVoteToDto().apply(vote.get());
    } else {
      throw new ResponseStatusException(
          HttpStatus.GONE, "User vote not found for this flashcard answer");
      }
    }

    /**
     * Retrieves a List of all Votes for the given Answer
     * @param answerId - the answer for which to return all Votes
     * @return - returns a List of Votes according the the answerId
     */
    public List<VoteDTO> getAll(int answerId){
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (!answer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ANSWER_EXCEPTION);
        }

        return voteRepository.findByAnswerAnswerId(answer.get().getAnswerId()).stream().map(VoteDTO.convertVoteToDto()).collect(Collectors.toList());
    }
}
