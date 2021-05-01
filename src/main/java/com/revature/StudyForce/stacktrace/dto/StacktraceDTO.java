package com.revature.StudyForce.stacktrace.dto;

import com.revature.StudyForce.stacktrace.model.Stacktrace;
import com.revature.StudyForce.stacktrace.model.Technology;
import com.revature.StudyForce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.function.Function;

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

    public static Function<Stacktrace, StacktraceDTO> stacktraceToDTO() {
        return stacktrace -> {
            return new StacktraceDTO(stacktrace.getStacktraceId(),
                    stacktrace.getUserId(),
                    stacktrace.getTitle(),
                    stacktrace.getBody(),
                    stacktrace.getTechnologyId(),
                    stacktrace.getCreationTime());
        };
    }

    public static Function<StacktraceDTO, Stacktrace> DTOToStacktrace() {
        return stacktraceDTO -> {
            return new Stacktrace(stacktraceDTO.getStacktraceId(),
                    stacktraceDTO.getUser(),
                    stacktraceDTO.getTitle(),
                    stacktraceDTO.getBody(),
                    stacktraceDTO.getTechnologyId(),
                    stacktraceDTO.getCreationTime());
        };
    }
}
