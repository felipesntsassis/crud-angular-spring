package br.com.felipeassis.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipeassis.crudspring.model.Course;
import br.com.felipeassis.crudspring.repository.CourseRepository;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CursosController {
    
    private final CourseRepository repository;

    @GetMapping
    public List<Course> list() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Course>create(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(course));
    }

}
