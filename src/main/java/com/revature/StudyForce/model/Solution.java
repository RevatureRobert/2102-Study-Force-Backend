package com.revature.StudyForce.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "solution")
@Getter
@Setter
public class Solution {

    @Id
    @Column(name = "solution_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int solutionId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "stacktrace_id")
    private Stractrace stackTraceId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private Users usersId;

    @Column
    private String body;

    @Column(name = "admin_selected")
    private Boolean adminSelected;

    @Column(name = "creation_time")
    private Date createtionTime;

}
