package com.revature.studyforce.user.integration;

import com.revature.studyforce.user.controller.BatchController;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.BatchRepository;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * tests for integration of Batch Controller {@link BatchController}
 * @author Daniel Reyes
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
@Transactional
class BatchIntegrationTest {


    private MockMvc mockMvc;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BatchController batchController;


    @Test
    void givenBatch_whenGetAll_theBatchesRetrieved() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(0 , "dan2@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(0 , "test2@gmail.com", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(0, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        System.out.println(batch.toString());
        batchRepository.save(batch);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/batch")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].batchId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("2102 Enterprise2"));
    }

    @Test
    void givenBatch_whenGetById_theBatchRetrieved() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(0 , "dan2@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(0 , "test2@gmail.com", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        Admin = userRepository.save(Admin);
        student = userRepository.save(student);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(0, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        batchRepository.save(batch);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/batch/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batchId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("2102 Enterprise2"));
    }

    @Test
    void givenBatch_whenGetBatchByName_theBatchRetrieved() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(0 , "dan2@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(0 , "test2@gmail.com", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        Admin = userRepository.save(Admin);
        student = userRepository.save(student);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(0, "Enterprise2", AdminList, StudentList, lastLoginTime);
        batchRepository.save(batch);
        System.out.println(batchRepository.findByNameContainingIgnoreCase("Enterprise2"));
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/batch/name?name=Enterprise2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.batchId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Enterprise2"));
    }

    @Test
    void givenBatch_whenGetBatchByCreationTime_theBatchRetrieved() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(0 , "dan2@gmail.com", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(0 , "test2@gmail.com", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        Admin = userRepository.save(Admin);
        student = userRepository.save(student);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(3, "Enterprise2", AdminList, StudentList, t2);
        batchRepository.save(batch);
        System.out.println(batchRepository.findByNameContainingIgnoreCase("Enterprise2"));
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/batch/time/1619996684739")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].batchId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Enterprise2"));
    }
}
