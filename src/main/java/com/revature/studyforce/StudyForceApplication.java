package com.revature.studyforce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class StudyForceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyForceApplication.class, args);
	}

}
