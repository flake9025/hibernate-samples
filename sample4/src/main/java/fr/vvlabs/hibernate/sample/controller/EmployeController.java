package fr.vvlabs.hibernate.sample.controller;

import fr.vvlabs.hibernate.sample.dto.EmployeDTO;
import fr.vvlabs.hibernate.sample.model.Employe.EmployeId;
import fr.vvlabs.hibernate.sample.service.EmployeService;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeController {

  private final EmployeService service;

  @Operation(summary = "Count all", description = "Count all employees")
  @GetMapping(value = "/count")
  public ResponseEntity<Long> countAll() {
    try {
      return ResponseEntity.ok(service.countAll());
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Find all", description = "Find all employees")
  @GetMapping
  public ResponseEntity<Page<EmployeDTO>> findAll(Pageable page) {
    try {
      return ResponseEntity.ok(service.findAll(page));
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Find", description = "Find a employee")
  @GetMapping(value = "/{employerId}/{personId}")
  public ResponseEntity<EmployeDTO> find(
      @PathVariable("employerId") Integer employerId,
      @PathVariable("personId") Integer personId) {
    try {
      return ResponseEntity.of(Optional.of(service.findById(employerId, personId)));
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Create", description = "Create an employee")
  @PostMapping
  public ResponseEntity<EmployeDTO> create(@RequestBody EmployeDTO dto) {
    try {
      EmployeId newKey = service.create(dto);
      if (newKey == null) {
        return ResponseEntity.noContent().build();
      }
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newKey).toUri();
      return ResponseEntity.created(location).build();
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Update", description = "Update an employee")
  @PutMapping(value = "/{employerId}/{personId}")
  public ResponseEntity<Integer> update(
      @PathVariable("employerId") Integer employerId,
      @PathVariable("personId") Integer personId,
      @RequestBody EmployeDTO dto) {
    try {
      EmployeId savedKey = service.update(employerId, personId, dto);
      if (savedKey == null) {
        return ResponseEntity.noContent().build();
      }
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.created(location).build();
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Delete", description = "Delete an employee")
  @DeleteMapping(value = "/{employerId}/{personId}")
  public ResponseEntity<Void> deleteById(
      @PathVariable("employerId") Integer employerId,
      @PathVariable("personId") Integer personId) {
    try {
      service.deleteById(employerId, personId);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.noContent().build();
    }
  }


}
