package com.revature.studyforce.notifcation.repo;


import com.revature.studyforce.notifcation.model.StackTrace;
import com.revature.studyforce.notifcation.model.StackTraceSubscription;
import com.revature.studyforce.notifcation.model.StacktraceSubscriptionID;
import com.revature.studyforce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface StackTracesubscriptionRepository extends JpaRepository<StackTraceSubscription, StacktraceSubscriptionID> {
    //find all the subscriptions for a user
    @Query(value = "Select * from STACK_TRACE_SUBSCRIPTION where stack_trace_id = :id" ,nativeQuery = true )
    List<StackTraceSubscription> findBystacktraceId(@RequestParam int id);

    @Query(value = "Select * from STACK_TRACE_SUBSCRIPTION where user_id = :id" , nativeQuery = true)
    List<StackTraceSubscription> findByUserId(@RequestParam int id);

    @Modifying
    @Transactional
    @Query(value = "Delete From STACK_TRACE_SUBSCRIPTION where stack_trace_id = :id" , nativeQuery = true)
    void removeBySubscrption(@RequestParam int id);

}
