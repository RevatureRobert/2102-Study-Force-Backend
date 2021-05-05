package com.revature.studyforce.notification.dto;

import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Integer id;
    private String message;
    private boolean read;
    private Timestamp timeToLive;
    private Timestamp createdTime;
    private FeatureArea featureArea;
    private Integer userId;

    public NotificationDto(Notification notification){
        this.id = notification.getNotificationId();
        this.message = notification.getNotificationMessage();
        this.read = notification.isRead();
        this.timeToLive = notification.getTimeToLive();
        this.createdTime = notification.getCreatedTime();
        this.featureArea = notification.getFeatureArea();
        this.userId = notification.getApplicationUserId();
    }
}
