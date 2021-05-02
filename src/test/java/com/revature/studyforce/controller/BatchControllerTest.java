package com.revature.studyforce.controller;

import com.revature.studyforce.user.contollers.BatchController;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.Batch;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.BatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
public class BatchControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BatchController batchController;


    @Test
    void GetAllBatches() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(1 , "dan2@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(2 , "test2@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(110, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        System.out.println(batch.toString());
        //batchRepository.save(batch);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        //MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/allBatches?offset=5&order=ASC&page=0&sortby=batchId")
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/allBatches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].batchId").value(100))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    void GetBatchesById() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(1 , "dan2@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(2 , "test2@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(110, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        System.out.println(batch.toString());
        //batchRepository.save(batch);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/batch/109")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].batchId").value(100))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void GetBatchesByName() throws Exception {
        Set<User> AdminList = new HashSet<>();
        Set<User> StudentList = new HashSet<>();
        List<Batch> BatchList = new ArrayList<>();

        Authority authority = Authority.ADMIN;
        Authority user1 = Authority.USER;
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        User Admin = new User(1 , "dan2@gmail.com", "pass", "Daniel", true, true, true, authority, lastLoginTime, lastLoginTime);
        User student = new User(2 , "test2@gmail.com", "pass", "Danny", true, true, true, user1, lastLoginTime, lastLoginTime);
        AdminList.add(Admin);
        StudentList.add(student);
        Batch batch = new Batch(110, "2102 Enterprise2", AdminList, StudentList, lastLoginTime);
        System.out.println(batch.toString());
        //batchRepository.save(batch);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Batch/batch/name?name=2102%20Enterprise2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].batchId").value(100))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
