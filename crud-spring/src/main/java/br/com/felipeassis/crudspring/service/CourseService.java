package br.com.felipeassis.crudspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.felipeassis.crudspring.model.Course;
import br.com.felipeassis.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository repository;

    CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course create(@Valid Course course) {
        return repository.save(course);
    }

    public boolean delete(@NotNull @Positive Long id) {
        return repository.findById(id)
            .map(recordFound -> {
                repository.delete(recordFound);
                return true;
            })
            .orElse(false);
    }

    public Optional<Course> findById(@NotNull @Positive Long id) {
        return repository.findById(id);
    }
    
    public List<Course> list() {
        return repository.findAll();
    }

    public Optional<Course> update(@NotNull @Positive Long id, @Valid Course course) {
        return repository.findById(id)
            .map(recordFound -> {
                recordFound.setName(course.getName());
                recordFound.setCategory(course.getCategory());

                return repository.save(recordFound);
            });
    }
}
