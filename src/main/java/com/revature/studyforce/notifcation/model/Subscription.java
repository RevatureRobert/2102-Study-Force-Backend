package com.revature.studyforce.notifcation.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.studyforce.user.model.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * author https://github.com/ralscha/blog2019/commits?author=ralscha
 *
 * Github repo : https://github.com/ralscha/blog2019
 */
@Entity
@Table(name = "Subscriptions")
@NoArgsConstructor
@Getter
@Setter
public class Subscription {

    @Id
    private int id;

    //This will be the user
    @OneToOne
    private User user;

    private String endpoint;

    private Long expirationTime;

    private  String p256dh;

    private  String auth;

//    @JsonCreator
//    public Subscription(@JsonProperty("endpoint") String endpoint,
//                        @JsonProperty("expirationTime") Long expirationTime,
//                        @JsonProperty("keys") SubscriptionKeys keys) {
//        this.endpoint = endpoint;
//        this.expirationTime = expirationTime;
//        this.keys = keys;
//    }





}