package com.revature.StudyForce.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    Integer notificationId;

    @Column(name = "notification_message")
    private String notificationMessage;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "time_to_live")
    private Date timeToLive;

    @Column(name = "feature_area_id")
    private int featureAreaId;

    @Column(name = "application_user_id")
    private int applicationUserId;

}
