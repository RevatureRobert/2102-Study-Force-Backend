package com.revature.StudyForce.flashcard.model;

import com.revature.StudyForce.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Model to store each vote on submitted flashcard answers
 * @author Elizabeth Ye
 */
@Entity
@Table(name = "answer_vote")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    /**
     * Serial primary key voteId for Vote object
     */
    @Id
    @GeneratedValue
    private int voteId;

    /**
     * Value of vote, integer of -1 or 1 for down votes and up votes respectively
     */
    @NotNull
    private int voteValue;

    /**
     * Linked to the answer the user is rating
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    /**
     * Linked to the user performing the vote
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
