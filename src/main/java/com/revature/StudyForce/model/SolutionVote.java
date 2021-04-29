package com.revature.StudyForce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.revature.StudyForce.model.User;
import com.revature.StudyForce.model.Solution;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SolutionVote {
    @Id
    @GeneratedValue
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
