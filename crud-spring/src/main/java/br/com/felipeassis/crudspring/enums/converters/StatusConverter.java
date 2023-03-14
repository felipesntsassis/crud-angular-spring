package br.com.felipeassis.crudspring.enums.converters;

import java.util.stream.Stream;

import br.com.felipeassis.crudspring.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status,String> {
    
    @Override
    public String convertToDatabaseColumn(Status stauts) {
        if (stauts == null)
            return null;

        return stauts.getValue();
    }

    @Override
    public Status convertToEntityAttribute(String value) {
        if (value == null)
            return null;

        return Stream.of(Status.values())
            .filter(c -> c.getValue().equals(value))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

}
