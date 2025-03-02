package com.petproject.workflow_server.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.petproject.workflow_server.entities.Employee;

import java.io.IOException;

public class EmployeeSerializer extends JsonSerializer<Employee> {
    @Override
    public void serialize(
            Employee employee,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", employee.getId().toString());
        jsonGenerator.writeStringField("name", employee.getName());
        jsonGenerator.writeEndObject();
    }
}
