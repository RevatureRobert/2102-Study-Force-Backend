package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnologyService {

        private TechnologyRepository technologyRepo;

        @Autowired
        public TechnologyService (TechnologyRepository technologyRepo) {
            this.technologyRepo = technologyRepo;
        }

        /**
         * Gets all Technologies
         * @return A list of Technologies
         */
        public List<TechnologyDTO> getAllTechnologies() {
            return technologyRepo.findAll().stream().map(TechnologyDTO.technologyToDTO()).collect(Collectors.toList());
        }
    public TechnologyDTO createNewTechnology(TechnologyDTO technologyDTO){
        return TechnologyDTO.technologyToDTO().apply(technologyRepo.save(TechnologyDTO.dtoToTechnology().apply(technologyDTO)));
    }

    /**
     * Deletes Technology with the given id
     * @param technologyId Primary id of Technology to be deleted
     */
    public void deleteTechnology(int technologyId){
        technologyRepo.deleteById(technologyId);
    }

    /**
     * Updates a technology by its ID. If the technology doesn't exist it will be created.
     * @param technologyDTO Data transfer object of the technology to be updated.
     * @return Data transfer object of the technology to be updated.
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
