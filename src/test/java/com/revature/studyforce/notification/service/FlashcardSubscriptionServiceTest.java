package com.revature.studyforce.notification.service;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.notification.model.FlashcardSubscription;
import com.revature.studyforce.notification.model.Subscription;
import com.revature.studyforce.notification.repository.FlashcardSubscriptionRepository;
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
 * {@link FlashcardSubscriptionService }
 * {@link FlashcardSubscriptionRepository}
 * {@link FlashcardRepository}
 * {@link SubscriptionService}
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
public class FlashcardSubscriptionServiceTest {
    @Autowired
    private FlashcardSubscriptionService flashcardSubscriptionService;

    @MockBean
    private FlashcardSubscriptionRepository flashcardSubscriptionRepository;
    @MockBean
    private FlashcardRepository flashcardRepository;
    @MockBean
    private SubscriptionService subscriptionService;

    FlashcardSubscription flashcardSubscription;
    List<FlashcardSubscription> flashcardSubscriptions = new ArrayList<>();
    Flashcard flashcard;
    Subscription subscription;

    @BeforeEach
    public void setUp(){
        flashcard = new Flashcard();
        flashcard.setId(1);
        subscription = new Subscription();
        subscription.setId(1);
        flashcardSubscription = new FlashcardSubscription(flashcard, subscription);
        flashcardSubscriptions.add(flashcardSubscription);
    }

    @Test
    void getFlashcardSubscriptionByFlashcardAndUserId(){
        Mockito.doReturn(Optional.of(flashcard)).when(flashcardRepository).findById(1);
        Mockito.doReturn(subscription).when(subscriptionService).getSubscriptionByUserId(1);
        Mockito.doReturn(flashcardSubscription).when(flashcardSubscriptionRepository)
                .findByFlashcardSubscriptionId(flashcardSubscription.getFlashcardSubscriptionId());
        FlashcardSubscription result = flashcardSubscriptionService.getFlashcardSubscriptionByFlashcardAndUserId(1,1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), result.getFlashcardSubscriptionId());
        Assertions.assertEquals(1, result.getFlashcard().getId());
        Assertions.assertEquals(1, result.getSubscription().getId());
    }

    @Test
    void createFlashcardSubscriptionByFlashcardAndUserId(){
        Mockito.doReturn(Optional.of(flashcard)).when(flashcardRepository).findById(1);
        Mockito.doReturn(subscription).when(subscriptionService).getSubscriptionByUserId(1);
        Mockito.doReturn(flashcardSubscription).when(flashcardSubscriptionRepository)
                .save(flashcardSubscription);
        FlashcardSubscription result = flashcardSubscriptionService.createFlashcardSubscription(1,1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), result.getFlashcardSubscriptionId());
        Assertions.assertEquals(1, result.getFlashcard().getId());
        Assertions.assertEquals(1, result.getSubscription().getId());
    }

    @Test
    void getAllSubscribersByFlashcardIdTest(){
        Mockito.doReturn(flashcardSubscriptions).when(flashcardSubscriptionRepository).findAllByFlashcard_Id(1);
        List<FlashcardSubscription> result = flashcardSubscriptionService.getAllSubscribersByFlashcardId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcardSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcard().getId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());

    }

    @Test
    void getAllSubscriptionsByUserIdTest(){
        Mockito.doReturn(flashcardSubscriptions).when(flashcardSubscriptionRepository).findAllBySubscription_User_UserId(1);
        List<FlashcardSubscription> result = flashcardSubscriptionService.getAllSubscriptionsByUserId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcardSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcard().getId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());

    }

    @Test
    void deleteFlashcardSubscriptionByFlashcardAndUserId(){
        Mockito.doReturn(Optional.of(flashcard)).when(flashcardRepository).findById(1);
        Mockito.doReturn(subscription).when(subscriptionService).getSubscriptionByUserId(1);
        FlashcardSubscription result = flashcardSubscriptionService.deleteFlashcardSubscription(1,1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), result.getFlashcardSubscriptionId());
        Assertions.assertEquals(1, result.getFlashcard().getId());
        Assertions.assertEquals(1, result.getSubscription().getId());
    }

    @Test
    void deleteAllFlashcardSubscriptionsByFlashcardSubscriptionsList(){
        List<FlashcardSubscription> result = flashcardSubscriptionService.deleteAllFlashcardSubscriptions(flashcardSubscriptions);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcardSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcard().getId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());
    }

    @Test
    void deleteAllFlashcardSubscriptionsByFlashcardId(){
        Mockito.doReturn(flashcardSubscriptions).when(flashcardSubscriptionRepository).findAllByFlashcard_Id(1);
        List<FlashcardSubscription> result = flashcardSubscriptionService.deleteAllFlashcardSubscriptionsByFlashcardId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcardSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcard().getId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());
    }

    @Test
    void deleteAllFlashcardSubscriptionsByUserId(){
        Mockito.doReturn(flashcardSubscriptions).when(flashcardSubscriptionRepository).findAllBySubscription_User_UserId(1);
        List<FlashcardSubscription> result = flashcardSubscriptionService.deleteAllFlashcardSubscriptionsByUserId(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(flashcardSubscription.getFlashcardSubscriptionId(), Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcardSubscriptionId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getFlashcard().getId());
        Assertions.assertEquals(1, Objects.requireNonNull(result.stream().findFirst().orElse(null)).getSubscription().getId());
    }
}
