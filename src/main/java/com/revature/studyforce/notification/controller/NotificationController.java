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
 * author: Patrick
 */

@RestController
@RequestMapping(path="/notification", produces="application/json")
@CrossOrigin(origins="*")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /***
     *
     * @return
     *     Returns either the entire list of notifications from the database
     *     or if there are no notifications it returns a not found http response
     */
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(){
        List<Notification> notificationList = notificationService.findAll();
        if(notificationList != null){
            return ResponseEntity.ok(notificationList);
        }
        return ResponseEntity.notFound().build();
    }

    /***
     * @param userId
     * userId is the id of the user that we are grabbing the page for
     * @param page
     * default value of page is 0 so if no page number is entered
     * We start with the very first page.
     * @return
     *     Method returns a page of notifications
     *     The default size of the page is 5
     *     If there are no notifications then
     *     We return an Http Response of Not Found
     */
    // If we want the user to specify certain pagination or a certain page then we have
    // to pass it in as a Path Variable and create the corresponding pageable object
    @GetMapping("/{id}")
    public ResponseEntity<Page<Notification>> getNotificationsByUserId(@PathVariable("id") Integer userId, @RequestParam(name="page", defaultValue = "0") String page){
        Page<Notification> notificationPage = notificationService.findByUserId(userId, Integer.parseInt(page));
        if(notificationPage != null){
            return ResponseEntity.ok(notificationPage);
        }
        return ResponseEntity.notFound().build();
    }

    /***
     *
     * @param notification
     * A notification is passed into the controller from the client side
     * @return
     * We return the same notification if it is inserted successfully into the database
     * If the notification parameter is null then we return an Http Response of Unprocessable Entity
     */
    @PostMapping
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification){
        if(notification != null){
            notificationService.save(notification);
            return new ResponseEntity<>(notification, HttpStatus.CREATED);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    /***
     *
     * @param userId
     * The path should the userId for use
     * @return
     * Method returns either a No Content response if the notifications for a user are successfully deleted
     * or a Not Found Http Response if there weren't any notifications deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Notification> deleteAllNotificationsByUserId(@PathVariable("id") Integer userId){
        try{
            notificationService.deleteByUserId(userId);
            return ResponseEntity.noContent().build();
        }
        catch(EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
