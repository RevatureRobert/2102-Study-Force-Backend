package com.revature.StudyForce.stacktrace.model;


import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Stacktrace {

    @Id
    @GeneratedValue
    private int stackstraceId;

    //TODO: change ints to Objects
    @NotNull
    private int creatorId;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    //TODO: change ints to Objects
    @NotNull
    private int technologyId;

    @UpdateTimestamp
    private Timestamp creationTime;

}
