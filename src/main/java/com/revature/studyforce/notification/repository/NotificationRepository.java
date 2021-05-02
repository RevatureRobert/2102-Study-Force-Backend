package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findByApplicationUserId(@RequestParam("id") Integer id, Pageable pageable);
    void deleteByApplicationUserId(Integer id);
}
