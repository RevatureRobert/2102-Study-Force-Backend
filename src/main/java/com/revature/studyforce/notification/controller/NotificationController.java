package com.revature.studyforce.notification.controller;

import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * Controller for {@link Notification Notifications}
 * <p>
 *     Relies heavily on {@link NotificationService} to handle business logic
 * <p/>
 * @author Patrick Gonzalez
 */

@RestController
@RequestMapping(path="/notifications", produces="application/json")
@CrossOrigin(origins="*")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /***
     *  Retrieves a Page of notifications
     * @return Returns either a page of notifications from the database with the http ok status
     *     or if there are no notifications it returns a not found http response
     */

    @GetMapping
    public ResponseEntity<Page<Notification>> getAllNotifications(){
        Page<Notification> notificationPage = notificationService.findAll();
        if(notificationPage != null){
            return ResponseEntity.ok(notificationPage);
        }
        return ResponseEntity.notFound().build();
    }

    /***
     * Get a page of {@link Notification notifications} for a particular user based on the user id passed.
     * @param userId userId is the id of the user that we are grabbing the page for
     * @param page Default value of page is 0 so if no page number is passed as an argument, we start with the very first page.
     *             Also the default size of the page is 5.
     * @return Returns a page of notifications, if there are no notifications then we return an Http Response with status of Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Page<Notification>> getNotificationsByUserId(@PathVariable("id") Integer userId, @RequestParam(name="page", defaultValue = "0") String page){
        Page<Notification> notificationPage = notificationService.findByUserId(userId, Integer.parseInt(page));
        Page<NotificationDto> notificationDtoPage = notificationPage.map(new NotificationDto());
        if(notificationPage != null){
            return ResponseEntity.ok(notificationPage);
        }
        return ResponseEntity.notFound().build();
    }

    /***
     * Adds a {@link NotificationDto}
     * @param notificationDto The notificationDto parameter is the {@link Notification} to be stored
     * @return We return the same notification if it is inserted successfully.
     * If the notification parameter is null or we were unable to insert the notification
     * then we return an Http Response with status Unprocessable Entity
     */
    @PostMapping
    public ResponseEntity<NotificationDto> addNotification(@RequestBody NotificationDto notificationDto){
        Notification notification = new Notification(notificationDto);
        if(notification != null){
            notification = notificationService.save(notification);
            notificationDto = new NotificationDto(notification);
            return new ResponseEntity<>(notificationDto, HttpStatus.CREATED);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    /***
     * Delete all {@link Notification notifications} that belong to a particular user
     * @param userId userId is the id of the user
     * @return Returns an Http Response with Status No Content when the notifications are successfully deleted,
     *          Otherwise returns an Http Response with status Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationDto> deleteAllNotificationsByUserId(@PathVariable("id") Integer userId){
        try{
            notificationService.deleteByUserId(userId);
            return ResponseEntity.noContent().build();
        }
        catch(EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
