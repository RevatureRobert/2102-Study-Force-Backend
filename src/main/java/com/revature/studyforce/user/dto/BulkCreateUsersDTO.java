package com.revature.studyforce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data transfer object for bulk user creation
 * @author Nick Wickham
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkCreateUsersDTO {

    private String email;
}
