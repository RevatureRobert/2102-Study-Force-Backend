package com.revature.studyforce.stacktrace.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

import com.revature.studyforce.user.model.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * This model represents the solutions users will supply the Stacktrace owner.
 * The Stacktrace owner will choose one of these solutions that solves his problem.
 * @author Joey Elmblad
 * @author Joshua Swanson
 */

@Entity
@Table(name = "solution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Solution {

    /**
     * This is the serial primary key for the solution table
     */
    @Id
    @Column(name = "solution_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int solutionId;

    /**
     * This is the foreign key which will be connect to the Stacktrace a user has picked as their solution.
     * Should be nullable if solution isn't picked for given solution.
     */
    @JsonIgnoreProperties("solutions")
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "stacktrace_id")
    private Stacktrace stackTraceId;

    /**
     * This is the foreign key which connects the user table with the solution they have created.
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "creator_id")
    @NotNull
    private User userId;

    /**
     * This is the body of text the user will explain a possible solution for the Stacktrace.
     */
    @Column
    @NotNull
    private String body;

    /**
     * This is the boolean used to give the admin the ability to pick a solution over the Stacktrace creator.
     */
    @Column(name = "admin_selected")
    private Boolean adminSelected;

    /**
     * This is the boolean used to give the user the ability to pick a solution.
     */
    @Column(name = "user_selected")
    private Boolean userSelected;

    /**
     * This is a timestamp of the time a solution was supplied.
     */
    @Column(name = "creation_time")
    @UpdateTimestamp
    private Date creationTime;

    /**
     * This will be the sum of votes for any given solution
     */
    @Column(name = "total_vote", nullable = false, columnDefinition = "int default 0")
    private int totalVote;


    /**
     * Bidirectional relationship needed to cascade delete SolutionVotes
     */
    @JsonBackReference
    @OneToMany(mappedBy = "solutionId", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SolutionVote> solutionVotes;
}
