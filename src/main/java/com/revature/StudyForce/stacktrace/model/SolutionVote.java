package com.revature.StudyForce.stacktrace.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.StudyForce.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * This model represents the solutions users will supply the Stacktrace owner.
 * The Stacktrace owner will choose one of these solutions that solves his problem.
 * @author Joey Elmblad
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SolutionVote {

    /**
     * This is the primary key for the solution vote, to track if a user has voted once on a solution yet.
     */
    @Id
    @GeneratedValue
    @Column(name = "solution_vote_id")
    private int id;

    /**
     *  This is a foreign key for the User table to track which user has posted this possible solution.
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * This is a foreign key for the Solution table which tracks which solution this vote will count towards.
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "solution_id")
    private Solution solution;
    @Min(-1)
    @Max(1)
    private int value;
}