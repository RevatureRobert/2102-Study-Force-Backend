package com.revature.studyforce.notification.model;

import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @Column(name = "body")
    private String body;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "time_to_live")
    private Timestamp timeToLive;

    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "feature_area_id")
    @Enumerated
    private FeatureArea featureArea;


    @Column(name = "reference_id")
    private int referenceId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
