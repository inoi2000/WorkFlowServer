package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapToCar(CarDto carDto) {
        return new Car(
                carDto.getId(),
                carDto.getBrand(),
                carDto.getModel(),
                carDto.getLicensePlate(),
                carDto.getVin(),
                carDto.getYear(),
                carDto.getColor(),
                carDto.getOdometer(),
                carDto.getStatus()
        );
    }

    public CarDto mapToCarDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getLicensePlate(),
                car.getVin(),
                car.getYear(),
                car.getColor(),
                car.getOdometer(),
                car.getStatus()
        );
    }
}
