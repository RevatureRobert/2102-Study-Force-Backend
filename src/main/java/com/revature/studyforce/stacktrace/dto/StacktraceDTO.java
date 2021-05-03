package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
import java.util.function.Function;

/**
 * Stacktrace DTO used to transfer Stacktrace Data
 * @author John Stone
 * @authore Joshua Swanson
 */
@Data
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
    private User user;

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
    private Technology technologyId;

    /**
     * The timestamp of when the stacktrace was created
     */
    private Timestamp creationTime;

    private Set<Solution> solutions;

    public static Function<Stacktrace, StacktraceDTO> stacktraceToDTO() {
        return stacktrace -> {
            return new StacktraceDTO(stacktrace.getStacktraceId(),
                    stacktrace.getUserId(),
                    stacktrace.getTitle(),
                    stacktrace.getBody(),
                    stacktrace.getTechnologyId(),
                    stacktrace.getCreationTime(),
                    stacktrace.getSolutions());
        };
    }

    public static Function<StacktraceDTO, Stacktrace> DTOToStacktrace() {
        return stacktraceDTO -> {
            return new Stacktrace(stacktraceDTO.getStacktraceId(),
                    stacktraceDTO.getUser(),
                    stacktraceDTO.getTitle(),
                    stacktraceDTO.getBody(),
                    stacktraceDTO.getTechnologyId(),
                    stacktraceDTO.getCreationTime(),
                    stacktraceDTO.getSolutions());
        };
    }
}
