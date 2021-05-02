package com.revature.studyforce.controller;

import com.revature.studyforce.user.contollers.BatchController;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * tests for integration of Batch Controller
 *
 * @author Daniel Reyes
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class BatchControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BatchController batchController;


    @Transactional
    @Test
    void GetAllBatches() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        //List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(0 , "dan2@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(0 , "test2@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(0, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        System.out.println(batch.toString());
        batchRepository.save(batch);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        //MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/allBatches?offset=5&order=ASC&page=0&sortby=batchId")
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/allBatches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].batchId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("2102 Enterprise2"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Transactional
    @Test
    void GetBatchById() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        //List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(0 , "dan2@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(0 , "test2@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        Admin = userRepository.save(Admin);
        student = userRepository.save(student);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(0, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        //System.out.println(batch.toString());
        batchRepository.save(batch);
    System.out.println(batchRepository.findAll());
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/batch/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batchId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("2102 Enterprise2"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Transactional
    @Test
    void GetBatchByName() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        //List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(0 , "dan2@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(0 , "test2@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        Admin = userRepository.save(Admin);
        student = userRepository.save(student);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(0, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        //System.out.println(batch.toString());
        batchRepository.save(batch);
        System.out.println(batchRepository.findAll());
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/name?name=2102 Enterprise2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batchId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("2102 Enterprise2"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
