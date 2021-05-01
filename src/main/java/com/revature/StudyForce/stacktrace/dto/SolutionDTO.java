package com.revature.StudyForce.stacktrace.dto;

import com.revature.StudyForce.stacktrace.model.Solution;
import com.revature.StudyForce.stacktrace.model.Stacktrace;
import com.revature.StudyForce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.function.Function;


/**
 * Solution DTO used to transfer solution data
 *
 * @author Joshua Swanson
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDTO {
    private int solutionId;
    private Stacktrace stackTraceId;
    private User userId;
    private String body;
    private Boolean adminSelected;
    private Date creationTime;

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
                    solution.getCreationTime()
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
                    solutionDTO.getCreationTime()
            );
        };
    }
}
