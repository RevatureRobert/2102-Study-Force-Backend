package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Solution;
import lombok.*;

import java.sql.Date;
import java.util.function.Function;


/**
 * Solution DTO used to transfer solution data
 *
 * @author Joshua Swanson
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDTO {
    private int solutionId;
    private int stackTraceId;

    /**
     * Stacktrace Group userDTO that only sends userId, firstname and lastname
     */
    private int userId;
    private String userName;
    private String body;
    private Boolean adminSelected;
    private Date creationTime;
    private int totalVote;

    /**
     * Returns a function used to converts Solution to SolutionDTO
     * @return Function used to convert Solution to SolutionDTO
     */
    public static Function<Solution, SolutionDTO> solutionToDTO(){
        return solution -> {
            if(solution == null){
                throw new IllegalArgumentException("Parameter solution cannot be null");
            }
            return new SolutionDTO(
                    solution.getSolutionId(),
                    solution.getStackTraceId().getStacktraceId(),
                    solution.getUserId().getUserId(),
                    solution.getUserId().getName(),
                    solution.getBody(),
                    solution.getAdminSelected(),
                    solution.getCreationTime(),
                    solution.getTotalVote()
            );
        };
    }
}
