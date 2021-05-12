package com.revature.studyforce.notification.service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushAsyncService;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.asynchttpclient.Response;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;
import com.revature.studyforce.notification.model.Subscription;


import java.security.Security;
import java.util.concurrent.CompletableFuture;

/**
 * @author Daniel Kopeloff
 *
 */
@Service
public class SendNotificationService {

    private static final String PUBLIC_KEY = "BEH36g-ez23QfnT8OIbnZJMmj892dDYa_LKyGz_wM2tyZbSt1YK4Jy1sRz1OyAeilAOBDrg-TnCBLFtWdVIApK8";
    private static final String PRIVATE_KEY = "VzVcwmu7b3eu55MUQD_h2pna4DAI702aluxbJnjOcxs";
    private String SUBJECT = "Study Force";




    /**
     * This Method will be how to the notification is sent. Just need to pass in a nl.martijndwars.webpush subscription
     * The subject identifier is used to tell the method what subject the notification is coming from
     * Key for subjectIdentifier
     * 1: Stacktrace
     * 2: FlashCard
     * 3: User
     * 4: Other
     * @param subscription
     * @param subjectIdentifier
     * @return Http Response code for the request 
     */
    public String send(Subscription subscription, int subjectIdentifier , String message) {
        String title;
        switch (subjectIdentifier){
            case 1 : {
                title = "Stacktrace";
                break;
            }
            case 2 : {
                title = "Flashcard";
                break;

            }
            case 3 : {
               title = "User";
                break;
            }
            case 4 : {
                title = "Other";
                break;
            }
            default:{
                title = "Not a valid Subject";
            }

        }
        Security.addProvider(new BouncyCastleProvider());

        nl.martijndwars.webpush.Subscription sub1 = new nl.martijndwars.webpush.Subscription(subscription.getEndpoint(),
                new nl.martijndwars.webpush.Subscription.Keys(subscription.getKey(),subscription.getAuth()));


        String PAYLOAD = "{\"notification\":{\"title\":\""+ title +"\",\"body\":\""+message+"\"}}";

        try {
            PushAsyncService pushService = new PushAsyncService(PUBLIC_KEY, PRIVATE_KEY, SUBJECT);
//            Subscription subscription = new Gson().fromJson(subscriptionJson, Subscription.class);
            Notification notification = new Notification(sub1, PAYLOAD);
            CompletableFuture<Response> httpResponse = pushService.send(notification);

            int statusCode = httpResponse.get().getStatusCode();

//            System.out.println(statusCode);
            return String.valueOf(statusCode);
        } catch (Exception e) {
            return ExceptionUtils.getStackTrace(e);
        }
    }

}
