package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * author: Patrick
 */
@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    /***
     * Grab and return the notification based on the id passed
     * @param id
     * The id parameter represents the id of the notification in the database
     * @return
     * Method returns the notification retrieved from the database
     * or it returns null if the notification was not found
     */
    public Notification findById(Integer id){
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }

    /***
     * Grab all notifications from the database and return a list of them
     * @return
     * Returns a List of all the notifications in the database
     */
    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

    /***
     * Find all notifications that belong to a particular user
     * and return a page of those notifications
     * @param userId
     * We use the userId in order to find and get all of the notifications that correspond to this user
     * @param page
     * The page parameter determines what page number we are returning
     * The default size of the page is always 5 notifications
     * @return
     * Method returns a Page of Notifications
     */
    public Page<Notification> findByUserId(Integer userId, Integer page){
        // We can change the page request parameters later
        return notificationRepository.findByApplicationUserId(userId, PageRequest.of(page, 5, Sort.by("notificationId").descending()));
    }

    /***
     * Insert a notification into the database
     * @param notification
     * Method takes in a notification that is saved in the database
     */
    public void save(Notification notification){
        notificationRepository.save(notification);
    }

    /***
     * Update an existing notification
     * @param notification
     * Method takes in a notification that updates an existing notification
     * If the notification does not exist
     * then we do not enter the notification into the database
     */
    public void update(Notification notification){
        Optional<Notification> checkNotification = notificationRepository.findById(notification.getNotificationId());
        if(checkNotification.isPresent()){
            notificationRepository.save(notification);
        }
    }

    /***
     * Delete a notification
     * @param notification
     * notification is the parameter to be deleted from our database
     */
    public void delete(Notification notification){
        notificationRepository.delete(notification);
    }

    /***
     * Delete a notification based on the id passed
     * @param id
     * id is the id of the notification we want to delete from the database
     */
    public void deleteById(Integer id){
        notificationRepository.deleteById(id);
    }

    /***
     * Delete all notifications that belong to a particular user
     * @param userId
     * userId is the parameter used to find all notifications that belong
     * to that particular user
     */
    public void deleteByUserId(Integer userId){ notificationRepository.deleteByApplicationUserId(userId); }




}
