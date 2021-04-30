package com.revature.studyforce.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Model for User
 * @author Lok Kan Kung
 * @author Daniel Bernier
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue()
    @Column(name = "user_id")
    private int userId;

    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

}
