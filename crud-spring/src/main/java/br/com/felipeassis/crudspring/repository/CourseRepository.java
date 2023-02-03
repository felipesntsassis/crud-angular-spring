package br.com.felipeassis.crudspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.felipeassis.crudspring.model.Course;

public interface CourseRepository extends JpaRepository<Course,Long> {
    
}
