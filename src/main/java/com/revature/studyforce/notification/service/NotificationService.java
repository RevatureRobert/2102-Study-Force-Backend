package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository){
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
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
     *             default page is 0
     * @param offset This sets the amount of elements in each page
     *               default offset is 10
     * @return Returns a Page of Notifications
     */
    public Page<NotificationDto> findByUserId(Integer userId, Integer page, Integer offset){
        // We can change the page request parameters later
        Page<Notification> notificationPage = notificationRepository.findByUser_UserId(userId, PageRequest.of(page, offset, Sort.by("isRead").ascending().and(Sort.by("createdTime").descending())));
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
        Notification notification = new Notification();

        Optional<User> user = userRepository.findById(notificationDto.getUserId());
        if(!user.isPresent()){
            return null;
        }
        notification.setUser(user.get());
        notification.setNotificationId(notificationDto.getId());
        notification.setBody(notificationDto.getMessage());
        notification.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
        notification.setTimeToLive(Timestamp.valueOf(notification.getCreatedTime().toLocalDateTime().plusDays(3)));
        notification.setRead(notificationDto.isRead());
        notification.setFeatureArea(notificationDto.getFeatureArea());
        return NotificationDto.convertToDto().apply(notificationRepository.save(notification));
    }

    /***
     * Update an existing {@link Notification},
     * If the notification does not exist then nothing is performed
     * @param notificationDto The {@link NotificationDto} parameter represents the updated notification
     */
    public NotificationDto update(NotificationDto notificationDto){
        Optional<Notification> checkNotification = notificationRepository.findById(notificationDto.getId());
        if(checkNotification.isPresent()){
            Notification notification = checkNotification.get();
            Optional<User> user = userRepository.findById(notificationDto.getUserId());
            if(!user.isPresent()){
                return null;
            }
            notification.setUser(user.get());
            notification.setBody(notificationDto.getMessage());
            notification.setTimeToLive(notificationDto.getTimeToLive());
            notification.setRead(notificationDto.isRead());
            notification.setFeatureArea(notificationDto.getFeatureArea());
            return NotificationDto.convertToDto().apply(notificationRepository.save(notification));
        }
        return null;
    }

    /***
     * Delete a {@link Notification}
     * @param notificationDto The {@link NotificationDto notificationDto} parameter represents the notification to be deleted
     */
//    public void delete(NotificationDto notificationDto){
//        Optional<User> user = userRepository.findById(notificationDto.getUserId());
//        if(!user.isPresent()){
//            return;
//        }
//    }

    /***
     * Delete a {@link Notification}
     * @param id The parameter id is the id of the Notification we want to delete
     * @return We return the deleted notification
     */
    public NotificationDto deleteByNotificationId(Integer id){
        Optional<Notification> checkNotification = notificationRepository.findById(id);
        if(checkNotification.isPresent()){
            notificationRepository.deleteById(id);
            return NotificationDto.convertToDto().apply(checkNotification.get());
        }
        return null;
    }

    /***
     * Delete all {@link Notification notifications} that belong to a particular user
     * @param userId The userId parameter is used to find all notifications that belong to a particular user
     * @return We return a List of the deleted notifications
     */
    public List<NotificationDto> deleteByUserId(Integer userId){
        List<Notification> notificationList = notificationRepository.deleteByUser_UserId(userId);
        return notificationList.stream().map(NotificationDto.convertToDto()).collect(Collectors.toList());
    }

}
