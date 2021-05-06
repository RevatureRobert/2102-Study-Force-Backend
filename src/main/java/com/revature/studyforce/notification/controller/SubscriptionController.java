package com.revature.studyforce.notification.controller;

import com.revature.studyforce.notification.dto.FlashcardSubscriptionDTO;
import com.revature.studyforce.notification.dto.StacktraceSubscriptionDTO;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.service.FlashcardSubscriptionService;
import com.revature.studyforce.notification.service.StacktraceSubscriptionService;
import com.revature.studyforce.notification.service.SubscriptionService;
import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Subscription Controller for
 *  Subscriptions {@link SubscriptionService}
 *  FlashcardSubscriptions {@link FlashcardSubscriptionService}
 *  StacktraceSubscriptions {@link StacktraceSubscriptionService}
 * @author Brandon Pinkerton
 */
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

    /**
     * Processes POST request on '/subscriptions' to create a user subscription {@link SubscriptionService#createSubscription(Subscription)}}
     * @param subscription The subscription information
     * @return The created subscription
     */
    //TODO: create a subscription DTO
    @PostMapping
    public @ResponseBody Subscription createSubscription(@RequestBody Subscription subscription){
        return subscriptionService.createSubscription(subscription);
    }

    /**
     * GET mapping for '/getSubscriptionByUserId in {@link SubscriptionService#getSubscriptionByUserId(Integer)}
     * @param id The user id of the user to find the subscription for the user
     * @return The users subscription
     */
    @GetMapping("/{user-id}")
    public @ResponseBody Subscription getUserSubscription(@PathVariable("user-id") String id){
        return subscriptionService.getSubscriptionByUserId(Integer.parseInt(id));
    }

    /**
     * DELETE mapping for '/deleteSubscriptionByUserId in {@link SubscriptionService#deleteSubscriptionByUserId(Integer)}
     * @param id The user id of the user to delete the subscription for the user
     * @return The users subscription that was deleted
     */
    @DeleteMapping("/{user-id}")
    public @ResponseBody Subscription deleteUserSubscription(@PathVariable("user-id") String id){
        return subscriptionService.deleteSubscriptionByUserId(Integer.parseInt(id));
    }

    /**
     * Processes POST request on '/subscriptions/flashcards' to create a flashcard subscription {@link FlashcardSubscriptionService#createFlashcardSubscription(Integer, Integer)}
     * @param subscriptionRequest A data transfer object containing the flashcard subscription information
     * @return The created subscription
     */
    @PostMapping("/flashcards")
    public @ResponseBody
    FlashcardSubscription createFlashcardSubscription(@RequestBody FlashcardSubscriptionDTO subscriptionRequest){
        return flashcardSubscriptionService.createFlashcardSubscription(
                subscriptionRequest.getFlashcardId(),
                subscriptionRequest.getUserId());
    }

    /**
     * GET mapping for '/getFlashcardSubscriptionsByFlashcardAndUserId in {@link FlashcardSubscriptionService#getFlashcardSubscriptionByFlashcardAndUserId(Integer, Integer)}
     * @param flashcardId The flashcard id of the flashcard to find the flashcard
     * @param userId The user id of the user to find the subscription for the user
     * @return The users flashcard subscription
     */
    @GetMapping("/flashcards")
    public @ResponseBody
    FlashcardSubscription getFlashcardSubscription(@RequestParam("flashcard-id") String flashcardId,
                                                   @RequestParam("user-id") String userId){
        return flashcardSubscriptionService.getFlashcardSubscriptionByFlashcardAndUserId(
                Integer.parseInt(flashcardId),
                Integer.parseInt(userId)
        );
    }

    /**
     * DELETE mapping for '/deleteFlashcardSubscription in {@link FlashcardSubscriptionService#deleteFlashcardSubscription(Integer, Integer)}
     * @param subscriptionRequest The DTO with the flashcardId and the userId
     * @return The users flashcard subscription that was deleted
     */
    @DeleteMapping("/flashcards")
    public @ResponseBody
    FlashcardSubscription deleteFlashcardSubscription(@RequestBody FlashcardSubscriptionDTO subscriptionRequest){
        return flashcardSubscriptionService.deleteFlashcardSubscription(
                subscriptionRequest.getFlashcardId(),
                subscriptionRequest.getUserId()
        );
    }

    /**
     * Processes POST request on '/subscriptions/stacktraces' to create a stacktrace subscription {@link StacktraceSubscriptionService#createStacktraceSubscription(Integer, Integer)}
     * @param subscriptionRequest A data transfer object containing the stacktrace subscription information
     * @return The created subscription
     */
    @PostMapping("/stacktraces")
    public @ResponseBody
    StacktraceSubscription createStacktraceSubscription(@RequestBody StacktraceSubscriptionDTO subscriptionRequest){
        return stacktraceSubscriptionService.createStacktraceSubscription(
                subscriptionRequest.getStacktraceId(),
                subscriptionRequest.getUserId());
    }

    /**
     * GET mapping for '/getStacktraceSubscriptionsByStacktraceAndUserId in {@link StacktraceSubscriptionService#getStacktraceSubscriptionByStacktraceAndUserId(Integer, Integer)}
     * @param stacktraceId The stacktrace id of the stacktrace to find the stacktrace
     * @param userId The user id of the user to find the subscription for the user
     * @return The users stacktrace subscription
     */
    @GetMapping("/stacktraces")
    public @ResponseBody
    StacktraceSubscription getStacktraceSubscription(@RequestParam("stacktrace-id") String stacktraceId,
                                                     @RequestParam("user-id") String userId){
        return stacktraceSubscriptionService.getStacktraceSubscriptionByStacktraceAndUserId(
                Integer.parseInt(stacktraceId),
                Integer.parseInt(userId)
        );
    }

    /**
     * DELETE mapping for '/deleteStacktraceSubscription in {@link StacktraceSubscriptionService#deleteStacktraceSubscription(Integer, Integer)}
     * @param subscriptionRequest The DTO with the stacktraceId and the userId
     * @return The users stacktrace subscription that was deleted
     */
    @DeleteMapping("/stacktraces")
    public @ResponseBody
    StacktraceSubscription deleteStacktraceSubscription(@RequestBody StacktraceSubscriptionDTO subscriptionRequest){
        return stacktraceSubscriptionService.deleteStacktraceSubscription(
                subscriptionRequest.getStacktraceId(),
                subscriptionRequest.getUserId()
        );
    }

}
