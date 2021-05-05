package com.revature.studyforce.notification;

import com.revature.studyforce.StudyForceApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Used to configure HATOEOS links in JPA repository
 * @author Ronald Lopez
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StudyForceApplication.class);
    }
}
