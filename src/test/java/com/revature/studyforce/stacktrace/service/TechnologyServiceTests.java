package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(username = "test@test.test",authorities = "ROLE_USER")
class TechnologyServiceTests {
    List<Technology> technologyArrayList;

    @MockBean
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyService technologyService;

    private Technology testTech;


    @BeforeEach
    private void beforeEach() {
        testTech = new Technology(0,"TestTech");
        technologyArrayList = new ArrayList<>();
        technologyArrayList.add(testTech);
    }

    @Test
    void WhenGetAllTechnologies_ThenTechnologyListReturned() {
        Mockito.doReturn(technologyArrayList).when(technologyRepository).findAll();
        List<Technology> returnedTechnologyList = technologyService.getAllTechnologies().stream().map(TechnologyDTO.dtoToTechnology()).collect(Collectors.toList());
        for(int i = 0; i < returnedTechnologyList.size(); i++){
            assertEquals(returnedTechnologyList.get(i).getTechnologyId(), technologyArrayList.get(i).getTechnologyId());
            assertEquals(returnedTechnologyList.get(i).getTechnologyName(), technologyArrayList.get(i).getTechnologyName());
        }
    }
    @Test
    void WhenCreateTechnology_ThenTechnologyReturned() {
        Mockito.doReturn(testTech).when(technologyRepository).save(any(Technology.class));
        TechnologyDTO technologyDTO = technologyService.createNewTechnology(new TechnologyDTO(0,"TestTech"));
        assertEquals(0,technologyDTO.getTechnologyId());
        assertEquals("TestTech",technologyDTO.getTechnologyName());
    }

    @Test
    void whenDeleteTechnologyCalled_ThenDeleteAndReturnTechnology() {
        Mockito.doReturn(Optional.of(testTech)).when(technologyRepository).findById(1);
        Mockito.doNothing().when(technologyRepository).delete(testTech);
        assertEquals(testTech, technologyService.deleteTechnology(1));
    }

    @Test
    void whenDeleteTechnologyCalledWithNullParameter_ThenReturnNull() {
        Mockito.doReturn(Optional.ofNullable(null)).when(technologyRepository).findById(1);
        assertNull(technologyService.deleteTechnology(1));
    }
}
