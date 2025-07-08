package com.petproject.workflow_server.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.petproject.workflow_server.store.entities.Department;

import java.io.IOException;

public class DepartmentSerializer extends JsonSerializer<Department> {

    @Override
    public void serialize(
            Department department,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", department.getId().toString());
        jsonGenerator.writeStringField("name", department.getName());
        jsonGenerator.writeEndObject();
    }
}
