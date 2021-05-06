package com.revature.studyforce.notification.integration;

import com.revature.studyforce.notification.controller.SubscriptionController;

import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.Subscription;

import com.revature.studyforce.notification.repository.StacktraceSubscriptionRepository;
import com.revature.studyforce.notification.repository.SubscriptionRepository;

import com.revature.studyforce.notification.service.StacktraceSubscriptionService;
import com.revature.studyforce.notification.service.SubscriptionService;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * @author Brandon Pinkerton
 * @author Daniel Kopeloff
 *
 * {@link SubscriptionController}
 * {@link SubscriptionService}
 * {@link SubscriptionRepository}
 * {@link StacktraceSubscriptionService}
 * {@link StacktraceSubscriptionRepository}
 * {@link StacktraceRepository}
 * {@link UserRepository}
 * {@link TechnologyRepository}
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class StacktraceSubscriptionIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private SubscriptionController subscriptionController;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private StacktraceSubscriptionService stacktraceSubscriptionService;
    @Autowired
    private StacktraceSubscriptionRepository stacktraceSubscriptionRepository;
    @Autowired
    private StacktraceRepository stacktraceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TechnologyRepository technologyRepository;

    @Test
    void givenStacktraceIdAndUserId_whenCreateNewStacktraceSubscription_ShouldReturnStacktraceSubscription() throws Exception {
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Technology tech = new Technology(0,"java");
        Stacktrace stacktrace = new Stacktrace(0,user,"Title","body", tech,Timestamp.valueOf(LocalDateTime.now()));
        technologyRepository.save(tech);
        userRepository.save(user);
        stacktraceRepository.save(stacktrace);
        Subscription subscription = new Subscription(0, user, "endpoint", "p256dh", "auth");
        subscriptionRepository.save(subscription);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/subscriptions/stacktraces")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stacktraceId\":1,\"userId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stacktraceSubscriptionId.stacktrace").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stacktraceSubscriptionId.subscription").value(2))
                .andReturn();
        System.out.println("POST");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }

    @Test
    void givenStacktraceAndUserId_whenGetSubscriptionByStacktraceIdAndUserId_ShouldReturnStacktraceSubscription() throws Exception {
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Technology tech = new Technology(0,"java");
        Stacktrace stacktrace = new Stacktrace(0,user,"Title","body", tech,Timestamp.valueOf(LocalDateTime.now()));
        System.out.println(userRepository.save(user));
        System.out.println(technologyRepository.save(tech).getTechnologyId());
        System.out.println(stacktraceRepository.save(stacktrace).getStacktraceId());
        Subscription subscription = new Subscription(0, user, "endpoint", "p256dh", "auth");
        subscriptionRepository.save(subscription);
        StacktraceSubscription stacktraceSubscription = new StacktraceSubscription(stacktrace, subscription);
        System.out.println(stacktraceSubscriptionRepository.save(stacktraceSubscription));
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/subscriptions/stacktraces?stacktrace-id=1&user-id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println("GET");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }

    @Test
    void givenStacktraceIdAndUserId_whenDeleteStacktraceSubscription_ShouldReturnDeletedStacktraceSubscription() throws Exception {
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Technology tech = new Technology(0,"java");
        Stacktrace stacktrace = new Stacktrace(0,user,"Title","body", tech,Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
        technologyRepository.save(tech);
        stacktraceRepository.save(stacktrace);
        Subscription subscription = new Subscription(0, user, "endpoint", "p256dh", "auth");
        System.out.println(subscriptionRepository.save(subscription));
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/subscriptions/stacktraces")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stacktraceId\":1,\"userId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stacktraceSubscriptionId.stacktrace").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stacktraceSubscriptionId.subscription").value(2))
                .andReturn();
        System.out.println("DELETE");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }

}
