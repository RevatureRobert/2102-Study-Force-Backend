package com.revature.StudyForce.Users.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "batches")
public class Batches {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "batch_id")
    int batchId;

    @Column(unique = true)
    String name;

    @NotNull
    @ManyToMany
    Set<User> instructors;

    @OneToMany
    Set<User> users;

    @CreationTimestamp
    Timestamp creationDate;
}
