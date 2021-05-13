package com.revature.studyforce.notification.service;


import com.revature.studyforce.notification.model.StacktraceSubscription;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.repository.StacktraceSubscriptionRepository;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Brandon Pinkerton
 * @author Daniel Kopeloff
 * {@link StacktraceSubscriptionService}
 * {@link StacktraceSubscriptionRepository}
 * * {@link StacktraceRepository}
 *  * {@link SubscriptionService}
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class StacktraceSubscriptionServiceTest {
    @Autowired
    private StacktraceSubscriptionService stacktraceSubscriptionService;

    @MockBean
    private StacktraceSubscriptionRepository stacktraceSubscriptionRepository;
    @MockBean
    private StacktraceRepository stacktraceRepository;
    @MockBean
    private SubscriptionService subscriptionService;

    StacktraceSubscription stacktraceSubscription;
    List<StacktraceSubscription> stacktraceSubscriptions = new ArrayList<>();
    Stacktrace stacktrace;
    Subscription subscription;

    @BeforeEach
    public void setUp(){
        stacktrace = new Stacktrace();
        stacktrace.setStacktraceId(1);
        subscription = new Subscription();
        subscription.setId(1);
        stacktraceSubscription = new StacktraceSubscription(stacktrace, subscription);
        stacktraceSubscriptions.add(stacktraceSubscription);
    }

    @Test
    void getStacktraceSubscriptionByStacktraceAndUserIdAndReturnStacktraceSubscriptionTest(){
        Mockito.doReturn(Optional.of(stacktrace)).when(stacktraceRepository).findById(1);
        Mockito.doReturn(subscription).when(subscriptionService).getSubscriptionByUserId(1);
        Mockito.doReturn(stacktraceSubscription).when(stacktraceSubscriptionRepository)
                .findAllByStacktraceSubscriptionId(stacktraceSubscription.getStacktraceSubscriptionId());
        StacktraceSubscription result = stacktraceSubscriptionService.getStacktraceSubscriptionByStacktraceAndUserId(1,1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), result.getStacktraceSubscriptionId());
        Assertions.assertEquals(1, result.getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, result.getSubscription().getId());
    }

    @Test
    void createStacktraceSubscriptionByStacktraceAndUserIdAndReturnStacktraceSubscriptionTest(){
        Mockito.doReturn(Optional.of(stacktrace)).when(stacktraceRepository).findById(1);
        Mockito.doReturn(subscription).when(subscriptionService).getSubscriptionByUserId(1);
        Mockito.doReturn(stacktraceSubscription).when(stacktraceSubscriptionRepository)
                .save(stacktraceSubscription);
        StacktraceSubscription result = stacktraceSubscriptionService.createStacktraceSubscription(1,1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), result.getStacktraceSubscriptionId());
        Assertions.assertEquals(1, result.getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, result.getSubscription().getId());
    }

    @Test
    void getAllSubscribersByStacktraceIdTestAndReturnStacktraceSubscriptionsListTest(){
        Mockito.doReturn(stacktraceSubscriptions).when(stacktraceSubscriptionRepository).findAllByStacktrace_StacktraceId(1);
        List<StacktraceSubscription> result = stacktraceSubscriptionService.getAllSubscribersByStacktraceId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktraceSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());

    }

    @Test
    void getAllSubscriptionsByUserIdTestAndReturnStacktraceSubscriptionsListTest(){
        Mockito.doReturn(stacktraceSubscriptions).when(stacktraceSubscriptionRepository).findAllBySubscription_User_UserId(1);
        List<StacktraceSubscription> result = stacktraceSubscriptionService.getAllSubscriptionsByUserId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktraceSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());

    }

    @Test
    void deleteStacktraceSubscriptionByStacktraceAndUserIdAndReturnStacktraceSubscriptionTest(){
        Mockito.doReturn(Optional.of(stacktrace)).when(stacktraceRepository).findById(1);
        Mockito.doReturn(subscription).when(subscriptionService).getSubscriptionByUserId(1);
        StacktraceSubscription result = stacktraceSubscriptionService.deleteStacktraceSubscription(1,1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), result.getStacktraceSubscriptionId());
        Assertions.assertEquals(1, result.getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, result.getSubscription().getId());
    }

    @Test
    void deleteAllStacktraceSubscriptionsByStacktraceSubscriptionsListAndReturnStacktraceSubscriptionsListTest(){
        List<StacktraceSubscription> result = stacktraceSubscriptionService.deleteAllStacktraceSubscriptions(stacktraceSubscriptions);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktraceSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());
    }

    @Test
    void deleteAllStacktraceSubscriptionsByStacktraceIdAndReturnStacktraceSubscriptionsListTest(){
        Mockito.doReturn(stacktraceSubscriptions).when(stacktraceSubscriptionRepository).findAllByStacktrace_StacktraceId(1);
        List<StacktraceSubscription> result = stacktraceSubscriptionService.deleteAllStacktraceSubscriptionByStacktraceId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktraceSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());
    }

    @Test
    void deleteAllStacktraceSubscriptionsByUserIdAndReturnStacktraceSubscriptionsListTest(){
        Mockito.doReturn(stacktraceSubscriptions).when(stacktraceSubscriptionRepository).findAllBySubscription_User_UserId(1);
        List<StacktraceSubscription> result = stacktraceSubscriptionService.deleteAllStacktraceSubscriptionsByUserId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(stacktraceSubscription.getStacktraceSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktraceSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getStacktrace().getStacktraceId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());
    }
}
