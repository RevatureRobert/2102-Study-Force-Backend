package com.revature.studyforce.notification.model;


import com.revature.studyforce.stacktrace.model.Stacktrace;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "stacktrace_subscriptions")
public class StacktraceSubscription {

  @EmbeddedId
    private StacktraceSubscriptionId stacktraceSubscriptionId;

  public StacktraceSubscription(Stacktrace stacktrace, Subscription subscription) {
    this.stacktraceSubscriptionId.setStacktrace(stacktrace);
    this.stacktraceSubscriptionId.setSubscription(subscription);
  }
}
