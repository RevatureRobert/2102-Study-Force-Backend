package com.revature.studyforce.notifcation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@NoArgsConstructor
@Data
public class UserSubscriptionID implements Serializable {

    private int user;

    private int subscription;

}
