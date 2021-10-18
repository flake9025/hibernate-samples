package fr.vvlabs.hibernate.sample.controller;

import fr.vvlabs.hibernate.sample.dto.VoitureDTO;
import fr.vvlabs.hibernate.sample.service.VoitureService;
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
@RequestMapping("/cars")
@RequiredArgsConstructor
public class VoitureController {

  private final VoitureService service;

  @Operation(summary = "Count all", description = "Count all cars")
  @GetMapping(value = "/count")
  public ResponseEntity<Long> countAll() {
    try {
      return ResponseEntity.ok(service.countAll());
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Find all", description = "Find all cars")
  @GetMapping
  public ResponseEntity<Page<VoitureDTO>> findAll(Pageable page) {
    try {
      return ResponseEntity.ok(service.findAll(page));
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Find", description = "Find a car")
  @GetMapping(value = "/{id}")
  public ResponseEntity<VoitureDTO> find(@PathVariable Integer id) {
    try {
      return ResponseEntity.of(Optional.of(service.findById(id)));
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Create", description = "Create a car")
  @PostMapping
  public ResponseEntity<VoitureDTO> create(@RequestBody VoitureDTO dto) {
    try {
      Integer newKey = service.create(dto);
      if (newKey == null) {
        return ResponseEntity.noContent().build();
      }
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newKey).toUri();
      return ResponseEntity.created(location).build();
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Update", description = "Update a car")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Integer> update(@PathVariable Integer id, @RequestBody VoitureDTO dto) {
    try {
      Integer savedKey = service.update(id, dto);
      if (savedKey == null) {
        return ResponseEntity.noContent().build();
      }
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
      return ResponseEntity.created(location).build();
    } catch (Exception e) {
      throw new InternalError(e.getMessage());
    }
  }

  @Operation(summary = "Delete", description = "Delete a car")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
    try {
      service.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.noContent().build();
    }
  }
}
