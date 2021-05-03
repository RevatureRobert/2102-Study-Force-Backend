package com.revature.studyforce;

import com.revature.studyforce.flashcard.controller.TopicController;
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
