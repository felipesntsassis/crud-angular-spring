package br.com.felipeassis.crudspring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

}
