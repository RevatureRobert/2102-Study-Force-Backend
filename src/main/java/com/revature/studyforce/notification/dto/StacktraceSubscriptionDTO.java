package com.revature.studyforce.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO model for a stacktrace subscription
 * @author Brandon Pinkerton
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StacktraceSubscriptionDTO {
    private Integer stacktraceId;
    private Integer userId;
}
