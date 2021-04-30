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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stacktrace_id")
    private int stacktraceId;

    /**
     * ID of user who created stacktrace
     */
    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User userId;

    /**
     * the title a user gives to a stacktrace
     */
    @NotNull
    @Column(name = "title")
    private String title;

    /**
     * the body text of a stacktrace
     */
    @NotNull
    @Column(name = "body")
    private String body;

    /**
     * The technology that the stacktrace is using.ex JAVA
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "technology_id", referencedColumnName = "technology_id")
    private Technology technologyId;

    /**
     *The timestamp of when the stacktrace was created
     */
    @Column(name = "creation_time")
    @UpdateTimestamp
    private Timestamp creationTime;

}
