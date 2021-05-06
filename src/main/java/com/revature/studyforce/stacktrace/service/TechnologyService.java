package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service which processes various requests related to {@link Technology Technologies}
 * <p>Relies on {@link TechnologyRepository} and {@link TechnologyDTO} for processing these requests</p>
 * @author Joey Elmbald
 * @author John Stone
 */
@Service
public class TechnologyService {
    private final TechnologyRepository technologyRepo;

    @Autowired
    public TechnologyService (TechnologyRepository technologyRepo) {
        this.technologyRepo = technologyRepo;
    }

    /**
     * Gets all Technologies as a List of {@link TechnologyDTO TechnologyDTOs} using {@link TechnologyRepository#findAll}
     * @return A List of All Technologies in the Database
     */
    public List<TechnologyDTO> getAllTechnologies() {
        return technologyRepo.findAll().stream().map(TechnologyDTO.technologyToDTO()).collect(Collectors.toList());
    }

    /**
     * Adds a new Technology to the Databse
     * <p>Converts a {@link TechnologyDTO} into a {@link Technology} and saves it using {@link TechnologyRepository#save}</p>
     * @param technologyDTO The Data Transfer Object to be converted to a {@link Technology}
     * @return A {@link TechnologyDTO} matching the {@link Technology} created
     */
    public TechnologyDTO createNewTechnology(TechnologyDTO technologyDTO){
        return TechnologyDTO.technologyToDTO().apply(technologyRepo.save(TechnologyDTO.dtoToTechnology().apply(technologyDTO)));
    }

    /**
     * Deletes {@link Technology} with the given id
     * <p>Searches using {@link TechnologyRepository#findById} to find a {@link Technology} that matches technologyId</p>
     * @param technologyId Primary id of the {@link Technology} to be deleted
     * @return A copy of {@link Technology}
     */
    public Technology deleteTechnology(int technologyId){
        Technology technology = technologyRepo.findById(technologyId).orElse(null);
        if(technology == null)
            return null;
        technologyRepo.delete(technology);
        return technology;
    }

    /**
     * Updates a technology by its ID. If the technology doesn't exist it will be created.
     * <p>Searches using {@link TechnologyRepository#findById} to find a {@link Technology} that that matches the input.
     * If it exists, the {@link Technology} is updated.
     * If it doesn't exist, a new {@link Technology} is created. Both cases utilize {@link TechnologyRepository#save}.</p>
     * @param technologyDTO Data transfer object of the {@link Technology} to be updated
     * @return The technologyDTO parameter is returned without modification
     */
    public TechnologyDTO updateTechnology(TechnologyDTO technologyDTO){
        Optional<Technology> technology = technologyRepo.findById(technologyDTO.getTechnologyId());
        if(technology.isPresent()){
            technology.get().setTechnologyName(technologyDTO.getTechnologyName());
            return TechnologyDTO.technologyToDTO().apply(technologyRepo.save(technology.get()));
        }else{
            Technology newTechnology = TechnologyDTO.dtoToTechnology().apply(technologyDTO);
            technologyRepo.save(newTechnology);
            return technologyDTO;
        }
    }
}
