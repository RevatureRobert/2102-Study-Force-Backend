package com.revature.StudyForce.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Stacktraces {

    @Id
    @GeneratedValue
    private int stackstrace_id;

    @NotNull
    private int creator_id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotNull
    private int technology_id;

    @UpdateTimestamp
    Timestamp creation_time;

}
