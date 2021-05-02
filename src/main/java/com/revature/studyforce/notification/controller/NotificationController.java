package com.revature.studyforce.notification.controller;

import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * Notification Controller
 * author: Patrick
 */

@RestController
@RequestMapping(path="/notification", produces="application/json")
@CrossOrigin(origins="*")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(){
        List<Notification> notificationList = notificationService.findAll();
        if(notificationList != null){
            return ResponseEntity.ok(notificationList);
        }
        return ResponseEntity.notFound().build();
    }

    // If we want the user to specify certain pagination or a certain page then we have
    // to pass it in as a Path Variable and create the corresponding pageable object
    @GetMapping("/{id}")
    public ResponseEntity<Page<Notification>> getNotificationsByUserId(@PathVariable("id") Integer id, @RequestParam(name="page", defaultValue = "0") String page){
        Page<Notification> notificationPage = notificationService.findByUserId(id, Integer.parseInt(page));
        if(notificationPage != null){
            return ResponseEntity.ok(notificationPage);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification){
        if(notification != null){
            notificationService.save(notification);
            return new ResponseEntity<>(notification, HttpStatus.CREATED);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Notification> deleteAllNotificationsByUserId(@PathVariable("id") Integer id){
        try{
            notificationService.deleteByUserId(id);
            return ResponseEntity.noContent().build();
        }
        catch(EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
