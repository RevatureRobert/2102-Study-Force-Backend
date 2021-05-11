package com.revature.studyforce.notification.model;


import com.revature.studyforce.user.model.User;
import lombok.*;

import javax.persistence.*;

/**
 * @author Brandon Pinkerton
 */
@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscription {

    /**
     * This class will hold all the information needed to send notifications to a user
     * the endpoint is the url that is used to send the notification
     * the p256dh is the encryption key
     * the auth is the public key
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    private String endpoint;

    private  String key;

    private  String auth;

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", user=" + user +
                ", endpoint='" + endpoint + '\'' +
                ", key='" + key + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}