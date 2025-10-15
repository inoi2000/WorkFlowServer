package com.petproject.workflow.api.services;

import com.petproject.workflow.api.InvalidTimeException;
import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.JourneyDto;
import com.petproject.workflow.api.dtos.StatementJourneyMapper;
import com.petproject.workflow.store.entities.Journey;
import com.petproject.workflow.store.repositories.JourneyRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;
    private final EmployeeServiceClient employeeServiceClient;
    private final StatementJourneyMapper statementJourneyMapper;
    private final EmployeeHelper employeeHelper;

    public JourneyService(
            JourneyRepository journeyRepository,
            EmployeeServiceClient employeeServiceClient,
            StatementJourneyMapper statementJourneyMapper,
            EmployeeHelper employeeHelper) {
        this.journeyRepository = journeyRepository;
        this.employeeServiceClient = employeeServiceClient;
        this.statementJourneyMapper = statementJourneyMapper;
        this.employeeHelper = employeeHelper;
    }

    public List<JourneyDto> getAllJourneys() {
        List<Journey> journeys = journeyRepository.findAll();
        Set<UUID> employeesIds = employeeHelper.collectEmployeesUUIDFromJourneysIterabletoSet(journeys);

        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);

        return journeys.stream().map(journey ->
                statementJourneyMapper.mapToJourneyDto(
                        journey,
                        employeesMap.get(journey.getStatement().getLogistId()),
                        employeesMap.get(journey.getDriverId()))).toList();
    }


    // Валидация времени от клиента
    public void validateClientTime(LocalDateTime clientTime) throws InvalidTimeException {
        LocalDateTime serverTime = LocalDateTime.now();
        Duration difference = Duration.between(clientTime, serverTime);

        if (Math.abs(difference.toHours()) > 24) {
            throw new InvalidTimeException("Client time differs too much from server time");
        }
    }
}
