package com.revature.studyforce.user.dto;

import com.revature.studyforce.user.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for authority information
 * @author Daniel Bernier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorityDTO {
    int userId;
    Authority authority;
}
