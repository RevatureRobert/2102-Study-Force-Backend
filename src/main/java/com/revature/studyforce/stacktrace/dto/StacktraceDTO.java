package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import lombok.*;

import java.sql.Timestamp;
import java.util.function.Function;

/**
 * Stacktrace DTO used to transfer Stacktrace Data
 * @author John Stone
 * @author Joshua Swanson
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StacktraceDTO {
    /**
     * ID of stacktrace
     */
    private int stacktraceId;

    /**
     * ID of User who created stacktrace
     */

    private int userId;

    /**
     * Name of User who created stacktrace
     */
    private String userName;

    /**
     * the title a user gives to a stacktrace
     */
    private String title;

    /**
     * the body text of a stacktrace
     */
    private String body;

    /**
     * The Id of the technology that the stacktrace is using.ex JAVA
     */

    private int technologyId;

    /**
     * The name of the technology that the stacktrace is using.ex JAVA
     */
    private String technologyName;

    /**
     * the solutionId that is picked by the user or admin
     */
    private int chosenSolution;

    /**
     * The timestamp of when the stacktrace was created
     */
    private Timestamp creationTime;

    public static Function<Stacktrace, StacktraceDTO> stacktraceToDTO() {
    return stacktrace -> new StacktraceDTO(
          stacktrace.getStacktraceId(),
          stacktrace.getUserId().getUserId(),
          stacktrace.getUserId().getName(),
          stacktrace.getTitle(),
          stacktrace.getBody(),
          stacktrace.getTechnology().getTechnologyId(),
          stacktrace.getTechnology().getTechnologyName(),
          stacktrace.getChosenSolution(),
          stacktrace.getCreationTime());
    }
}
