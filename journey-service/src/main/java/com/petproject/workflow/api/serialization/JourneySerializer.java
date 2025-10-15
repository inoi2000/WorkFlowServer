package com.petproject.workflow.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.petproject.workflow.api.dtos.JourneyDto;

import java.io.IOException;

public class JourneySerializer extends JsonSerializer<JourneyDto> {

    @Override
    public void serialize(
            JourneyDto journey,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", journey.getId().toString());
        jsonGenerator.writeObjectField("car", journey.getCar());
        jsonGenerator.writeObjectField("driver", journey.getDriver());
        jsonGenerator.writeStringField("status", journey.getStatus().toString());
        jsonGenerator.writeStringField("created_at", journey.getCreatedAt().toString());
        if (journey.getStartOdometer() != 0D)
            jsonGenerator.writeNumberField("start_odometer", journey.getStartOdometer());
        if (journey.getEndOdometer() != 0D)
            jsonGenerator.writeNumberField("end_odometer", journey.getEndOdometer());
        if (journey.getConfirmedAt() != null)
            jsonGenerator.writeStringField("confirmed_at", journey.getConfirmedAt().toString());
        if (journey.getStartedAt() != null)
            jsonGenerator.writeStringField("started_at", journey.getStartedAt().toString());
        if (journey.getFinishedAt() != null)
            jsonGenerator.writeStringField("finished_at", journey.getFinishedAt().toString());
        if (journey.getCanceledAt() != null)
            jsonGenerator.writeStringField("canceled_at", journey.getCanceledAt().toString());
        jsonGenerator.writeEndObject();
    }
}
