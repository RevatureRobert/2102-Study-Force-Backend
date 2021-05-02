package com.revature.studyforce.notifcation.service;

import com.revature.studyforce.notifcation.model.StackTraceSubscription;
import com.revature.studyforce.notifcation.repo.StackTracesubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StackTraceService {
    private StackTracesubscriptionRepository REPO = null;

    public StackTraceService(StackTracesubscriptionRepository repo) {
        REPO = repo;
    }

    // Want to unsubscribe

    // want to display all users subscribed
    public  List<StackTraceSubscription> findSubs(){
        return REPO.findAll();
    }


    public List<Integer> findByUser(int id){
        return REPO.findByUserId(id);
    }

    public List<StackTraceSubscription> findByStackTrace(int id){
        return REPO.findByStacktraceId(id);
    }

    public StackTraceSubscription add(StackTraceSubscription stackTraceSubscription){
        return REPO.save(stackTraceSubscription);
    }

    public void remove(StackTraceSubscription stackTraceSubscription){
        REPO.delete(stackTraceSubscription);
    }

    public void removeAllFromStackTrace(int stackId){
        REPO.removeBySubscription(stackId);
    }


    //Delete all subscriptions ties to a stackTrace


}
