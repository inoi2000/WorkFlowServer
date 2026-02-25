package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.DateTimeUpdateDto;
import com.petproject.workflow.api.exceptions.InvalidTimeException;
import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.JourneyDto;
import com.petproject.workflow.api.dtos.StatementJourneyMapper;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.entities.Journey;
import com.petproject.workflow.store.entities.JourneyStatus;
import com.petproject.workflow.store.repositories.JourneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JourneyService {

    private final JourneyRepository journeyRepository;
    private final StatementJourneyMapper statementJourneyMapper;
    private final EmployeeServiceClient employeeServiceClient;
    private final EmployeeHelper employeeHelper;

    public List<JourneyDto> getAllJourneys() {
        List<Journey> journeys = journeyRepository.findAll();
        return mapJourneysToDto(journeys);
    }

    public JourneyDto getJourneyById(UUID journeyId) throws NotFoundIdException {
        Journey journey = journeyRepository
                .findById(journeyId)
                .orElseThrow(() -> new NotFoundIdException("Journey with id " + journeyId + " not found"));
        Set<UUID> employeesIds = employeeHelper.collectEmployeeUUIDtoSet(journey);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);

        return statementJourneyMapper.mapToJourneyDto(
                journey,
                employeesMap.get(journey.getStatement().getLogistId()),
                employeesMap.get(journey.getDriverId())
        );
    }

    public List<JourneyDto> getJourneysByDriverId(UUID driverId) {
        List<Journey> journeys = journeyRepository.findByDriverId(driverId);
        return mapJourneysToDto(journeys);
    }

    public List<JourneyDto> getJourneysByCarId(UUID driverId) {
        List<Journey> journeys = journeyRepository.findByCarId(driverId);
        return mapJourneysToDto(journeys);
    }

    private List<JourneyDto> mapJourneysToDto(List<Journey> journeys) {
        Set<UUID> employeesIds = employeeHelper.collectEmployeesUUIDFromJourneysIterabletoSet(journeys);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);
        return journeys.stream().map(journey ->
                statementJourneyMapper.mapToJourneyDto(
                        journey,
                        employeesMap.get(journey.getStatement().getLogistId()),
                        employeesMap.get(journey.getDriverId()))).toList();
    }

    public Optional<JourneyDto> changeJourneyStatus(JourneyStatus status, DateTimeUpdateDto dateTimeUpdateDto) {
        return journeyRepository.findById(dateTimeUpdateDto.getObjectId())
                .map(journey -> {
                    journey.setStatus(status);
                    switch (status) {
                        case NEW:
                            if (journey.getCreatedAt() != null) {
                                throw new IllegalStateException();
                            }
                            journey.setCreatedAt(dateTimeUpdateDto.getUpdatedTime());
                            break;
                        case CONFIRMED:
                            if (journey.getConfirmedAt() != null) {
                                throw new IllegalStateException();
                            }
                            journey.setConfirmedAt(dateTimeUpdateDto.getUpdatedTime());
                            break;
                        case STARTED:
                            if (journey.getStartedAt() != null) {
                                throw new IllegalStateException();
                            }
                            journey.setStartedAt(dateTimeUpdateDto.getUpdatedTime());
                            break;
                        case FINISHED:
                            if (journey.getFinishedAt() != null) {
                                throw new IllegalStateException();
                            }
                            journey.setFinishedAt(dateTimeUpdateDto.getUpdatedTime());
                            break;
                        case CANCELED:
                            if (journey.getCanceledAt() != null) {
                                throw new IllegalStateException();
                            }
                            journey.setFinishedAt(dateTimeUpdateDto.getUpdatedTime());
                            break;
                    }
                    journeyRepository.save(journey);
                    return mapJourneyToDtoObject(journey);
                });
    }


    private JourneyDto mapJourneyToDtoObject(Journey journey) {
        Set<UUID> employeesIds = employeeHelper.collectEmployeeUUIDtoSet(journey);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);
        return statementJourneyMapper.mapToJourneyDto(
                journey,
                employeesMap.get(journey.getStatement().getLogistId()),
                employeesMap.get(journey.getDriverId())
        );
    }

    private List<JourneyDto> mapJourneysToDtoList(List<Journey> journeys) {
        List<JourneyDto> journeyDtos = new ArrayList<>();
        Set<UUID> employeesIds = employeeHelper.collectEmployeesUUIDFromJourneysIterabletoSet(journeys);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);
        for (Journey journey : journeys) {
            journeyDtos.add(statementJourneyMapper.mapToJourneyDto(
                    journey,
                    employeesMap.get(journey.getStatement().getLogistId()),
                    employeesMap.get(journey.getDriverId())
            ));
        }
        return journeyDtos;
    }

    // Валидация времени от клиента
    private void validateClientTime(LocalDateTime clientTime) throws InvalidTimeException {
        LocalDateTime serverTime = LocalDateTime.now();
        Duration difference = Duration.between(clientTime, serverTime);

        if (Math.abs(difference.toHours()) > 24) {
            throw new InvalidTimeException("Client time differs too much from server time");
        }
    }
}
