package com.revature.StudyForce.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
