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
 * Service which processes various requests relating to {@link Notification Notifications}
 * <p>
 *     Relies heavily on {@link NotificationRepository} to process these requests
 * <p/>
 *
 * @author Patrick Gonzalez
 */
@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    /***
     * Grab and return the {@link Notification} based on the id passed
     * @param id The id parameter represents the unique id of the notification
     * @return Returns the notification retrieved
     * or it returns null if the notification was not found
     */
    public Notification findById(Integer id){
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }

    /***
     * Grab all {@link Notification Notifications} and return a list of them
     * @return Returns a List of all the notifications
     */
    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

    /***
     * Find all {@link Notification Notifications} that belong to a particular user
     * and return a page of those notifications
     * @param userId We use the userId in order to find and get all of the notifications corresponding to this user
     * @param page The page parameter determines what page number we are returning
     * The default size of the page is 5
     * @return Returns a Page of Notifications
     */
    public Page<Notification> findByUserId(Integer userId, Integer page){
        // We can change the page request parameters later
        return notificationRepository.findByApplicationUserId(userId, PageRequest.of(page, 5, Sort.by("notificationId").descending()));
    }

    /***
     * Post a {@link Notification}
     * @param notification The notification parameter represents the notification to be stored
     * @return Returns the notification that was stored
     */
    public Notification save(Notification notification){
        return notificationRepository.save(notification);
    }

    /***
     * Update an existing {@link Notification},
     * If the notification does not exist then nothing is performed
     * @param notification The notification parameter represents the updated notification
     */
    public void update(Notification notification){
        Optional<Notification> checkNotification = notificationRepository.findById(notification.getNotificationId());
        if(checkNotification.isPresent()){
            notificationRepository.save(notification);
        }
    }

    /***
     * Delete a {@link Notification}
     * @param notification The notification parameter represents the notification to be deleted
     */
    public void delete(Notification notification){
        notificationRepository.delete(notification);
    }

    /***
     * Delete a {@link Notification} based on the id passed
     * @param id The id parameter represents the unique id of the notification
     */
    public void deleteById(Integer id){
        notificationRepository.deleteById(id);
    }

    /***
     * Delete all {@link Notification notifications} that belong to a particular user
     * @param userId The userId parameter is used to find all notifications that belong to a particular user
     */
    public void deleteByUserId(Integer userId){ notificationRepository.deleteByApplicationUserId(userId); }




}
