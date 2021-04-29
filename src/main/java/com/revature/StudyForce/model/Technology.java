package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "technologies")
@Getter
@Setter
@NoArgsConstructor
public class Technology {
    @Id
    @GeneratedValue
    @Column(name = "technology_id")
    private int technologyId;
    @Size(max = 32)
    @Column(name = "technology_name", nullable = false)
    private String technologyName;
}
