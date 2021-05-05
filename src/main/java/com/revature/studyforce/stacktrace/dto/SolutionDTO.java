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
    private int userId;
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
                    solution.getStackTraceId().getStacktraceId(),
                    solution.getUserId().getUserId(),
                    solution.getBody(),
                    solution.getAdminSelected(),
                    solution.getCreationTime()
            );
        };
    }
}
