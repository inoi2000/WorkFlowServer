package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.store.Absence;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmployeeHelper {

    public Set<UUID> collectEmployeeUUIDtoSet(Absence absence) {
        Set<UUID> set = new HashSet<>();
        set.add(absence.getEmployeeId());
        set.add(absence.getCreatedById());
        return set;
    }

    public Set<UUID> collectEmployeeUUIDtoSet(Iterable<Absence> absences) {
        Set<UUID> set = new HashSet<>();
        for (Absence absence : absences) {
            set.add(absence.getEmployeeId());
            set.add(absence.getCreatedById());
        }
        return set;
    }

    public Map<UUID, EmployeeDto> toMap(Iterable<EmployeeDto> employees) {
        Map<UUID, EmployeeDto> map = new HashMap<>();
        for (EmployeeDto employee : employees) {
            map.put(employee.getId(), employee);
        }
        return map;
    }
}
