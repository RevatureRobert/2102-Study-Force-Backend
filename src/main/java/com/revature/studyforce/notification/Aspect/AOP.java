package com.revature.studyforce.notification.Aspect;


import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.service.*;
import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.user.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Aspect
@Component
public class AOP {

    private final SendNotificationService sendNotificationService;
    private final StacktraceSubscriptionService stracktraceSubscriptionService;
    private final SubscriptionService subscriptionService;
    private final FlashcardSubscriptionService flashcardSubscriptionService;
    private final NotificationService notificationService;

    public AOP(SendNotificationService sendNotificationService, StacktraceSubscriptionService stracktraceSubscriptionService,
               SubscriptionService subscriptionService,
               FlashcardSubscriptionService flashcardSubscriptionService, NotificationService notificationService) {
        this.sendNotificationService = sendNotificationService;
        this.stracktraceSubscriptionService = stracktraceSubscriptionService;
        this.subscriptionService = subscriptionService;
        this.flashcardSubscriptionService = flashcardSubscriptionService;
        this.notificationService = notificationService;
    }

    /**
     * Creating a pointCut to when a new subscription is posted
     * The effect this is going to happen is that after a subscription is posted to the DB by either
     * updating an existing subscription or creating a new we will then send out a notification to that
     * User with that subscription
     */
    @Pointcut("execution (* com.revature.studyforce.notification.repository.SubscriptionRepository.save(..))")
    public void SubscriptionPostPointCut(){}

    @AfterReturning(value = "SubscriptionPostPointCut()", returning = "subscription")
    public void confirmationSubscription(JoinPoint jp , Subscription subscription){
        /**
         * This will check if the notification was successfully sent
         * The System.currentTimeMillis should created  TTL that is three days after the creation
         */
        if(this.sendNotificationService.send(subscription , 4 , "Welcome to Study Force").equals("201")){

            Timestamp timestamp = new Timestamp(new Date().getTime());
            Timestamp timestamp1 = new Timestamp(timestamp.getTime() + ((1000L * 60 * 60 *24 *3)));

            NotificationDto notificationDto = new NotificationDto(0,"Welcome to Study Force",false , timestamp ,
                    timestamp1  , FeatureArea.CONFIRMATION  , subscription.getUser().getUserId() , 0 );
            this.notificationService.save(notificationDto);
        };

    }


    @Pointcut("execution (* com.revature.studyforce.stacktrace.repository.SolutionRepository.save(..))")
    public void SolutionPostPointCut(){}


    /**
     * After a new solution is posted we will then have to find all the subscriptions tied to that
     * stacktrace and send out a notification that a new solution is posted
     * @param jp
     * @param solution
     */
    @AfterReturning(value = "SolutionPostPointCut()", returning = "solution")
    public void solutionUpdateSendNotifications(JoinPoint jp , Solution solution){
       List<StacktraceSubscription> subs = this.stracktraceSubscriptionService.getAllSubscribersByStacktraceId(solution.getStackTraceId().getStacktraceId());
       for(StacktraceSubscription stacktracesub : subs){
           User temp = stacktracesub.getSubscription().getUser();
           if(temp.isActive() && temp.isSubscribedStacktrace()){
               subs.remove(stacktracesub);
           }

       }
       this.massSendStackraceotifications(subs , "New Answer from " + solution.getStackTraceId().getBody());
    }


    @Pointcut("execution (* com.revature.studyforce.flashcard.repository.AnswerRepository.save(..))")
    public void AnswerPostPointCut(){}

    /**
     * After a new answer is posted we will then have to find all the subscriptions tied to that
     * flashcard and send out a notification that a new answer is posted
     * @param jp
     * @param answer
     */
    @AfterReturning(value = "AnswerPostPointCut()", returning = "answer")
    public void solutionUpdateSendNotifications(JoinPoint jp , Answer answer){
        List<FlashcardSubscription> subs = this.flashcardSubscriptionService.getAllSubscribersByFlashcardId(answer.getFlashcard().getId());
        for(FlashcardSubscription flashcardsub : subs){
                User temp = flashcardsub.getSubscription().getUser();
                if(temp.isActive() && temp.isSubscribedFlashcard()){
                    subs.remove(flashcardsub);
                }

        }
        this.massSendFlashCardNotifications(subs , "New Answer from " + answer.getFlashcard().getQuestion());
    }


    @Pointcut("execution (* com.revature.studyforce.notification.repository.StacktraceSubscriptionRepository.save(..))")
    public void StackTraceSubscriptionPostPointCut(){}

    /**
     * After a user subscribes to a stacktrace they will get a notification
     * @param jp
     * @param stacktraceSubscription
     * @return status code if the notification went through
     */
    @AfterReturning(value = "StackTraceSubscriptionPostPointCut()", returning = "stacktraceSubscription")
    public void StackTraceSubscriptionPostPointCut(JoinPoint jp , StacktraceSubscription stacktraceSubscription){
        if(this.sendNotificationService.send(stacktraceSubscription.getSubscription() , 1 , "You have subscribed").equals("201")){
            Timestamp timestamp = new Timestamp(new Date().getTime());
            Timestamp timestamp1 = new Timestamp(timestamp.getTime() + ((1000L * 60 * 60 *24 *3 )));

            NotificationDto notificationDto = new NotificationDto(0,"\"You are now subscribed\"",false , timestamp ,
                    timestamp1  , FeatureArea.CONFIRMATION  , stacktraceSubscription.getSubscription().getUser().getUserId() , stacktraceSubscription.getStacktrace().getStacktraceId() );
            this.notificationService.save(notificationDto);
        }
    }

