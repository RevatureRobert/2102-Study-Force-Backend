package com.revature.studyforce.stacktrace.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revature.studyforce.user.model.User;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "stacktrace")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stacktrace {
    /**
     * Primary key
     * @author : Noel Shaji
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
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    @JsonIgnore
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
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "technology_id", referencedColumnName = "technology_id")
    @JsonIgnore
    private Technology technology;

    /**
     *The timestamp of when the stacktrace was created
     */
    @Column(name = "creation_time")
    @UpdateTimestamp
    private Timestamp creationTime;

    /**
     * Bidirectional relationship needed to cascade delete solutions
     */
    @JsonIgnoreProperties("stackTraceId")
    @OneToMany(mappedBy = "stackTraceId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Solution> solutions;
}
