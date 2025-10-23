package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.AbsenceDto;
import com.petproject.workflow.api.dtos.AbsenceMapper;
import com.petproject.workflow.api.dtos.PolicyMapper;
import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.store.Absence;
import com.petproject.workflow.store.PolicyRepository;
import com.petproject.workflow.store.AbsenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final PolicyRepository policyRepository;
    private final EmployeeServiceClient employeeServiceClient;
    private final EmployeeHelper employeeHelper;
    private final AbsenceMapper absenceMapper;
    private final PolicyMapper policyMapper;


    public AbsenceDto getAbsenceById(UUID absenceId) throws NotFoundIdException {
        Absence absence = absenceRepository
                .findById(absenceId)
                .orElseThrow(() -> new NotFoundIdException("Absence with id " + absenceId + " not found"));
        Set<UUID> employeesIds = employeeHelper.collectEmployeeUUIDtoSet(absence);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);

        return absenceMapper.mapToAbsenceDto(
                absence,
                employeesMap.get(absence.getEmployeeId()),
                employeesMap.get(absence.getCreatedById())
        );
    }

    public Iterable<AbsenceDto> getAbsenceByEmployeeId(UUID employeeId) {
        List<Absence> absences = absenceRepository.findAllByEmployeeId(employeeId);
        Set<UUID> employeesIds = employeeHelper.collectEmployeeUUIDtoSet(absences);
        Iterable<EmployeeDto> employeesIterable = employeeServiceClient.getEmployeesByIds(employeesIds);
        Map<UUID, EmployeeDto> employeesMap = employeeHelper.toMap(employeesIterable);

        return absences.stream().map(absence ->
                absenceMapper.mapToAbsenceDto(
                        absence,
                        employeesMap.get(absence.getEmployeeId()),
                        employeesMap.get(absence.getCreatedById())
                )).toList();
    }
}
