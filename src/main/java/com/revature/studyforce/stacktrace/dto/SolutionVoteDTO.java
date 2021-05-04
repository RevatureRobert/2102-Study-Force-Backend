package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.user.model.User;
import lombok.*;

import java.util.function.Function;


/**
 * Solution DTO used to transfer solution data
 *
 * @author Joey Elmblad
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionVoteDTO {

    /**
     * This is the primary key for the solution vote, to track if a user has voted once on a solution yet.
     */
    private int solutionVoteId;

    /**
     *  This is a foreign key for the User table to track which user has posted this possible solution.
     */
    private User userId;

    /**
     * This is a foreign key for the Solution table which tracks which solution this vote will count towards.
     */
    private Solution solutionId;

    /**
     * This represents the like or dislike button value.
     */
    private int value;

    /**
     * Returns a funtion used to converts SolutionVote to SolutionVoteDTO
     * @return Funtion used to convert SolutionVote to SolutionVoteDTO
     */
    public static Function<SolutionVote, SolutionVoteDTO> solutionVoteToDTO(){
        return solutionVote -> {
            if(solutionVote == null){
                throw new IllegalArgumentException("Parameter user cannot be null");
            }
            return new SolutionVoteDTO(
                    solutionVote.getSolutionVoteId(),
                    solutionVote.getUserId(),
                    solutionVote.getSolutionId(),
                    solutionVote.getValue());
        };
    }

    /**
     * Returns a funtion used to converts SolutionVoteDTO to SolutionVote
     * @return Funtion used to convert SolutionVoteDTO to SolutionVote
     */
    public static Function<SolutionVoteDTO, SolutionVote> dtoToSolutionVote(){
        return solutionVoteDTO -> {
            if(solutionVoteDTO == null){
                throw new IllegalArgumentException("Parameter user cannot be null");
            }
            return new SolutionVote(
                    solutionVoteDTO.getSolutionVoteId(),
                    solutionVoteDTO.getUserId(),
                    solutionVoteDTO.getSolutionId(),
                    solutionVoteDTO.getValue());
        };
    }
}
