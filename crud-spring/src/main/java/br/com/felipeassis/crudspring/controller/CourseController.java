package br.com.felipeassis.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeassis.crudspring.dto.CourseDTO;
import br.com.felipeassis.crudspring.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/courses")
@Validated
public class CourseController {
    
    private final CourseService service;

    CourseController(CourseService service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<CourseDTO> list() {
        return service.list();
    }

    @PostMapping
    public ResponseEntity<CourseDTO>create(@RequestBody @Valid CourseDTO course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(course));
    }

    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody  @Valid CourseDTO course) {
        return service.update(id, course);
    }

}
