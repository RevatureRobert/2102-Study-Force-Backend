package com.revature.studyforce.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Basic Model for batches
 * @author Daniel Bernier
 * @author Sam Daniel
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "batches")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "batch_id")
    int batchId;

    @Column(unique = true)
    String name;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "instructors_batches", joinColumns = {@JoinColumn(name = "batch_id")}, inverseJoinColumns = {@JoinColumn(name = "instructor_id")})
    Set<User> instructors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_batches", joinColumns = {@JoinColumn(name = "batch_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    Set<User> users;

    @CreationTimestamp
    @Column(name = "creation_time")
    Timestamp creationTime;
}
