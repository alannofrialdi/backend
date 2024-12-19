package api.todolist.converter;

import api.todolist.model.Task.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return (status != null) ? status.name() : null;
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return (dbData != null) ? Status.fromString(dbData) : null;
    }
}
