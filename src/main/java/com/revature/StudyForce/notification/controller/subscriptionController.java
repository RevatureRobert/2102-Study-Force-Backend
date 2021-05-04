package com.revature.studyforce.notification.controller;

import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.dto.FlashcardSubscriptionDTO;
import com.revature.studyforce.notification.service.FlashcardSubscriptionService;
import com.revature.studyforce.notification.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private SubscriptionService subscriptionService;
    private FlashcardSubscriptionService flashcardSubscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService,
                                  FlashcardSubscriptionService flashcardSubscriptionService){
        this.subscriptionService = subscriptionService;
        this.flashcardSubscriptionService = flashcardSubscriptionService;
    }

    @PostMapping
    public @ResponseBody Subscription createSubscription(@RequestBody Subscription subscription){
        return subscriptionService.createSubscription(subscription);
    }

    @GetMapping("/{user-id}")
    public @ResponseBody Subscription getUserSubscription(@PathVariable("user-id") String id){
        return subscriptionService.getSubscriptionByUserId(Integer.parseInt(id));
    }

    @DeleteMapping("/{user-id}")
    public @ResponseBody Subscription deleteUserSubscription(@PathVariable("user-id") String id){
        return subscriptionService.deleteSubscriptionByUserId(Integer.parseInt(id));
    }

    @PostMapping("/flashcards")
    public @ResponseBody
    FlashcardSubscription createFlashcardSubscription(@RequestBody FlashcardSubscriptionDTO subscriptionRequest){
        return flashcardSubscriptionService.createFlashcardSubscription(
                subscriptionRequest.getFlashcardId(),
                subscriptionRequest.getUserId());
    }

    @GetMapping("/flashcards")
    public @ResponseBody
    FlashcardSubscription getFlashcardSubscription(@RequestBody FlashcardSubscriptionDTO subscriptionRequest){
        return flashcardSubscriptionService.getFlashcardSubscriptionByFlashcardAndUserId(
                subscriptionRequest.getFlashcardId(),
                subscriptionRequest.getUserId()
        );
    }

    @DeleteMapping("/flashcards")
    public @ResponseBody
    FlashcardSubscription deleteFlashcardSubscription(@RequestBody FlashcardSubscriptionDTO subscriptionRequest){
        return flashcardSubscriptionService.deleteFlashcardSubscription(
                subscriptionRequest.getFlashcardId(),
                subscriptionRequest.getUserId()
        );
    }

}
