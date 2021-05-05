package com.revature.studyforce.notification.model;

import com.revature.studyforce.notification.dto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "notification_message")
    private String notificationMessage;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "time_to_live")
    private Timestamp timeToLive;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "feature_area_id")
    @Enumerated
    private FeatureArea featureArea;

    @Column(name = "application_user_id")
    private int applicationUserId;

    public Notification(NotificationDto notificationDto){
        this.notificationId = notificationDto.getId();
        this.notificationMessage = notificationDto.getMessage();
        this.isRead = notificationDto.isRead();
        this.timeToLive = notificationDto.getTimeToLive();
        this.createdTime = notificationDto.getCreatedTime();
        this.featureArea = notificationDto.getFeatureArea();
        this.applicationUserId = notificationDto.getUserId();
    }

}
