package com.revature.studyforce.user.service;


import com.revature.studyforce.user.dto.CreateUpdateBatchDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.BatchRepository;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * Service Layer Testing {@link BatchService }
 * @author Daniel Reyes
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(username = "test@test.test",authorities = "ROLE_USER")
class BatchServiceCreateTest {

    @MockBean
    private BatchRepository batchRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BatchService batchService;

    @Test
    void givenBatchDTO_whenCreateBatch_theBatchCreated(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority userAuth = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(0, "admin@gmail.com", "Robert", true, true, true, authority, t2, t2);
        User student = new User(0 , "user@gmail.com", "Daniel", true, true, true, userAuth, t2, t2);
        User Admin2 = new User(0, "admin2@gmail.com", "Richard", true, true, true, authority, t2, t2);
        User student2 = new User(0 , "user2@gmail.com", "Danny", true, true, true, userAuth, t2, t2);
        Set<String> users = new HashSet<>();
        Set<String> instructor = new HashSet<>();
        users.add("user@gmail.com");
        users.add("user2@gmail.com");
        instructor.add("admin@gmail.com");
        instructor.add("admin2@gmail.com");
        AdminList.add(Admin);
        AdminList.add(Admin2);
        StudentList.add(student);
        StudentList.add(student2);

        CreateUpdateBatchDTO batch = new CreateUpdateBatchDTO(0, "2102 Enterprise", instructor, users);

        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(student));
        Mockito.when(userRepository.findByEmail("user2@gmail.com")).thenReturn(Optional.of(student2));
        Mockito.when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(Admin));
        Mockito.when(userRepository.findByEmail("admin2@gmail.com")).thenReturn(Optional.of(Admin2));
        Mockito.when(userRepository.findByEmail("admin2@gmail.com")).thenReturn(Optional.of(Admin2));

        Batch response = batchService.createBatch(batch);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getName());
        Assertions.assertEquals(AdminList, response.getInstructors());
        Assertions.assertEquals(StudentList, response.getUsers());

    }

    @Test
    void givenBatch_whenUpdate_theUpdatedBatches(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Set<User> updatedAdminList = new HashSet<>();
        Set<User> updatedStudentList = new HashSet<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority userAuth = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(0, "admin@gmail.com", "Robert", true, true, true, authority, t2, t2);
        User student = new User(0 , "user@gmail.com", "Daniel", true, true, true, userAuth, t2, t2);
        User Admin2 = new User(0, "admin2@gmail.com", "Richard", true, true, true, authority, t2, t2);
        User student2 = new User(0 , "user2@gmail.com", "Danny", true, true, true, userAuth, t2, t2);
        Set<String> users = new HashSet<>();
        Set<String> instructor = new HashSet<>();
        users.add("user@gmail.com");
        users.add("user2@gmail.com");
        instructor.add("admin@gmail.com");
        instructor.add("admin2@gmail.com");
        AdminList.add(Admin);
        AdminList.add(Admin2);
        StudentList.add(student);
        StudentList.add(student2);

        CreateUpdateBatchDTO batch = new CreateUpdateBatchDTO(0, "2102 Enterprise", instructor, users);
        Batch batch1 = new Batch(1, "2102 Enterprise", AdminList, StudentList, t2);

        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(student));
        Mockito.when(userRepository.findByEmail("user2@gmail.com")).thenReturn(Optional.of(student2));
        Mockito.when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(Admin));
        Mockito.when(userRepository.findByEmail("admin2@gmail.com")).thenReturn(Optional.of(Admin2));
        Mockito.when(batchRepository.findById(1)).thenReturn(Optional.of(batch1));

        Batch response = batchService.createBatch(batch);

        batch.setName("1403 Enterprise V2");
        User student3 = new User(0 , "user3@gmail.com", "Nicholas", true, true, true, userAuth, t2, t2);
        users.add("user3@gmail.com");
        batch.setUsers(users);
        batch.setBatchId(1);
        updatedAdminList.add(Admin);
        updatedAdminList.add(Admin2);
        updatedStudentList.add(student);
        updatedStudentList.add(student2);
        updatedStudentList.add(student3);
        Mockito.when(userRepository.findByEmail("user3@gmail.com")).thenReturn(Optional.of(student3));

        Batch updateResponse = batchService.updateBatch(batch);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getBatchId());
        Assertions.assertEquals("2102 Enterprise", response.getName());
        Assertions.assertEquals(AdminList, response.getInstructors());
        Assertions.assertEquals(StudentList, response.getUsers());

        Assertions.assertNotNull(updateResponse);
        Assertions.assertEquals(1, updateResponse.getBatchId());
        Assertions.assertEquals("1403 Enterprise V2", updateResponse.getName());
        Assertions.assertEquals(updatedAdminList, updateResponse.getInstructors());
        Assertions.assertEquals(updatedStudentList, updateResponse.getUsers());

    }

    @Test
    void givenBatchId_deactivateBatch_thenUpdatedBatch(){
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ROLE_ADMIN;
        Authority userAuth = Authority.ROLE_USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(0, "admin@gmail.com", "Robert", true, true, true, authority, t2, t2);
        User student = new User(0 , "user@gmail.com", "Daniel", true, true, true, userAuth, t2, t2);
        User Admin2 = new User(0, "admin2@gmail.com", "Richard", true, true, true, authority, t2, t2);
        User student2 = new User(0 , "user2@gmail.com", "Danny", true, true, true, userAuth, t2, t2);
        Set<String> users = new HashSet<>();
        Set<String> instructor = new HashSet<>();
        users.add("user@gmail.com");
        users.add("user2@gmail.com");
        instructor.add("admin@gmail.com");
        instructor.add("admin2@gmail.com");
        AdminList.add(Admin);
        AdminList.add(Admin2);
        StudentList.add(student);
        StudentList.add(student2);

        CreateUpdateBatchDTO batch = new CreateUpdateBatchDTO(0, "2102 Enterprise", instructor, users);
        Batch batch1 = new Batch(1, "2102 Enterprise", AdminList, StudentList, t2);

        Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(student));
        Mockito.when(userRepository.findByEmail("user2@gmail.com")).thenReturn(Optional.of(student2));
        Mockito.when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(Admin));
        Mockito.when(userRepository.findByEmail("admin2@gmail.com")).thenReturn(Optional.of(Admin2));
        Mockito.when(batchRepository.findById(1)).thenReturn(Optional.of(batch1));

        Optional<User> userFound = userRepository.findByEmail("user@gmail.com");
        Optional<User> userFound2 = userRepository.findByEmail("user2@gmail.com");
        Optional<User> userFound3 = userRepository.findByEmail("admin@gmail.com");
        Optional<User> userFound4 = userRepository.findByEmail("admin2@gmail.com");

        //Check all are true before deactivate
        Assertions.assertEquals(true, userFound.get().isActive());
        Assertions.assertEquals(true, userFound2.get().isActive());
        Assertions.assertEquals(true, userFound3.get().isActive());
        Assertions.assertEquals(true, userFound4.get().isActive());

        batchService.deactivateBatch(1);

        //Check all are false after deactivate
        Assertions.assertEquals(false, userFound.get().isActive());
        Assertions.assertEquals(false, userFound2.get().isActive());
        Assertions.assertEquals(true, userFound3.get().isActive());
        Assertions.assertEquals(true, userFound4.get().isActive());


    }

    @Test
    void givenBatchId_whenDeleteBatch_theBatchesDeleted(){

        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));

        Batch batch2 = new Batch(1, "2102 Enterprise", null, null, t2);

        Mockito.when(batchRepository.findById(1)).thenReturn(Optional.of(batch2));

        Batch response  = batchService.deleteBatch(1);
        System.out.println(response.toString());
        Assertions.assertEquals(response, batch2);

    }
}
