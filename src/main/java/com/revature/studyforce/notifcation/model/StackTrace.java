package com.revature.studyforce.notifcation.model;


import com.revature.studyforce.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stackTrace")
public class StackTrace {

    @Id
    @GeneratedValue
    @Column(name = "stackTraceId")
    private int stackTraceId;


    @OneToOne
    private User user;




}
