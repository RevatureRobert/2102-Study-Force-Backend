package com.revature.StudyForce.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feature_areas")
public class FeatureArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_area_id")
    private int FeatureAreaId;

    @Column(name = "feature_area_name")
    private String featureAreaName;

}
