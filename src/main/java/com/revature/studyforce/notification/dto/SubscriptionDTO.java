package com.revature.studyforce.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO model for a subscription
 * @author Brandon Pinkerton
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private Integer id;
    private Integer userId;
    private String endpoint;
    private String key;
    private String auth;
}
