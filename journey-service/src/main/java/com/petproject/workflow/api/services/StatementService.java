package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.dtos.StatementDto;
import com.petproject.workflow.api.dtos.StatementJourneyMapper;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.entities.Statement;
import com.petproject.workflow.store.repositories.StatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementService {

    private final StatementRepository statementRepository;
    private final EmployeeServiceClient employeeServiceClient;
    private final StatementJourneyMapper statementJourneyMapper;
    private final EmployeeHelper employeeHelper;

    public List<StatementDto> getAllStatements() {
        List<Statement> statements = statementRepository.findAll();
        return mapStatementsToDto(statements);
    }

    public StatementDto getStatementById(UUID statementId) throws NotFoundIdException {
        Statement statement = statementRepository
                .findById(statementId)
                .orElseThrow(() -> new NotFoundIdException("Statement with id " + statementId + " not found"));
        Set<UUID> employeesIds = employeeHelper.collectEmployeeUUIDtoSet(statement);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);

        return statementJourneyMapper.mapToStatementDto(
                statement,
                employeesMap.get(statement.getLogistId()),
                employeesMap.get(statement.getJourney().getDriverId())
        );
    }

    private List<StatementDto> mapStatementsToDto(List<Statement> statements) {
        Set<UUID> employeesIds = employeeHelper.collectEmployeesUUIDFromStatementsIterabletoSet(statements);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);
        return statements.stream().map(statement ->
                statementJourneyMapper.mapToStatementDto(
                        statement,
                        employeesMap.get(statement.getLogistId()),
                        employeesMap.get(statement.getJourney().getDriverId()))).toList();
    }
}
