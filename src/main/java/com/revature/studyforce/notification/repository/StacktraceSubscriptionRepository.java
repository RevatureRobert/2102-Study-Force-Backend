package com.revature.studyforce.notification.repository;

import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StacktraceSubscriptionRepository extends JpaRepository<StacktraceSubscription, Integer> {
    StacktraceSubscription findAllByStacktraceSubscriptionId(StacktraceSubscriptionId stacktraceSubscriptionId);
    List<StacktraceSubscription> findAllByStacktrace_StacktraceId(Integer stacktraceId);
    List<StacktraceSubscription> findAllBySubscription_User_UserId(Integer userId);
}
