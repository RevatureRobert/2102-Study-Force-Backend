package com.revature.studyforce.stacktrace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Model for technology
 * A Technology can't be deleted if associated with
 * Stacktrace but can be edited.
 * @author John Stone
 */

@Entity
@Table(name = "technologies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Technology {
    /**
     * Primary Key for each entry
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technology_id")
    private int technologyId;
    /**
     * Name of Technology
     */
    @Size(max = 32)
    @Column(name = "technology_name", nullable = false)
    private String technologyName;
}