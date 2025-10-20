package com.petproject.workflow.api.dtos;

import com.petproject.workflow.store.Absence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbsenceMapper {

    private final AbsencePolicyMapper absencePolicyMapper;

    @Autowired
    public AbsenceMapper(AbsencePolicyMapper absencePolicyMapper) {
        this.absencePolicyMapper = absencePolicyMapper;
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
                absencePolicyMapper.mapToAbsencePolicy(absenceDto.getPolicy()),
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
                absencePolicyMapper.mapToAbsencePolicyDto(absence.getPolicy()),
                absence.getCreatedAt(),
                absence.getUpdatedAt()
        );
    }
}
