package com.revature.studyforce.notification.model;


import com.revature.studyforce.user.model.User;
import lombok.*;

import javax.persistence.*;

/**
 * author https://github.com/ralscha/blog2019/commits?author=ralscha
 *
 * Github repo : https://github.com/ralscha/blog2019
 */
@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    private User user;

    private String endpoint;

    private  String p256dh;

    private  String auth;
}