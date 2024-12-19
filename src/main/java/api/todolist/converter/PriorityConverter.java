package api.todolist.converter;

import api.todolist.model.Task.Priority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PriorityConverter implements AttributeConverter<Priority, String> {

    @Override
    public String convertToDatabaseColumn(Priority priority) {
        return (priority != null) ? priority.name() : null;
    }

    @Override
    public Priority convertToEntityAttribute(String dbData) {
        return (dbData != null) ? Priority.fromString(dbData) : null;
    }
}
