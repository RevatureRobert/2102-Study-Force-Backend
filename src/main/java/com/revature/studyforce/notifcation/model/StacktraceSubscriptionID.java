package com.revature.studyforce.notifcation.model;

import com.revature.studyforce.user.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.security.auth.Subject;
import java.io.Serializable;

/**
 * This class is used to create a composite key for the stacktrace subscription table
 */
@Embeddable
@NoArgsConstructor
@Data
public class StacktraceSubscriptionID  implements Serializable {


    @OneToOne
    private Subscription subscription;

    private int stackTrace;

}
