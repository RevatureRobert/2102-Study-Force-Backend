package com.revature.studyforce.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

/**
 * Model for User
 * @author Lok Kan Kung
 * @author Daniel Bernier
 * @author Nick Wickham
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

    @Size(max = 511)
    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @ColumnDefault("true")
    @Column(name = "is_subscribed_flashcard")
    private boolean isSubscribedFlashcard;

    @ColumnDefault ("true")
    @Column(name = "is_subscribed_stacktrace")
    private boolean isSubscribedStacktrace;

    /*
    make .STRING AND USER FOR LOCAL DATABASE
     */
    @Enumerated(EnumType.STRING)
    @ColumnDefault ("USER")
    @JoinColumn(name = "authority_id", referencedColumnName = "authority_id")
    @NotNull
    private Authority authority;

    @CreationTimestamp
    @Column(name = "registration_time")
    private Timestamp registrationTime;

    @Column(name = "last_login")
    private Timestamp lastLogin;

}
