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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * tests for integration of Batch Controller {@link BatchController}
 * Include Deactivate, Create, and findByUsersInBatches
 * @author Daniel Reyes
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
@Transactional
class BatchCreateAndUpdateIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BatchController batchController;

    @Test
    void givenBatch_whenCreate_theReturnCreatedBatch() throws Exception {

        Authority authority = Authority.ADMIN;
        Authority userAuth = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(0, "admin@gmail.com", "Robert", true, true, true, authority, t2, t2);
        User student = new User(0 , "c@gmail.com", "Daniel", true, true, true, userAuth, t2, t2);
        User Admin2 = new User(0, "admin2@gmail.com", "Richard", true, true, true, authority, t2, t2);
        User student2 = new User(0 , "user2@gmail.com", "Danny", true, true, true, userAuth, t2, t2);

        userRepository.save(Admin);
        userRepository.save(Admin2);
        userRepository.save(student);
        userRepository.save(student2);

        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/batches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{ \"batchId\" : 1, \"name\" : \"2102 Enterprise\", \"instructors\" : [\"admin@gmail.com\"], \"users\" : [\"c@gmail.com\"] }"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.batchId").value(5))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("2102 Enterprise"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.users[0].name").value("Daniel"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.instructors[0].name").value("Robert"));
    }

  @Test
  void givenBatchId_whenDeactivateBatch_theReturnUpdatedBatch() throws Exception {

      Set<User> AdminList = new HashSet<>();
      Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ADMIN;
        Authority userAuth = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(0, "admin@gmail.com", "Robert", true, true, true, authority, t2, t2);
        User student = new User(0, "c@gmail.com", "Daniel", true, true, true, userAuth, t2, t2);
        User Admin2 = new User(0, "admin2@gmail.com", "Richard", true, true, true, authority, t2, t2);
        User student2 = new User(0, "user2@gmail.com", "Danny", true, true, true, userAuth, t2, t2);

      AdminList.add(Admin);
      StudentList.add(student);
      AdminList.add(Admin2);
      StudentList.add(student2);

        userRepository.save(Admin);
        userRepository.save(Admin2);
        userRepository.save(student);
        userRepository.save(student2);

        Batch batch = new Batch(0, "2102 Enterprise2", AdminList, StudentList, t2);
        batchRepository.save(batch);

    System.out.println(batch.toString());

        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        mockMvc.perform(MockMvcRequestBuilders.put("/batches/deactivateBatch/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[0].active").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructors[0].active").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[1].active").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructors[1].active").value(true));

    }

    @Test
    void givenUserId_whenGetAllBatchesUserBelongsTo_theReturnALlBatches() throws Exception {

        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();

        Authority authority = Authority.ADMIN;
        Authority userAuth = Authority.USER;
        Instant instant = Instant.now();
        long epochMilli = Date.from(instant).getTime();
        Timestamp t2 = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        User Admin = new User(0, "admin@gmail.com", "Robert", true, true, true, authority, t2, t2);
        User student = new User(0, "c@gmail.com", "Daniel", true, true, true, userAuth, t2, t2);
        User Admin2 = new User(0, "admin2@gmail.com", "Richard", true, true, true, authority, t2, t2);
        User student2 = new User(0, "user2@gmail.com", "Danny", true, true, true, userAuth, t2, t2);

        AdminList.add(Admin);
        StudentList.add(student);
        AdminList.add(Admin2);
        StudentList.add(student2);

        userRepository.save(Admin);
        userRepository.save(Admin2);
        userRepository.save(student);
        userRepository.save(student2);

        Batch batch = new Batch(0, "2102 Enterprise2", AdminList, StudentList, t2);
        batchRepository.save(batch);

        System.out.println(batch.toString());


        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/batches/userid/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].batchId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("2102 Enterprise2"));
    }
}
