package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
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
     * User who created stacktrace
     */
    private StacktraceUserDTO creator;

    /**
     * the title a user gives to a stacktrace
     */
    private String title;

    /**
     * the body text of a stacktrace
     */
    private String body;

    /**
     * The technology that the stacktrace is using.ex JAVA
     */
    private Technology technology;

    /**
     * The timestamp of when the stacktrace was created
     */
    private Timestamp creationTime;

    public static Function<Stacktrace, StacktraceDTO> stacktraceToDTO() {
    return stacktrace -> new StacktraceDTO(
          stacktrace.getStacktraceId(),
          StacktraceUserDTO.userToDTO().apply(stacktrace.getUserId()),
          stacktrace.getTitle(),
          stacktrace.getBody(),
          stacktrace.getTechnology(),
          stacktrace.getCreationTime());
    }
}
