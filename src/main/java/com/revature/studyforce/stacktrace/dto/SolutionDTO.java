package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.user.model.User;
import lombok.*;

import java.sql.Date;
import java.util.Set;
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
    private Stacktrace stackTraceId;
    private User userId;
    private String body;
    private Boolean adminSelected;
    private Date creationTime;
    private Set<SolutionVote> solutionVotes;

    /**
     * Returns a funtion used to converts Solution to SolutionDTO
     * @return Funtion used to convert Solution to SolutionDTO
     */
    public static Function<Solution, SolutionDTO> solutionToDTO(){
        return solution -> {
            if(solution == null){
                throw new IllegalArgumentException("Parameter user cannot be null");
            }
            return new SolutionDTO(
                    solution.getSolutionId(),
                    solution.getStackTraceId(),
                    solution.getUserId(),
                    solution.getBody(),
                    solution.getAdminSelected(),
                    solution.getCreationTime(),
                    solution.getSolutionVotes()
            );
        };
    }

    /**
     * Returns a funtion used to converts SolutionDTO to Solution
     * @return Funtion used to convert SolutionDTO to Solution
     */
    public static Function<SolutionDTO, Solution> dtoToSolution(){
        return solutionDTO -> {
            if(solutionDTO == null){
                throw new IllegalArgumentException("Parameter user cannot be null");
            }
            return new Solution(
                    solutionDTO.getSolutionId(),
                    solutionDTO.getStackTraceId(),
                    solutionDTO.getUserId(),
                    solutionDTO.getBody(),
                    solutionDTO.getAdminSelected(),
                    solutionDTO.getCreationTime(),
                    solutionDTO.getSolutionVotes()
            );
        };
    }
}
