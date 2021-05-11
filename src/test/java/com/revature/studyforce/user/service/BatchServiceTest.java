package com.revature.studyforce.user.service;

import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.BatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * Service Layer Testing {@link BatchService }
 * @author Daniel Reyes
 * @author Daniel Bernier
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class BatchServiceTest {

    @MockBean
    private BatchRepository batchRepository;

    @Autowired
    private BatchService batchService;

    @Test
    void whenGetAllBatches_callBatchRepository_retrieveBatchPage(){

        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority user1 = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
        User student = new User(2 , "test@gmail.com", "Danny", true, true, true, user1, t2, t2);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, t2);
        BatchList.add(batch);
        Page<Batch> Batches = new PageImpl<>(BatchList);

        Mockito.when(batchRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(Batches);

        Page<Batch> response = batchService.getAllBatches(0, 5, "batchId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getContent().get(0).getName());
        Assertions.assertEquals(AdminList, response.getContent().get(0).getInstructors());
        Assertions.assertEquals(StudentList, response.getContent().get(0).getUsers());
        Assertions.assertEquals(t2, response.getContent().get(0).getCreationTime());
    }

    @Test
    void whenGetAllBatches_callBatchRepository_retrieveBatchPage_testingSortBy(){

        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority user1 = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);
        User student = new User(2 , "test@gmail.com", "Danny", true, true, true, user1, timestamp, timestamp);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, timestamp);
        BatchList.add(batch);
        Page<Batch> Batches = new PageImpl<>(BatchList);

        Mockito.when(batchRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(Batches);

        Page<Batch> response = batchService.getAllBatches(0, 5, "batchId", "asc");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getContent().get(0).getName());
        Assertions.assertEquals(AdminList, response.getContent().get(0).getInstructors());
        Assertions.assertEquals(StudentList, response.getContent().get(0).getUsers());
        Assertions.assertEquals(timestamp, response.getContent().get(0).getCreationTime());
    }

    @Test
    void whenGetBatchById_callBatchRepository_retrieveBatch(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority user1 = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
        User student = new User(2 , "test@gmail.com", "Danny", true, true, true, user1, t2, t2);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, t2);

        Mockito.when(batchRepository.findById(1)).thenReturn(Optional.of(batch));

        Batch response = batchService.getBatchById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getName());
        Assertions.assertEquals(AdminList, response.getInstructors());
        Assertions.assertEquals(StudentList, response.getUsers());
        Assertions.assertEquals(t2, response.getCreationTime());
    }

    @Test
    void whenGetBatchByName_callBatchRepository_retrieveBatch(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority user1 = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
        User student = new User(2 , "test@gmail.com", "Danny", true, true, true, user1, t2, t2);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, t2);

        Mockito.when(batchRepository.findByNameContainingIgnoreCase("2102")).thenReturn(batch);

        Batch response = batchService.getBatchByName("2102");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getName());
        Assertions.assertEquals(AdminList, response.getInstructors());
        Assertions.assertEquals(StudentList, response.getUsers());
        Assertions.assertEquals(t2, response.getCreationTime());
    }

    @Test
    void whenGetBatchByCreationTime_callBatchRepository_retrieveBatchPage(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority user1 = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, t2, t2);
        User student = new User(2 , "test@gmail.com", "Danny", true, true, true, user1, t2, t2);
        AdminList.add(Admin);
        StudentList.add(student);

        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, t2);
        BatchList.add(batch);
        Page<Batch> Batches = new PageImpl<>(BatchList);

        Mockito.when(batchRepository.findByCreationTimeAfter(org.mockito.ArgumentMatchers.isA(Timestamp.class),
                org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(Batches);

        Page<Batch> response = batchService.getBatchByCreationTime(epochMilli, 0, 5, "batchId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getContent().get(0).getName());
        Assertions.assertEquals(AdminList, response.getContent().get(0).getInstructors());
        Assertions.assertEquals(StudentList, response.getContent().get(0).getUsers());
        Assertions.assertEquals(t2, response.getContent().get(0).getCreationTime());

    }

    @Test
    void whenGetBatchByCreationTime_callBatchRepository_retrieveBatchPage_testingSortBy(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority user1 = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(1 , "dan@gmail.com", "Daniel", true, true, true, authority, timestamp, timestamp);
        User student = new User(2 , "test@gmail.com", "Danny", true, true, true, user1, timestamp, timestamp);
        AdminList.add(Admin);
        StudentList.add(student);

        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, timestamp);
        BatchList.add(batch);
        Page<Batch> Batches = new PageImpl<>(BatchList);

        Mockito.when(batchRepository.findByCreationTimeAfter(org.mockito.ArgumentMatchers.isA(Timestamp.class),
                org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(Batches);

        Page<Batch> response = batchService.getBatchByCreationTime(epochMilli, 0, 5, "batchId", "asc");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getContent().get(0).getName());
        Assertions.assertEquals(AdminList, response.getContent().get(0).getInstructors());
        Assertions.assertEquals(StudentList, response.getContent().get(0).getUsers());
        Assertions.assertEquals(timestamp, response.getContent().get(0).getCreationTime());
    }
}
