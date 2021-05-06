package com.revature.studyforce.notification.Aspect;


import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.service.FlashcardSubscriptionService;
import com.revature.studyforce.notification.service.SendNotificationService;
import com.revature.studyforce.notification.service.StacktraceSubscriptionService;
import com.revature.studyforce.notification.service.SubscriptionService;
import com.revature.studyforce.stacktrace.model.Solution;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Component
public class AOP {

    private final SendNotificationService sendNotificationService;
    private final StacktraceSubscriptionService stracktraceSubscriptionService;
    private final SubscriptionService subscriptionService;
    private final FlashcardSubscriptionService flashcardSubscriptionService;

    public AOP(SendNotificationService sendNotificationService, StacktraceSubscriptionService stracktraceSubscriptionService,
               SubscriptionService subscriptionService,
               FlashcardSubscriptionService flashcardSubscriptionService) {
        this.sendNotificationService = sendNotificationService;
        this.stracktraceSubscriptionService = stracktraceSubscriptionService;
        this.subscriptionService = subscriptionService;
        this.flashcardSubscriptionService = flashcardSubscriptionService;
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
        System.out.println("Hitting");
        this.sendNotificationService.send(subscription , 4);
    }


    @Pointcut("execution (* com.revature.studyforce.stacktrace.repository.Soultion.save(..)")
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

           Subscription temp = this.subscriptionService.getSubscriptionById(stacktracesub.getStacktraceSubscriptionId().getSubscription()).get();
           this.sendNotificationService.send(temp , 1);
       }
    }


    @Pointcut("execution (* com.revature.studyforce.flashcard.repository.AnswerRepository.save(..))")
    public void AnswerPostPointCut(){}

    /**
     * After a new answer is posted we will then have to find all the subscriptions tied to that
     * flashcard and send out a notification that a new answer is posted
     * @param jp
     * @param anwser
     */
    @AfterReturning(value = "AnswerPostPointCut()", returning = "answer")
    public void solutionUpdateSendNotifications(JoinPoint jp , Answer anwser){
        List<FlashcardSubscription> subs = this.flashcardSubscriptionService.getAllSubscribersByFlashcardId(anwser.getFlashcard().getId());
        for(FlashcardSubscription flashcardsub : subs){

            Subscription temp = this.subscriptionService.getSubscriptionById(flashcardsub.getFlashcardSubscriptionId().getSubscription()).get();
            this.sendNotificationService.send(temp , 2);
        }
    }


    @Pointcut("execution (* com.revature.studyforce.notification.repository.StacktraceSubscriptionRepository.save(..))")
    public void StackTraceSubscriptionPostPointCut(){}

    /**
     * After a user subscribes to a stacktrace they will get a notification
     * @param jp
     * @param stacktraceSubscription
     * @return status code if the notification went through
     */
    @AfterReturning(value = "AnswerPostPointCut()", returning = "stacktracesubscription")
    public void StackTraceSubscriptionPostPointCut(JoinPoint jp , StacktraceSubscription stacktraceSubscription){
        this.sendNotificationService.send(stacktraceSubscription.getSubscription() , 1);
    }

    @Pointcut("execution (* com.revature.studyforce.notification.repository.flashcardSubscriptionRepository.save(..))")
    public void FlashcardSubscriptionPostPointCut(){}

    /**
     * After a user subscribes to a flashcard they will get a notification
     * @param jp
     * @param stacktraceSubscription
     * @return status code if the notification went through
     */
    @AfterReturning(value = "FlashcardSubscriptionPostPointCut()", returning = "flashcardsubscription")
    public void StackTraceSubscriptionPostPointCut(JoinPoint jp , FlashcardSubscription stacktraceSubscription){
        this.sendNotificationService.send(stacktraceSubscription.getSubscription() , 2);
    }


}
