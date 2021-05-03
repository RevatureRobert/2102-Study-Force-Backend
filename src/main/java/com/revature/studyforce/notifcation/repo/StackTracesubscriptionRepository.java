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
    //find all the users that have a subscription
    @Query(value = "select * from STACK_TRACE_SUBSCRIPTION where stack_trace = :id " ,nativeQuery = true )
    List<StackTraceSubscription> findByStacktraceId(@RequestParam int id);

    // find all the subscriptions for a user
    // Do i want to find all stacktraces here or can i just find their ids and then send back a HATOES Link to them?
    @Query(value = "select stack_trace from STACK_TRACE_SUBSCRIPTION , SUBSCRIPTIONS where subscription_id = id and user_user_id = :id" , nativeQuery = true)
    List<Integer> findByUserId(@RequestParam int id);

    @Modifying
    @Transactional
    @Query(value = "Delete From STACK_TRACE_SUBSCRIPTION where stack_trace_id = :id" , nativeQuery = true)
    void removeBySubscription(@RequestParam int id);

}
