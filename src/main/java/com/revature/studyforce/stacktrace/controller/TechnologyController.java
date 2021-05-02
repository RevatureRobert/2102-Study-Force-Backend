package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stacktrace/technology")
public class TechnologyController {

  @Autowired private TechnologyService technologyService;

  /**
   * Gets all Technologies
   *
   * @return A list of Technologies
   */
  @GetMapping()
  public List<TechnologyDTO> getAllTechnologies() {
    return technologyService.getAllTechnologies();
  }
  /**
   * Adds a new technology object
   * @param technologyDTO The technology to be added as a data transfer object
   * @return The data transfer representation of the newly added technology object
   */
  @PostMapping()
  public TechnologyDTO addNewTechnology(@RequestBody TechnologyDTO technologyDTO){
    technologyDTO.setTechnologyId(0);
    Technology t = TechnologyDTO.DTOTotechnology().apply(technologyDTO);
    return technologyService.createNewTechnology(t);
  }
}
