package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnologyService {

        @Autowired
        private TechnologyRepository technologyRepo;

        /**
         * Gets all Technologies
         * @return A list of Technologies
         */
        public List<TechnologyDTO> getAllTechnologies() {
            return technologyRepo.findAll().stream().map(TechnologyDTO.technologyToDTO()).collect(Collectors.toList());
        }
    /**
     * Persists a technology object by calling {@link TechnologyRepository#save(Object)} and returns the newly saved technology object as its data transfer representation
     * @param technology The Technology object being persisted
     * @return The newly persisted technology object converted to its data transfer representation using {@link TechnologyDTO#technologyToDTO()}
     */
    public TechnologyDTO createNewTechnology(Technology technology){
        technologyRepo.save(technology);
        //return TechnologyDTO.technologyToDTO().apply(saved);
        return TechnologyDTO.technologyToDTO().apply(technology);
    }
}
