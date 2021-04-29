package com.revature.StudyForce.stacktrace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.stacktrace.model.Stacktrace;

@Entity
@Table(name = "solution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id
    @Column(name = "solution_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int solutionId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "stacktrace_id")
    @NotNull
    private Stacktrace stackTraceId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "creator_id")
    @NotNull
    private User userId;

    @Column
    @NotNull
    private String body;

    @Column(name = "admin_selected")
    private Boolean adminSelected;

    @Column(name = "creation_time")
    private Date createtionTime;

}
