package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Car;
import com.petproject.workflow.store.entities.Journey;
import com.petproject.workflow.store.entities.Statement;
import com.petproject.workflow.store.entities.Trailer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatementJourneyMapper {

    private final CarMapper carMapper;
    private final TrailerMapper trailerMapper;

    public Statement mapToStatement(StatementDto statementDto) {
        Journey journey = null;
        if (statementDto.getJourney() != null) {
            journey = mapToJourney(statementDto.getJourney());
        }
        return new Statement(
                statementDto.getId(),
                statementDto.getLogist().getId(),
                statementDto.getData(),
                statementDto.getContactPhone(),
                statementDto.getDestinationTime(),
                statementDto.getDestinationAddress(),
                statementDto.getCreatedAt(),
                statementDto.getUpdatedAt(),
                journey
        );
    }

    public StatementDto mapToStatementDto(
            Statement statement,
            EmployeeDto logist,
            EmployeeDto driver) {
        StatementDto statementDto = mapToSimpleStatementDto(statement, logist);
        JourneyDto simpleJourneyDto = mapToSimpleJourneyDto(
                statement.getJourney(),
                driver);
        statementDto.setJourney(simpleJourneyDto);
        return statementDto;
    }

    public Journey mapToJourney(JourneyDto journeyDto) {
        Trailer trailer = null;
        if (journeyDto.getTrailer() != null) {
            trailer = new Trailer(journeyDto.getTrailer().getId());
        }
        Statement statement = null;
        if (journeyDto.getStatement() != null) {
            statement = mapToStatement(journeyDto.getStatement());
        }
        return new Journey(
                journeyDto.getId(),
                new Car(journeyDto.getCar().getId()),
                journeyDto.getDriver().getId(),
                journeyDto.getStatus(),
                journeyDto.getStartOdometer(),
                journeyDto.getEndOdometer(),
                journeyDto.getCreatedAt(),
                journeyDto.getConfirmedAt(),
                journeyDto.getStartedAt(),
                journeyDto.getFinishedAt(),
                journeyDto.getCanceledAt(),
                trailer,
                statement
        );
    }

    public JourneyDto mapToJourneyDto(
            Journey journey,
            EmployeeDto logist,
            EmployeeDto driver) {
        JourneyDto journeyDto = mapToSimpleJourneyDto(journey, driver);
        StatementDto simpleStatementDto = mapToSimpleStatementDto(journey.getStatement(), logist);
        journeyDto.setStatement(simpleStatementDto);
        return journeyDto;
    }

    private StatementDto mapToSimpleStatementDto(
            Statement statement,
            EmployeeDto logist) {
        return new StatementDto(
                statement.getId(),
                logist,
                statement.getData(),
                statement.getContactPhone(),
                statement.getDestinationTime(),
                statement.getDestinationAddress(),
                statement.getCreatedAt(),
                statement.getUpdatedAt(),
                null
        );
    }

    private JourneyDto mapToSimpleJourneyDto(
            Journey journey,
            EmployeeDto driver) {
        CarDto carDto = carMapper.mapToCarDto(journey.getCar());
        TrailerDto trailerDto = trailerMapper.mapToTrailerDto(journey.getTrailer());
        return new JourneyDto(
                journey.getId(),
                carDto,
                driver,
                journey.getStatus(),
                journey.getStartOdometer(),
                journey.getEndOdometer(),
                journey.getCreatedAt(),
                journey.getConfirmedAt(),
                journey.getStartedAt(),
                journey.getFinishedAt(),
                journey.getCanceledAt(),
                trailerDto,
                null
        );
    }
}
