package com.revature.studyforce.service;

import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.BatchRepository;
import com.revature.studyforce.user.repository.UserRepository;
import com.revature.studyforce.user.service.BatchService;
import com.revature.studyforce.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.util.*;

/**
 *
 * @author Daniel Reyes
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class BatchServiceTest {
    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private BatchService batchService;

    @Test
    void GetAllBatches(){

        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(2 , "test@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, lastLoginTime);
        BatchList.add(batch);
        Page<Batch> Batches = new PageImpl<>(BatchList);

        Mockito.when(batchRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(Batches);

        Page<Batch> response = batchService.getAllBatches(0, 5, "batchId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getContent().get(0).getName());
        Assertions.assertEquals(AdminList, response.getContent().get(0).getInstructors());
        Assertions.assertEquals(StudentList, response.getContent().get(0).getUsers());
        Assertions.assertEquals(lastLoginTime, response.getContent().get(0).getCreationTime());
    }

    @Test
    void GetBatchById(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(2 , "test@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);

        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, lastLoginTime);
        BatchList.add(batch);

        Mockito.when(batchRepository.findById(1)).thenReturn(Optional.of(batch));


        Optional<Batch> response = batchService.getBatchById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.get().getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.get().getName());
        Assertions.assertEquals(AdminList, response.get().getInstructors());
        Assertions.assertEquals(StudentList, response.get().getUsers());
        Assertions.assertEquals(lastLoginTime, response.get().getCreationTime());
    }

    @Test
    void GetBatchByName(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(2 , "test@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);

        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, lastLoginTime);
        BatchList.add(batch);

        Mockito.when(batchRepository.findByNameIgnoreCase("2102")).thenReturn(batch);


        Batch response = batchService.getBatchByName("2102");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getName());
        Assertions.assertEquals(AdminList, response.getInstructors());
        Assertions.assertEquals(StudentList, response.getUsers());
        Assertions.assertEquals(lastLoginTime, response.getCreationTime());
    }

    @Test
    void GetBatchByCreationTime(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(1 , "dan@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(2 , "test@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);

        Batch batch = new Batch(1, "2102 Enterprise", AdminList, StudentList, lastLoginTime);
        BatchList.add(batch);
        Page<Batch> Batches = new PageImpl<>(BatchList);

        Mockito.when(batchRepository.findByCreationTimeAfter(org.mockito.ArgumentMatchers.isA(Timestamp.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(Batches);


        Page<Batch> response = batchService.getBatchByCreationTime(lastLoginTime, 0, 5, "batchId", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getContent().get(0).getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getContent().get(0).getName());
        Assertions.assertEquals(AdminList, response.getContent().get(0).getInstructors());
        Assertions.assertEquals(StudentList, response.getContent().get(0).getUsers());
        Assertions.assertEquals(lastLoginTime, response.getContent().get(0).getCreationTime());
    }



}
