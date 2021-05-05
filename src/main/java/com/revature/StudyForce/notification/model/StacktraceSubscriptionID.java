package com.revature.studyforce.notification.model;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * This class is used to create a composite key for the stacktrace subscription table
 */
@Embeddable
@NoArgsConstructor
@Data
public class StacktraceSubscriptionId implements Serializable {

    private Integer subscription;
    private Integer stacktrace;

    public StacktraceSubscriptionId(Integer stacktrace, Integer subscription){
        this.stacktrace = stacktrace;
        this.subscription = subscription;
    }

}
