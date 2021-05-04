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

    private int solutionVoteId;

    private int userId;

    private int solutionId;

    private int value;

}
