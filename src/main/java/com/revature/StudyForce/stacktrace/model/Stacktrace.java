package com.revature.StudyForce.stacktrace.model;


import com.revature.StudyForce.user.model.User;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Stacktrace {
    /**
     * @author : Noel Shaji
     * Primary key
     */
    @Id
    @GeneratedValue
    private int stacktraceId;

    /**
     * ID of user who created stacktrace
     */
    @NotNull private User user;

    /**
     * the title a user gives to a stacktrace
     */
    @NotBlank
    private String title;

    /**
     * the body text of a stacktrace
     */
    @NotBlank
    private String body;

    /**
     * The technology that the stacktrace is using.ex JAVA
     */
    @NotNull private Technology technology;

    /**
     *The timestamp of when the stacktrace was created
     */
    @UpdateTimestamp
    private Timestamp creationTime;

}
