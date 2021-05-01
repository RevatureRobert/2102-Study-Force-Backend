package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    public Page<Notification> findByUserId(Integer userId){
        // We can change the page request parameters later
        return notificationRepository.findByApplicationUserId(userId, PageRequest.of(1, 10));
    }

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
