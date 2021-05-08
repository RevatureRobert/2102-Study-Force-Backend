package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

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
     * Grab a page of {@link Notification Notifications}
     * @param page page represents the page number that we want to return
     * @param offset offset is the amount of elements we want on our page
     * @return Returns a page of notifications
     */
    public Page<NotificationDto> findAll(Integer page, Integer offset){
        Page<Notification> notificationPage = notificationRepository.findAll(PageRequest.of(page, offset));
        try{
            return notificationPage.map(Objects.requireNonNull(NotificationDto.convertToDto()));
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Find all {@link Notification Notifications} that belong to a particular user
     * and return a page of those notifications
     * @param userId We use the userId in order to find and get all of the notifications corresponding to this user
     * @param page The page parameter determines what page number we are returning
     * The default size of the page is 5
     * @return Returns a Page of Notifications
     */
    public Page<NotificationDto> findByUserId(Integer userId, Integer page){
        // We can change the page request parameters later
        Page<Notification> notificationPage = notificationRepository.findByUserId(userId, PageRequest.of(page, 5, Sort.by("isRead").descending().and(Sort.by("createdTime").descending())));
        try{
            return notificationPage.map(Objects.requireNonNull(NotificationDto.convertToDto()));
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Post a {@link Notification}
     * @param notificationDto The {@link NotificationDto} parameter represents the notification to be stored
     * @return Returns the notification that was stored
     */
    public NotificationDto save(NotificationDto notificationDto){
        Notification notification = notificationRepository.save(NotificationDto.convertFromDto().apply(notificationDto));
        return NotificationDto.convertToDto().apply(notification);
    }

    /***
     * Update an existing {@link Notification},
     * If the notification does not exist then nothing is performed
     * @param notificationDto The {@link NotificationDto} parameter represents the updated notification
     */
    public NotificationDto update(NotificationDto notificationDto){
        Optional<Notification> checkNotification = notificationRepository.findById(notificationDto.getId());
        if(checkNotification.isPresent()){
           return NotificationDto.convertToDto().apply(notificationRepository.save(NotificationDto.convertFromDto().apply(notificationDto)));
        }
        return null;
    }

    /***
     * Delete a {@link Notification}
     * @param notificationDto The {@link NotificationDto notificationDto} parameter represents the notification to be deleted
     */
    public void delete(NotificationDto notificationDto){
        notificationRepository.delete(NotificationDto.convertFromDto().apply(notificationDto));
    }

    /***
     * Delete a {@link Notification}
     * @param id The parameter id is the id of the Notification we want to delete
     */
    public void deleteByNotificationId(Integer id){
        notificationRepository.deleteById(id);
    }

    /***
     * Delete all {@link Notification notifications} that belong to a particular user
     * @param userId The userId parameter is used to find all notifications that belong to a particular user
     */
    public void deleteByUserId(Integer userId){
        notificationRepository.deleteByUserId(userId);
    }

    /***
     * Find all {@link Notification Notifications} that belong to a particular user
     * and return a page of those notifications
     * @param userId We use the userId in order to find and get all of the notifications corresponding to this user
     * @param page The page parameter determines what page number we are returning
     * The default size of the page is 5
     * @return Returns a Page of Notifications
     */
    public Page<NotificationDto> findAllByUserId(Integer userId, Integer page){
        // We can change the page request parameters later
        Page<Notification> notificationPage = notificationRepository.findAllByUserId(userId, PageRequest.of(page, 1000, Sort.by("isRead").descending().and(Sort.by("createdTime").descending())));
        try{
            return notificationPage.map(Objects.requireNonNull(NotificationDto.convertToDto()));
        }
        catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
