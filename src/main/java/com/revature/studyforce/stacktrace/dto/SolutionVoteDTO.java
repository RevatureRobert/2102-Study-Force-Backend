package com.revature.studyforce.stacktrace.dto;

import lombok.*;

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
