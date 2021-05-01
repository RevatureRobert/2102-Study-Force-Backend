package com.revature.studyforce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for active status information
 * @author Daniel Bernier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIsActiveDTO {
    private int userId;
    private boolean isActive;
}
