package com.revature.studyforce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for user subscription information
 * @author Daniel Bernier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionsDTO {
    private int userId;
    private boolean isSubscribedFlashcard;
    private boolean isSubscribedStacktrace;
}
