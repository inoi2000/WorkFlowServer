package com.petproject.workflow.api;

import com.petproject.workflow.store.Absence;
import com.petproject.workflow.store.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/absence", produces = "application/json")
public class AbsenceController {
    private final AbsenceRepository absenceRepository;

    @Autowired
    public AbsenceController(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable UUID id) {
        Optional<Absence> optionalAbsence = absenceRepository.findById(id);
        return optionalAbsence
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public Iterable<Absence> getAbsenceByEmployeeId(@PathVariable UUID employeeId) {
        Iterable<Absence> absences = absenceRepository.findAllByEmployeeId(employeeId);
        return absences;
    }
}
