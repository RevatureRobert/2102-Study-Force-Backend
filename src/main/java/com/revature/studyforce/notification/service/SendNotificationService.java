package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.model.Subscription;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.gson.Gson;

import java.security.Security;

/**
 * Main code is from
 *
 */
public class SendNotificationService {

    private static final String PUBLIC_KEY = "BEH36g-ez23QfnT8OIbnZJMmj892dDYa_LKyGz_wM2tyZbSt1YK4Jy1sRz1OyAeilAOBDrg-TnCBLFtWdVIApK8";
    private static final String PRIVATE_KEY = "VzVcwmu7b3eu55MUQD_h2pna4DAI702aluxbJnjOcxs";
    private String SUBJECT = "";
    private String PAYLOAD = "{\"test\":\"hello world\",\"notification\":{\"title\":\"this is a test\",\"body\":\"body\"}}";


    /**
     * This Method will be how to the notification is sent. Just need to pass in a nl.martijndwars.webpush subscription
     * TODO:Maybe just pass in our subscription and I'll do the mapping on this end?
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
    public String send(Subscription subscription, int subjectIdentifier) {
        switch (subjectIdentifier){
            case 1 : {
                this.SUBJECT = "Stacktrace";
                break;
            }
            case 2 : {
                this.SUBJECT = "Flashcard";
                break;

            }
            case 3 : {
                this.SUBJECT = "User";
                break;
            }
            case 4 : {
                this.SUBJECT = "Other";
                break;
            }
            default:{
                this.SUBJECT = "Not a valid Subject";
            }

        }
        Security.addProvider(new BouncyCastleProvider());

        nl.martijndwars.webpush.Subscription sub1 = new nl.martijndwars.webpush.Subscription();



        try {
            PushService pushService = new PushService(PUBLIC_KEY, PRIVATE_KEY, SUBJECT);
//            Subscription subscription = new Gson().fromJson(subscriptionJson, Subscription.class);
            Notification notification = new Notification(subscription, PAYLOAD);
            HttpResponse httpResponse = pushService.send(notification);
            int statusCode = httpResponse.getStatusLine().getStatusCode();


            return String.valueOf(statusCode);
        } catch (Exception e) {
            return ExceptionUtils.getStackTrace(e);
        }
    }
}
