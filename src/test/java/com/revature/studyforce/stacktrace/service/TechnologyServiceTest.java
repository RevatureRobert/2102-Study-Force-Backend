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
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TechnologyServiceTest {
    List<Technology> technologyArrayList;

    @MockBean
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyService technologyService;

    @BeforeEach
    private void beforeEach() {
        technologyArrayList = new ArrayList<>();
        technologyArrayList.add(
                new Technology(0,"TestTech")
        );
    }

    @Test
    void getAllTechnologies() {
        Mockito.when(technologyRepository.findAll()).thenReturn(technologyArrayList);
        List<Technology> returnedTechnologyList = technologyService.getAllTechnologies().stream().map(TechnologyDTO.DTOtoTechnology()).collect(Collectors.toList());
        for(int i = 0; i < returnedTechnologyList.size(); i++){
            assertEquals(returnedTechnologyList.get(i).getTechnologyId(), technologyArrayList.get(i).getTechnologyId());
            assertEquals(returnedTechnologyList.get(i).getTechnologyName(), technologyArrayList.get(i).getTechnologyName());
        }
    }
    @Test
    void createTechnology() {
        technologyService.createNewTechnology(new Technology(0,"TestTech"));
        Mockito.when(technologyRepository.findAll()).thenReturn(technologyArrayList);
        assertEquals(technologyRepository.findAll().get(0).getTechnologyId(), 0);
        assertEquals(technologyRepository.findAll().get(0).getTechnologyName(), "TestTech");
    }
}