    @Pointcut("execution (* com.revature.studyforce.notification.repository.FlashcardSubscriptionRepository.save(..))")
    public void FlashcardSubscriptionPostPointCut(){}

    /**
     * After a user subscribes to a flashcard they will get a notification
     * @param jp
     * @param flashcardSubscription
     * @return status code if the notification went through
     */
    @AfterReturning(value = "FlashcardSubscriptionPostPointCut()", returning = "flashcardSubscription")
    public void StackTraceSubscriptionPostPointCut(JoinPoint jp , FlashcardSubscription flashcardSubscription){

        if(this.sendNotificationService.send(flashcardSubscription.getSubscription() , 2 ,"You have subscribed").equals("201")){
            Timestamp timestamp = new Timestamp(new Date().getTime());
            Timestamp timestamp1 = new Timestamp(timestamp.getTime() + ((1000L * 60 * 60 *24 *3)));

            NotificationDto notificationDto = new NotificationDto(0,"\"You are now subscribed\"",false , timestamp ,
                    timestamp1  , FeatureArea.CONFIRMATION  , flashcardSubscription.getSubscription().getUser().getUserId() , flashcardSubscription.getFlashcard().getId() );
            this.notificationService.save(notificationDto);
        }

    }

    @Pointcut("execution (* com.revature.studyforce.flashcard.repository.FlashcardRepository.save(..))")
    public void newFlashCard(){}

    /**
     * Will automatically subscribe a user to after they create a flashcard
     * Then since there is an aspect for the Adding of a subscription the user will be notified by that aspect
     * @param jp
     * @param flashcard
     */
    @AfterReturning(value = "newFlashCard()", returning = "flashcard")
    public void subscribingFlashcardCreator(JoinPoint jp , Flashcard flashcard){
       Subscription temp = this.subscriptionService.getSubscriptionByUserId(flashcard.getCreator().getUserId());
       this.flashcardSubscriptionService.createFlashcardSubscription(flashcard.getId(), flashcard.getCreator().getUserId());

    }

    @Pointcut("execution (* com.revature.studyforce.stacktrace.repository.StacktraceRepository.save(..))")
    public void newStackTrace(){}

    /**
     * Will automatically subscribe a user to after they create a stacktrace
     * @param jp
     * @param stacktrace
     */
    @AfterReturning(value = "newStackTrace()", returning = "stacktrace")
    public void StackTraceSubscriptionPostPointCut(JoinPoint jp , Stacktrace stacktrace){
        Subscription temp = this.subscriptionService.getSubscriptionByUserId(stacktrace.getUserId().getUserId());
        this.stracktraceSubscriptionService.createStacktraceSubscription(stacktrace.getStacktraceId(), stacktrace.getUserId().getUserId());

    }

    @Pointcut("execution (* com.revature.studyforce.stacktrace.repository.SolutionRepository.updateSolutionSelectedByAdminBySolutionId(..))")
    public void adminSubmitStacktrace(){}

    /**
     * A method that takes in a list of flashcard subscriptions and sends them out
     * @param subscriptions
     * @return
     */
    public boolean massSendFlashCardNotifications(List<FlashcardSubscription> subscriptions , String message){
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Timestamp timestamp1 = new Timestamp(timestamp.getTime() + ((1000L * 60 * 60 *24 * 3)));


        for (FlashcardSubscription flashcardsub: subscriptions) {
            if(this.sendNotificationService.send(flashcardsub.getSubscription() , 2 , message).equals("201")){
                NotificationDto notificationDto = new NotificationDto(0,message,false , timestamp ,
                        timestamp1  , FeatureArea.CONFIRMATION  , flashcardsub.getSubscription().getUser().getUserId() , flashcardsub.getFlashcard().getId() );
                this.notificationService.save(notificationDto);
            };
        }
        return true;
    }


    /**
     * A method that takes in a list of stacktrace subscriptions and sends them out
     * @param subscriptions
     * @return
     */
    public boolean massSendStackraceotifications(List<StacktraceSubscription> subscriptions ,String message ){
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Timestamp timestamp1 = new Timestamp(timestamp.getTime() + ((1000L * 60 * 60 *24 *3)));


        for (StacktraceSubscription stacktracesub: subscriptions) {
            if(this.sendNotificationService.send(stacktracesub.getSubscription() , 1 , message).equals("201")){
                NotificationDto notificationDto = new NotificationDto(0,message,false , timestamp ,
                        timestamp1  , FeatureArea.CONFIRMATION  , stacktracesub.getSubscription().getUser().getUserId() , stacktracesub.getStacktrace().getStacktraceId() );
                this.notificationService.save(notificationDto);
            };
        }
        return true;
    }


}
