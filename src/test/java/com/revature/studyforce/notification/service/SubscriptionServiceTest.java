package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.dto.SubscriptionDTO;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.repository.SubscriptionRepository;
import com.revature.studyforce.user.model.User;

import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author Brandon Pinkerton
 * {@link SubscriptionService}
 * {@link SubscriptionRepository}
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @MockBean
    private SubscriptionRepository subscriptionRepository;
    @MockBean
    private UserRepository userRepository;

    User user;
    Subscription subscription;
    SubscriptionDTO subscriptionDTO;
    List<Subscription> subscriptions = new ArrayList<>();
    Page<Subscription> subscriptionPage;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setUserId(1);
        subscription = new Subscription(1, user, "endpoint",  "p256dh", "auth");
        subscriptions.add(subscription);
        subscriptionDTO = new SubscriptionDTO(1, 1, "endpoint", "key", "auth");
    }

    @Test
    void getSubscriptionByUserIdAndReturnSubscriptionTest(){
        Mockito.doReturn(subscription).when(subscriptionRepository).findByUser_UserId(user.getUserId());
        Subscription result = subscriptionService.getSubscriptionByUserId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals(1, result.getUser().getUserId());
        Assertions.assertEquals("endpoint", result.getEndpoint());
        Assertions.assertEquals("p256dh", result.getKey());
        Assertions.assertEquals("auth", result.getAuth());
    }

    @Test
    void createSubscriptionAndReturnSubscriptionTest(){
        Mockito.doReturn(subscription).when(subscriptionRepository).save(any(Subscription.class));
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(1);

        Subscription result = subscriptionService.createSubscription(subscriptionDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals(1, result.getUser().getUserId());
        Assertions.assertEquals("endpoint", result.getEndpoint());
        Assertions.assertEquals("p256dh", result.getKey());
        Assertions.assertEquals("auth", result.getAuth());
    }

    @Test
    void deleteSubscriptionAndReturnSubscriptionTest(){
        Mockito.doReturn(subscription).when(subscriptionRepository).findByUser_UserId(user.getUserId());
        Subscription result = subscriptionService.deleteSubscriptionByUserId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals(1, result.getUser().getUserId());
        Assertions.assertEquals("endpoint", result.getEndpoint());
        Assertions.assertEquals("p256dh", result.getKey());
        Assertions.assertEquals("auth", result.getAuth());
    }

}
