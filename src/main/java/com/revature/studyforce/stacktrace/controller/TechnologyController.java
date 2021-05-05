package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stacktrace/technology")
@CrossOrigin("http://localhost:4200")

public class TechnologyController {

  private TechnologyService technologyService;

  @Autowired public TechnologyController (TechnologyService technologyService) {
    this.technologyService = technologyService;
  }


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
  @PostMapping
  public TechnologyDTO addNewTechnology(@RequestBody TechnologyDTO technologyDTO){
    return technologyService.createNewTechnology(technologyDTO);
  }

  /**
   * Deletes a technology by its ID
   * @param technologyId the ID of the technology to be deleted
   * @return an empty HTML response
   */
  @DeleteMapping("/{technologyId}")
  public ResponseEntity<Void> deleteTechnology(@PathVariable int technologyId){
    technologyService.deleteTechnology(technologyId);
    return ResponseEntity.noContent().build();
  }
  @PutMapping
  public TechnologyDTO updateTechnology(@RequestBody TechnologyDTO technologyDTO){
    return technologyService.updateTechnology(technologyDTO);
  }
}
