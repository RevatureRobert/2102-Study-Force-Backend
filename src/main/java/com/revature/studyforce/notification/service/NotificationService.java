package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public Notification findById(Integer id){
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

//    public List<Notification> findByUserId(Integer userId){
//        return notificationRepository.findByApplicationUserId(userId, );
//    }

    public void save(Notification notification){
        notificationRepository.save(notification);
    }

    public void update(Notification notification){
        notificationRepository.save(notification);
    }

    public void delete(Notification notification){
        notificationRepository.delete(notification);
    }

    public void deleteById(Integer id){
        notificationRepository.deleteById(id);
    }




}
