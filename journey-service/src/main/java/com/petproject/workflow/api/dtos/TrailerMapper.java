package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Trailer;
import org.springframework.stereotype.Component;

@Component
public class TrailerMapper {

    public TrailerDto mapToTrailerDto(Trailer trailer) {
        return new TrailerDto(
                trailer.getId(),
                trailer.getBrand(),
                trailer.getLicensePlate(),
                trailer.getVolumeLiter(),
                trailer.getMaterial()
        );
    }
}
