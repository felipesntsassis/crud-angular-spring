package br.com.felipeassis.crudspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.felipeassis.crudspring.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    
}
