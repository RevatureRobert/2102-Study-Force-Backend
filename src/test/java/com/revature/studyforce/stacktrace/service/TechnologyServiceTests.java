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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TechnologyServiceTest {
    //TODO: Standardize Tests across project
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
    void getAllTechnologiesTest() {
        Mockito.when(technologyRepository.findAll()).thenReturn(technologyArrayList);
        List<Technology> returnedTechnologyList = technologyService.getAllTechnologies().stream().map(TechnologyDTO.dTOtoTechnology()).collect(Collectors.toList());
        for(int i = 0; i < returnedTechnologyList.size(); i++){
            assertEquals(returnedTechnologyList.get(i).getTechnologyId(), technologyArrayList.get(i).getTechnologyId());
            assertEquals(returnedTechnologyList.get(i).getTechnologyName(), technologyArrayList.get(i).getTechnologyName());
        }
    }
    @Test
    void createTechnologyTest() {
        technologyService.createNewTechnology(new Technology(0,"TestTech"));
        Mockito.when(technologyRepository.findAll()).thenReturn(technologyArrayList);
        assertEquals(0,technologyRepository.findAll().get(0).getTechnologyId());
        assertEquals("TestTech",technologyRepository.findAll().get(0).getTechnologyName());
    }
    @Test
    void deleteTechnologyTest(){
        Mockito.doNothing().when(technologyRepository).deleteById(any(Integer.class));
        technologyService.deleteTechnology(1);
        verify(technologyRepository, times(1)).deleteById(any(Integer.class));
    }
}
