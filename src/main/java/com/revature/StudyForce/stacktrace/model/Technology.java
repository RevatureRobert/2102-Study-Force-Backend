package com.revature.StudyForce.stacktrace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @Author John Stone
 * Model for technology
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
    @GeneratedValue
    @Column(name = "technology_id")
    private int technologyId;
    /**
     * Name of Technology
     */
    @Size(max = 32)
    @Column(name = "technology_name", nullable = false)
    private String technologyName;
}