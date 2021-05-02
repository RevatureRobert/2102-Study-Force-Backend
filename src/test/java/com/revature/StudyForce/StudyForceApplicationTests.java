package com.revature.StudyForce;

import com.revature.StudyForce.flashcard.controller.TopicController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyForceApplicationTests {

	@Autowired
	private TopicController topicController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(topicController);
	}

}
