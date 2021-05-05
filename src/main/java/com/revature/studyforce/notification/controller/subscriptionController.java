package com.revature.studyforce.notification.controller;

import com.revature.studyforce.notification.dto.StacktraceSubscriptionDTO;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.dto.FlashcardSubscriptionDTO;
import com.revature.studyforce.notification.service.FlashcardSubscriptionService;
import com.revature.studyforce.notification.service.StacktraceSubscriptionService;
import com.revature.studyforce.notification.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private SubscriptionService subscriptionService;
    private FlashcardSubscriptionService flashcardSubscriptionService;
    private StacktraceSubscriptionService stacktraceSubscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService,
                                  FlashcardSubscriptionService flashcardSubscriptionService,
                                  StacktraceSubscriptionService stacktraceSubscriptionService){
        this.subscriptionService = subscriptionService;
        this.flashcardSubscriptionService = flashcardSubscriptionService;
        this.stacktraceSubscriptionService = stacktraceSubscriptionService;
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
    FlashcardSubscription getFlashcardSubscription(@RequestParam("flashcard-id") String flashcardId,
                                                   @RequestParam("user-id") String userId){
        return flashcardSubscriptionService.getFlashcardSubscriptionByFlashcardAndUserId(
                Integer.parseInt(flashcardId),
                Integer.parseInt(userId)
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

    @PostMapping("/stacktraces")
    public @ResponseBody
    StacktraceSubscription createStacktraceSubscription(@RequestBody StacktraceSubscriptionDTO subscriptionRequest){
        return stacktraceSubscriptionService.createStacktraceSubscription(
                subscriptionRequest.getStacktraceId(),
                subscriptionRequest.getUserId());
    }

    @GetMapping("/stacktraces")
    public @ResponseBody
    StacktraceSubscription getStacktraceSubscription(@RequestParam("flashcard-id") String stacktraceId,
                                                   @RequestParam("user-id") String userId){
        return stacktraceSubscriptionService.getStacktraceSubscriptionByStacktraceAndUserId(
                Integer.parseInt(stacktraceId),
                Integer.parseInt(userId)
        );
    }

    @DeleteMapping("/stacktraces")
    public @ResponseBody
    StacktraceSubscription deleteStacktraceSubscription(@RequestBody StacktraceSubscriptionDTO subscriptionRequest){
        return stacktraceSubscriptionService.deleteStacktraceSubscription(
                subscriptionRequest.getStacktraceId(),
                subscriptionRequest.getUserId()
        );
    }

}
