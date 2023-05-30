package br.com.felipeassis.crudspring.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.felipeassis.crudspring.dto.CourseDTO;
import br.com.felipeassis.crudspring.dto.LessonDTO;
import br.com.felipeassis.crudspring.enums.Category;
import br.com.felipeassis.crudspring.model.Course;

@Component
public class CourseMapper {
    
    public CourseDTO toDTO(Course course) {
        if (course == null) return null;

        List<LessonDTO> lessons = course.getLessons()
            .stream()
            .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
            .collect(Collectors.toList());

        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), lessons);
    }

    public Course toEntity(CourseDTO dto) {
        if(dto == null) return null;

        Course course = new Course();

        if(dto.id() != null) course.setId(dto.id());

        course.setName(dto.name());
        course.setCategory(convertCategoryValue(dto.category()));

        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null)
            return null;

        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
}
