package com.revature.studyforce.notification.dto;

import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.user.repository.UserRepository;
import com.revature.studyforce.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.function.Function;

/**
 * @author Ronald Lopez
 * @author Patrick Gonzalez
 */
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
    private Integer referenceId;

    public static Function<Notification, NotificationDto> convertToDto(){


        return notification -> {
            Assert.notNull(notification, "Notification is null");

            return new NotificationDto(
                    notification.getNotificationId(),
                    notification.getBody(),
                    notification.isRead(),
                    notification.getTimeToLive(),
                    notification.getCreatedTime(),
                    notification.getFeatureArea(),
                    notification.getUser().getUserId(),
                    notification.getReferenceId()
            );
        };

    }
//    public static Function<NotificationDto, Notification> convertFromDto(){
//
//        UserService userRepository = new UserService() {
//        };
//
//        return notificationDto -> {
//            Assert.notNull(notificationDto, "NotificationDTO is null");
//
//            return new Notification(
//                    notificationDto.getId(),
//                    notificationDto.getMessage(),
//                    notificationDto.isRead(),
//                    notificationDto.getTimeToLive(),
//                    notificationDto.getCreatedTime(),
//                    notificationDto.getFeatureArea(),
//                    notificationDto.getReferenceId(),
//                    userRepository.getUserById(notificationDto.getUserId())
//
//            );
//
//        };
//    }
}
