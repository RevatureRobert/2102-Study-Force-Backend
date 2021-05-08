package com.revature.studyforce.notification.integration;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.notification.controller.SubscriptionController;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.repository.FlashcardSubscriptionRepository;
import com.revature.studyforce.notification.repository.SubscriptionRepository;
import com.revature.studyforce.notification.service.FlashcardSubscriptionService;
import com.revature.studyforce.notification.service.SubscriptionService;
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
 * {@link SubscriptionController }
 * {@link SubscriptionService}
 * {@link SubscriptionRepository}
 * {@link FlashcardSubscriptionService}
 * {@link FlashcardSubscriptionRepository}
 * {@link FlashcardRepository}
 * {@link UserRepository}
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class FlashcardSubscriptionIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private SubscriptionController subscriptionController;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private FlashcardSubscriptionService flashcardSubscriptionService;
    @Autowired
    private FlashcardSubscriptionRepository flashcardSubscriptionRepository;
    @Autowired
    private FlashcardRepository flashcardRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void givenFlashcardIdAndUserId_whenCreateNewFlashcardSubscription_ShouldReturnFlashcardSubscription() throws Exception {
        User user = new User(0,"edson@revature.com","Edson",true,false,false,
                Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        userRepository.save(user);
        flashcardRepository.save(flashcard);
        Subscription subscription = new Subscription(0, user, "endpoint", "p256dh", "auth");
        subscriptionRepository.save(subscription);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/subscriptions/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"flashcardId\":2,\"userId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcardSubscriptionId.flashcard").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcardSubscriptionId.subscription").value(3))
                .andReturn();
        System.out.println("POST");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }

    @Test
    void givenFlashcardAndUserId_whenGetSubscriptionByFlashcardIdAndUserId_ShouldReturnFlashcardSubscription() throws Exception {
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        userRepository.save(user);
        flashcardRepository.save(flashcard);
        Subscription subscription = new Subscription(0, user, "endpoint", "p256dh", "auth");
        subscriptionRepository.save(subscription);
        FlashcardSubscription flashcardSubscription = new FlashcardSubscription(flashcard, subscription);
        flashcardSubscriptionRepository.save(flashcardSubscription);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/subscriptions/flashcards?flashcard-id=2&user-id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcardSubscriptionId.flashcard").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcardSubscriptionId.subscription").value(3))
                .andReturn();
        System.out.println("GET");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }

    @Test
    void givenFlashcardIdAndUserId_whenDeleteFlashcardSubscription_ShouldReturnDeletedFlashcardSubscription() throws Exception {
        User user = new User(0,"edson@revature.com","password","Edson","Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        userRepository.save(user);
        flashcardRepository.save(flashcard);
        Subscription subscription = new Subscription(0, user, "endpoint", "p256dh", "auth");
        subscriptionRepository.save(subscription);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/subscriptions/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"flashcardId\":2,\"userId\":1}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcardSubscriptionId.flashcard").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcardSubscriptionId.subscription").value(3))
                .andReturn();
        System.out.println("DELETE");
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }

}
