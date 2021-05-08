package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;



@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findAllByUserId(@RequestParam("id") Integer id, Pageable pageable);
    Page<Notification> findByUserId(@RequestParam("id") Integer id, Pageable pageable);
    @Transactional
    @Modifying
    void deleteByUserId(Integer id);
}
