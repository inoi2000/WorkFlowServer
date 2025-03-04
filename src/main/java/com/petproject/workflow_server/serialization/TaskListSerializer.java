package com.petproject.workflow_server.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.petproject.workflow_server.entities.Task;

import java.io.IOException;
import java.util.List;

public class TaskListSerializer extends JsonSerializer<List<Task>> {

    @Override
    public void serialize(
            List<Task> tasks,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Task task : tasks) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", task.getId().toString());
            jsonGenerator.writeStringField("description", task.getDescription());
            jsonGenerator.writeStringField("status", task.getStatus().toString());

            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
