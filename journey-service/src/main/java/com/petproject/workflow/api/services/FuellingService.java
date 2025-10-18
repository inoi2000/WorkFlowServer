package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.FuellingDto;
import com.petproject.workflow.api.dtos.FuellingMapper;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.entities.Fuelling;
import com.petproject.workflow.store.repositories.FuellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FuellingService {

    private final FuellingRepository fuellingRepository;
    private final EmployeeServiceClient employeeServiceClient;
    private final FuellingMapper fuellingMapper;
    private final EmployeeHelper employeeHelper;

    public List<FuellingDto> getAllFuellings() {
        List<Fuelling> fuellings = fuellingRepository.findAll();
        return mapFuellingsToDto(fuellings);
    }

    public FuellingDto getFuellingById(UUID fuellingId) throws NotFoundIdException {
        Fuelling fuelling = fuellingRepository
                .findById(fuellingId)
                .orElseThrow(() -> new NotFoundIdException("Fuelling with id " + fuellingId + " not found"));
        Set<UUID> employeesIds = employeeHelper.collectEmployeeUUIDtoSet(fuelling);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);

        return fuellingMapper.mapToFuellingDto(
                fuelling,
                employeesMap.get(fuelling.getDriverId()),
                employeesMap.get(fuelling.getOperatorId())
        );
    }

    private List<FuellingDto> mapFuellingsToDto(List<Fuelling> fuellings) {
        Set<UUID> employeesIds = employeeHelper.collectEmployeeUUIDFromFuellingsIterabletoSet(fuellings);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);
        return fuellings.stream().map(fuelling ->
                fuellingMapper.mapToFuellingDto(
                        fuelling,
                        employeesMap.get(fuelling.getDriverId()),
                        employeesMap.get(fuelling.getOperatorId()))).toList();
    }
}
