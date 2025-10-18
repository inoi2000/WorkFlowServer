package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.entities.Car;
import com.petproject.workflow.store.entities.Journey;
import com.petproject.workflow.store.entities.Statement;
import com.petproject.workflow.store.entities.Trailer;
import org.springframework.stereotype.Component;

@Component
public class StatementJourneyMapper {

    private final CarMapper carMapper;
    private final TrailerMapper trailerMapper;

    public StatementJourneyMapper(
            CarMapper carMapper,
            TrailerMapper trailerMapper) {
        this.carMapper = carMapper;
        this.trailerMapper = trailerMapper;
    }

    public Statement mapToStatement(StatementDto statementDto) {
        return new Statement(
                statementDto.getId(),
                statementDto.getLogist().getId(),
                statementDto.getData(),
                statementDto.getContactPhone(),
                statementDto.getDestinationTime(),
                statementDto.getDestinationAddress(),
                statementDto.getCreatedAt(),
                statementDto.getUpdatedAt(),
                null
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
                new Statement(journeyDto.getStatement().getId())
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
