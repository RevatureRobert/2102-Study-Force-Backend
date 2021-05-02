package com.revature.studyforce.notifcation.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This table will hold the user and their respective subscription details so we can send notification in the future
 */
@Entity
@Table(name = "UserSubscription")
@Data
@Builder
public class UserSubscription {

    @EmbeddedId
    private UserSubscriptionID userSubscriptionID;

}
