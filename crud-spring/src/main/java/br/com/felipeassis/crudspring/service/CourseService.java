package br.com.felipeassis.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.felipeassis.crudspring.dto.CourseDTO;
import br.com.felipeassis.crudspring.dto.mapper.CourseMapper;
import br.com.felipeassis.crudspring.exception.RecordNotFoundException;
import br.com.felipeassis.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper mapper;

    CourseService(CourseRepository repository, CourseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return mapper.toDTO(repository.save(mapper.toEntity(course)));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id).map(mapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public List<CourseDTO> list() {
        return repository.findAll()
            .stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO course) {
        return repository.findById(id)
            .map(recordFound -> {
                recordFound.setName(course.name());
                recordFound.setCategory(course.category());

                return mapper.toDTO(repository.save(recordFound));
            })
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

}
