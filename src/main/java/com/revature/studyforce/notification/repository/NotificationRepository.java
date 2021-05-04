package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "notifications", path = "notifications")
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findByApplicationUserId(@RequestParam("id") Integer id, Pageable pageable);

    Page<Notification> deleteByNotificationId(@RequestParam("notificationId") Integer id, Pageable pageable);

    Page<Notification> findByIsReadAndTimeToLiveBetween(@RequestParam("isRead") Boolean isRead, @RequestParam("timeToLive") @DateTimeFormat(pattern = "yyy-MM-dd") Date dateCreated, @RequestParam("timeToLive") @DateTimeFormat(pattern = "yyy-MM-dd") Date expirationDate, Pageable pageable);
}
