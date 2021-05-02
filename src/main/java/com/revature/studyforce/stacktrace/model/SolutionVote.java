package com.revature.studyforce.stacktrace.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.studyforce.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * The solution_vote table will hold the number of votes a solution gets,
 * and that a user can only vote on a solution once.
 * @author Joey Elmblad
 */

@Entity()
@Table(name = "solution_vote",uniqueConstraints= @UniqueConstraint(columnNames = {"user_id", "solution_id"}) )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionVote {

    /**
     * The solution_vote_id is the primary key for the solutionVote table to show each vote from a user on a solution.
     */
    @Id
    @GeneratedValue
    @Column(name = "solution_vote_id")
    private int solutionVoteId;

    /**
     *  This is a foreign key for the User table to track if a solution has been voted on yet by this user.
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User userId;

    /**
     * This is a foreign key for the Solution table which tracks which solution this vote will count towards.
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "solution_id")
    private Solution solutionId;

    /**
     * This represents the like or dislike button value.
     */
    @Min(-1)
    @Max(1)
    private int value;
}