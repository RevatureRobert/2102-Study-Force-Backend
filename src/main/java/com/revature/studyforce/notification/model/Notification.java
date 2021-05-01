package com.revature.studyforce.notification.model;

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
    Integer notificationId;

    @Column(name = "notification_message")
    private String notificationMessage;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "time_to_live")
    private Timestamp timeToLive;

    @Enumerated
    private FeatureArea featureArea;


    @Column(name = "application_user_id")
    private int applicationUserId;

}
