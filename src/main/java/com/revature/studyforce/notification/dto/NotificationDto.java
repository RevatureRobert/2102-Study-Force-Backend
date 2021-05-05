package com.revature.studyforce.notification.dto;

import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.function.Function;

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
        this.createdTime = notification.getCreatedTime();
        this.read = notification.isRead();
        this.timeToLive = notification.getTimeToLive();
        this.featureArea = notification.getFeatureArea();
        this.userId = notification.getApplicationUserId();
    }
    public static Function<Notification, NotificationDto> convertToDto(){
        try{
            return (Notification) -> new NotificationDto(
                    Notification.getNotificationId(),
                    Notification.getNotificationMessage(),
                    Notification.isRead(),
                    Notification.getTimeToLive(),
                    Notification.getCreatedTime(),
                    Notification.getFeatureArea(),
                    Notification.getApplicationUserId()
            );
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
    public static Function<NotificationDto, Notification> convertFromDto(){
        try{
            return (NotificationDto) -> new Notification(
                    NotificationDto.getId(),
                    NotificationDto.getMessage(),
                    NotificationDto.isRead(),
                    NotificationDto.getTimeToLive(),
                    NotificationDto.getCreatedTime(),
                    NotificationDto.getFeatureArea(),
                    NotificationDto.getUserId()
            );
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
