package com.petproject.workflow.api.services;

import com.petproject.workflow.api.InvalidTimeException;
import com.petproject.workflow.api.dtos.StatementJourneyMapper;
import com.petproject.workflow.store.entities.Journey;
import com.petproject.workflow.store.repositories.JourneyRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;

    private final StatementJourneyMapper statementJourneyMapper;

    public JourneyService(
            JourneyRepository journeyRepository,
            StatementJourneyMapper statementJourneyMapper) {
        this.journeyRepository = journeyRepository;
        this.statementJourneyMapper = statementJourneyMapper;
    }

    public List<Journey> getAllJourneys() {
        List<Journey> journeys = journeyRepository.findAll();
        journeys.stream().forEach(journey -> {
//            StatementDto statementDto = statementMapper.mapToStatementDto()
        });


        return null;
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
