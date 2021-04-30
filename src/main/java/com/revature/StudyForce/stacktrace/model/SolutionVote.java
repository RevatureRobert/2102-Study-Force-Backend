package com.revature.StudyForce.stacktrace.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.StudyForce.user.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SolutionVote {
    @Id
    @GeneratedValue
    @Column(name = "solution_vote_id")
    private int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "solution_id")
    private Solution solution;
    @Min(-1)
    @Max(1)
    private int value;
}