package com.revature.studyforce.notification.Aspect;


import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.service.SendNotificationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AOP {

    private final SendNotificationService sendNotificationService;

    public AOP(SendNotificationService sendNotificationService) {
        this.sendNotificationService = sendNotificationService;
    }

    /**
     * Creating a pointCut to when a new subscription is posted
     * The effect this is going to happen is that after a subscription is posted to the DB by either
     * updating an existing subscription or creating a new we will then send out a notification to that
     * User with that subscription
     */
    @Pointcut("execution (* com.revature.studyforce.notification.repository.SubscriptionRepository.save(..))")
    public void SubscriptionPostPointCut(){}

    @AfterReturning(value = "SubscriptionPostPointCut", returning = "subscription")
    public void confirmationSubscription(JoinPoint jp , Subscription subscription){

        this.sendNotificationService.send(subscription , 4);


    }




}
