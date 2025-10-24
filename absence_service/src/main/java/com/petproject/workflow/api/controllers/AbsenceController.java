package com.petproject.workflow.api.controllers;

import com.petproject.workflow.api.dtos.AbsenceDto;
import com.petproject.workflow.api.exceptions.NotFoundIdException;
import com.petproject.workflow.api.services.AbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/absence", produces = "application/json")
@RequiredArgsConstructor
public class AbsenceController {

    private final AbsenceService absenceService;

    @GetMapping("/{absenceId}")
    public AbsenceDto getAbsenceById(@PathVariable UUID absenceId) throws NotFoundIdException {
        return absenceService.getAbsenceById(absenceId);
    }

    @GetMapping("/employee/{employeeId}")
    public Iterable<AbsenceDto> getAbsenceByEmployeeId(@PathVariable UUID employeeId) {
        return absenceService.getAbsenceByEmployeeId(employeeId);
    }
}
