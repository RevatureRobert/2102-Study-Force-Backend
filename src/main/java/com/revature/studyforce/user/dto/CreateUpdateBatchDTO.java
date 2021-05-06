package com.revature.studyforce.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;


/**
 * Data transfer object for creating and updating batch
 * @author Daniel Reyes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateBatchDTO {
    private int batchId;
    private String name;
    private Set<String> instructors;
    private Set<String> users;

}
