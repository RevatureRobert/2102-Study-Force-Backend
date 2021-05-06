package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for technologyDTO
 * @author John Stone
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class TechnologyDTOTest {
    Technology testTechnology = new Technology(1, "TestTech");
    TechnologyDTO testTechnologyDTO = new TechnologyDTO(1, "TestTech");

    @BeforeEach
    public void setUp(){
        testTechnologyDTO = new TechnologyDTO(1, "TestTech");
        testTechnology = new Technology(1, "TestTech");
    }

    /**
     * Test that a Technology can correctly return a TechnologyDTO
     */
    @Test
    void whenTechnologyToDTOCalledThenCorrectDTOReturned(){
        TechnologyDTO technologyDTO = TechnologyDTO.technologyToDTO().apply(testTechnology);
        assertEquals(technologyDTO.getClass(), TechnologyDTO.class);
        assertEquals(technologyDTO.getTechnologyId(), testTechnology.getTechnologyId());
        assertEquals(technologyDTO.getTechnologyName(),testTechnology.getTechnologyName());
    }


    /**
     * Test that a TechnologyDTO can correctly return a Technology
     */
    @Test
    void whenDTOToTechnologyCalledThenCorrectTEchnologyReturned(){
        Technology Technology = TechnologyDTO.dtoToTechnology().apply(testTechnologyDTO);
        assertEquals(Technology.getClass(), Technology.class);
        assertEquals(Technology.getTechnologyId(), testTechnologyDTO.getTechnologyId());
        assertEquals(Technology.getTechnologyName(), testTechnologyDTO.getTechnologyName());
    }
}
