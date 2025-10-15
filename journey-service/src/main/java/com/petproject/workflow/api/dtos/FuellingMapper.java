package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Car;
import com.petproject.workflow.store.entities.Fuelling;
import org.springframework.stereotype.Component;

@Component
public class FuellingMapper {

    private final CarMapper carMapper;

    public FuellingMapper(CarMapper carMapper) {
        this.carMapper = carMapper;
    }

    public Fuelling mapToFuelling(FuellingDto fuellingDto) {
        return new Fuelling(
                fuellingDto.getId(),
                fuellingDto.getDriver().getId(),
                fuellingDto.getOperator().getId(),
                new Car(fuellingDto.getCar().getId()),
                fuellingDto.getVolume(),
                fuellingDto.getCreatedAt()
        );
    }

    public FuellingDto mapToFuellingDto(
            Fuelling fuelling,
            EmployeeDto driver,
            EmployeeDto operator) {
        return new FuellingDto(
                fuelling.getId(),
                driver,
                operator,
                carMapper.mapToCarDto(fuelling.getCar()),
                fuelling.getVolume(),
                fuelling.getCreatedAt()
        );
    }
}
