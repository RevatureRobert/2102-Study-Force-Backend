package com.revature.studyforce.stacktrace.controller;

import com.revature.studyforce.stacktrace.dto.TechnologyDTO;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles incoming HTTP requests related to {@link Technology} objects.
 * <p>
 *     Dispatches requests to the proper {@link TechnologyService} method.
 * </p>
 *
 * @author Tristan Panke
 */
@RestController
@RequestMapping("/stacktrace/technology")
@CrossOrigin(origins = "*") @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
public class TechnologyController {

  private final TechnologyService technologyService;

  /**
   * Constructs the controller using the given {@link TechnologyService}.
   *
   * @param technologyService The service this controller will use
   */
  @Autowired public TechnologyController (TechnologyService technologyService) {
    this.technologyService = technologyService;
  }

  /**
   * Gets all {@link Technology Technologies} using {@link TechnologyService#getAllTechnologies()}.
   *
   * @return A list of Technologies in data transfer object form
   */
  @GetMapping()
  public List<TechnologyDTO> getAllTechnologies() {
    return technologyService.getAllTechnologies();
  }

  /**
   * Adds a new {@link Technology} object using {@link TechnologyService#createNewTechnology(TechnologyDTO)}.
   *
   * @param technologyDTO The Technology to be added as a data transfer object
   * @return The data transfer representation of the newly added Technology object
   */
  @PostMapping
  public TechnologyDTO addNewTechnology(@RequestBody TechnologyDTO technologyDTO){
    return technologyService.createNewTechnology(technologyDTO);
  }

  /**
   * Deletes the {@link Technology} with the given ID using {@link TechnologyService#deleteTechnology(int)}.
   *
   * @param technologyId The ID of the Technology to be deleted
   * @return The deleted Technology object, now no longer persisted. May be null.
   */
  @DeleteMapping("/{technologyId}")
  public Technology deleteTechnology(@PathVariable int technologyId){
    return technologyService.deleteTechnology(technologyId);
  }

  /**
   * Updates the given {@link Technology} object using {@link TechnologyService#updateTechnology(TechnologyDTO)}.
   *
   * @param technologyDTO The Technology to be updated as DTO
   * @return The data transfer representation of the newly updated Technology object
   */
  @PutMapping
  public TechnologyDTO updateTechnology(@RequestBody TechnologyDTO technologyDTO){
    return technologyService.updateTechnology(technologyDTO);
  }
}
