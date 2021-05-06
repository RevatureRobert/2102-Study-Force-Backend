package com.revature.studyforce.user.service;


import com.revature.studyforce.user.dto.CreateUpdateBatchDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.BatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * Service Layer Testing {@link BatchService }
 * @author Daniel Reyes
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class BatchServiceTest2 {

    @MockBean
    private BatchRepository batchRepository;

    @Autowired
    private BatchService batchService;

//    @Test
//    void Check1(){
//        Set<User> AdminList = new HashSet<>();
//        Set<User> StudentList = new HashSet<>();
//
//        Authority authority = Authority.ADMIN;
//        Authority user1 = Authority.USER;
//        Instant instant = Instant.now();
//        long epochMilli = Date.from(instant).getTime();
//        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
//        //User Admin = new User(0, "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
//        // student = new User(0 , "test@gmail.com", "Danny", true, true, true, user1, t2, t2);
//        //AdminList.add(Admin);
//        //StudentList.add(student);
//        Set<String> users = new HashSet<>();
//        Set<String> instructor = new HashSet<>();
//        users.add("dan@gmail.com");
//        instructor.add("d@gmail.com");
//        CreateUpdateBatchDTO batch = new CreateUpdateBatchDTO(0, "2102 Enterprise", AdminList, StudentList);
//        CreateUpdateBatchDTO batch2 = new CreateUpdateBatchDTO(0, "2103 Enterprise2", AdminList, StudentList);
//
//        System.out.println("Admin " + batch.getInstructors().toString());
//        System.out.println("Users" + batch.getUsers().toString());
//        Batch response = batchService.createBatch(batch);
//        Batch response2 = batchService.createBatch(batch2);
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(0, response.getBatchId());
//        Assertions.assertEquals("2102 Enterprise", response.getName());
//        Assertions.assertEquals(AdminList, response.getInstructors());
//        Assertions.assertEquals(StudentList, response.getUsers());
//
//        Assertions.assertNotNull(response2);
//        Assertions.assertEquals(0, response2.getBatchId());
//        Assertions.assertEquals("2103 Enterprise2", response2.getName());
//        Assertions.assertEquals(AdminList, response2.getInstructors());
//        Assertions.assertEquals(StudentList, response2.getUsers());
//    }
}
