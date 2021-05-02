package com.revature.studyforce.notifcation.model;


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
@Table(name = "stackTraceSubscription")
public class StackTraceSubscription {

  @EmbeddedId
    private StacktraceSubscriptionID stacktraceSubscriptionID;

  public StackTraceSubscription(int stackId, Subscription subId) {
    this.stacktraceSubscriptionID.setStackTrace(stackId);
    this.stacktraceSubscriptionID.setSubscription(subId);
  }
}
