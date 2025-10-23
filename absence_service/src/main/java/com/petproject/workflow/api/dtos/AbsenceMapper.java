package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Absence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AbsenceMapper {

    private final PolicyMapper policyMapper;

    @Autowired
    public AbsenceMapper(PolicyMapper policyMapper) {
        this.policyMapper = policyMapper;
    }

    public Absence mapToAbsence(AbsenceDto absenceDto) {
        return new Absence(
                absenceDto.getId(),
                absenceDto.getStatus(),
                absenceDto.getStartDate(),
                absenceDto.getEndDate(),
                absenceDto.getPlace(),
                absenceDto.getEmployee().getId(),
                absenceDto.getCreatedBy().getId(),
                policyMapper.mapToPolicy(absenceDto.getPolicy()),  // ← изменилось
                absenceDto.getCreatedAt(),
                absenceDto.getUpdatedAt()
        );
    }

    public AbsenceDto mapToAbsenceDto(
            Absence absence,
            EmployeeDto employee,
            EmployeeDto createdBy) {
        return new AbsenceDto(
                absence.getId(),
                absence.getStatus(),
                absence.getStartDate(),
                absence.getEndDate(),
                absence.getPlace(),
                employee,
                createdBy,
                policyMapper.mapToPolicyDto(absence.getPolicy()),  // ← изменилось
                absence.getCreatedAt(),
                absence.getUpdatedAt()
        );
    }
}