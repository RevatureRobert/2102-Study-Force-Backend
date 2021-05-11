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
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault ("1")
    @JoinColumn(name = "authority_id", referencedColumnName = "authority_id")
    @NotNull
    private Authority authority= Authority.ROLE_USER;

    @CreationTimestamp
    @Column(name = "registration_time")
    private Timestamp registrationTime;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    public User(String email, String name){
        this.email = email;
        this.name= name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("StudyForce does not retain password information.");
    }

    /**
     *
     * @return true always.
     * Due to the nature of StudyForce as a stateless authenticated app,
     * possessing user details implies a non-expired token was received.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
