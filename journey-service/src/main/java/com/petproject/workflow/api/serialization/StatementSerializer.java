package com.petproject.workflow.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.petproject.workflow.api.dtos.StatementDto;

import java.io.IOException;

public class StatementSerializer extends JsonSerializer<StatementDto> {

    @Override
    public void serialize(
            StatementDto statement,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", statement.getId().toString());
        jsonGenerator.writeObjectField("logistician", statement.getLogistician());
        jsonGenerator.writeStringField("data", statement.getData());
        jsonGenerator.writeStringField("address", statement.getAddress());
        jsonGenerator.writeStringField("created_at", statement.getCreatedAt().toString());
        jsonGenerator.writeStringField("updated_at", statement.getUpdatedAt().toString());
        jsonGenerator.writeEndObject();
    }
}
