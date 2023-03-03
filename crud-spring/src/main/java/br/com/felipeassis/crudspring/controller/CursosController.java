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
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeassis.crudspring.model.Course;
import br.com.felipeassis.crudspring.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/courses")
@Validated
public class CursosController {
    
    private final CourseService service;

    CursosController(CourseService service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
        if (service.delete(id))
            return ResponseEntity.noContent().<Void>build();

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
        return service.findById(id)
            .map(foundRecord -> ResponseEntity.ok().body(foundRecord))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Course> list() {
        return service.list();
    }

    @PostMapping
    public ResponseEntity<Course>create(@RequestBody @Valid Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course>update(@PathVariable @NotNull @Positive Long id, @RequestBody  @Valid Course course) {
        return service.update(id, course)
            .map(updatedRecord ->  ResponseEntity.ok().body(updatedRecord))
            .orElse(ResponseEntity.notFound().build());
    }

}
