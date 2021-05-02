package com.revature.studyforce.notifcation.controller;

import com.revature.studyforce.notifcation.model.Subscription;
import com.revature.studyforce.notifcation.service.SubscriptionService;
import com.revature.studyforce.user.model.User;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This service will deal with the all the operations dealing with the subscriptions Table
 * Basically all the subscription destination
 */

@CrossOrigin(origins = {"http://localhost:4200" , "**"})
@RestController
public class PushController {


    private final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();

    private final SubscriptionService subscriptionService;

    public PushController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * This endpoint is used to retrieve the public key
     * @return
     */
    @CrossOrigin(origins = {"http://localhost:4200" , "**"})
    @GetMapping(path = "/publicSigningKey" , produces = "application/octet-stream" )
    public @ResponseBody byte[] publicSigningKey(){
        return "BEH36g-ez23QfnT8OIbnZJMmj892dDYa_LKyGz_wM2tyZbSt1YK4Jy1sRz1OyAeilAOBDrg-TnCBLFtWdVIApK8".getBytes(StandardCharsets.UTF_8);    }

    @CrossOrigin(origins = {"http://localhost:4200"})
    @PostMapping(path = "/subscribe")
    public @ResponseBody Subscription subscribe(@RequestBody Subscription subscription ){
        return this.subscriptionService.add(subscription );
    }

    @CrossOrigin(origins = {"http://localhost:4200"})
    @GetMapping(path = "/subscribe")
    public @ResponseBody List<Subscription> seeAllSubs(){
        return this.subscriptionService.findAll();
    }


    @CrossOrigin(origins = {"http://localhost:4200"})
    @GetMapping(path = "/users" ,  produces = "application/hal+json")
    public @ResponseBody
    Optional<User> findUser(@RequestParam int id){
        return this.subscriptionService.findUser(id);
    }


}
