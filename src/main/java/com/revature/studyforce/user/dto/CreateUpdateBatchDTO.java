package com.revature.studyforce.user.dto;

import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.function.Function;


/**
 * Data transfer object for creating and updating batch
 * @author Daniel Reyes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateBatchDTO {
    private int batchID;
    private String name;
    private Set<String> instructors;
    private Set<String> users;



//    public static Function<Batch, CreateUpdateBatchDTO> batchToDTO(){
//        return batch -> {
//            if(batch == null){
//                throw new IllegalArgumentException("Batch can not be null");
//            }
//            return new CreateUpdateBatchDTO(
//                    batch.getBatchId(),
//                    batch.getName(),
//                    batch.getInstructors(),
//                    batch.getUsers()
//            );
//        };
//    }
//
//
//    public static Function<CreateUpdateBatchDTO, Batch> dtoToBatch(){
//        Timestamp timestamp = Timestamp.from(Instant.now());
//        return createUpdateBatchDTO -> {
//            if (createUpdateBatchDTO == null){
//                throw new IllegalArgumentException("Parameter CreateUpdateBatchDTO can not be null");
//            }
//            return new Batch(
//                    createUpdateBatchDTO.getBatchID(),
//                    createUpdateBatchDTO.getName(),
//                    createUpdateBatchDTO.getInstructors(),
//                    createUpdateBatchDTO.getUsers(),
//                    timestamp
//            );
//        };
//    }




}
