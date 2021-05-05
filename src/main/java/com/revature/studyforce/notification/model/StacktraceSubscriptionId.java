package com.revature.studyforce.notification.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * This class is used to create a composite key for the stacktrace subscription table
 * @author Brandon Pinkerton
 */
@Embeddable
@NoArgsConstructor
@Data
public class StacktraceSubscriptionId implements Serializable {

    /**
     * this is the composite key holding a reference to
     * the subscription Id and a StackTraceId
     */
    private Integer subscription;
    private Integer stacktrace;

    public StacktraceSubscriptionId(Integer stacktrace, Integer subscription){
        this.stacktrace = stacktrace;
        this.subscription = subscription;
    }

}
