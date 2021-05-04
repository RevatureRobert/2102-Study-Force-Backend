package com.revature.studyforce.notifcation.service;

import com.revature.studyforce.notifcation.model.Subscription;
import com.revature.studyforce.notifcation.repo.SubscriptionRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository REPO ;

    private final UserRepository USERREPO;

    public SubscriptionService(SubscriptionRepository repo, UserRepository userrepo) {
        REPO = repo;
        USERREPO = userrepo;
    }

    // Add a new subscription
    public Subscription add(Subscription subscription ){
        return REPO.save(subscription);
    }

    //findAll
    public List<Subscription> findAll(){
        return REPO.findAll();
    }

    /**
     * This will only happen if the user uses a new browser
     * We will need to over write the existing users subscription details
     */
    //Overwrite an existing subscription


    /**
     * Not sure when this would used
     * @return
     */
    // Delete a subscription


    //find a user
    public Optional<User> findUser(int id){
        return USERREPO.findById(id);
    }
}
