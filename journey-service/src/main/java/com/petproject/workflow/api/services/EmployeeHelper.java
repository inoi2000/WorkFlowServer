package com.petproject.workflow.api.services;

import com.petproject.workflow.api.dtos.EmployeeDto;
import com.petproject.workflow.store.entities.Fuelling;
import com.petproject.workflow.store.entities.Journey;
import com.petproject.workflow.store.entities.Statement;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmployeeHelper {

    public Set<UUID> collectEmployeeUUIDtoSet(Journey journey) {
        Set<UUID> set = new HashSet<>();
        set.add(journey.getDriverId());
        set.add(journey.getStatement().getLogistId());
        return set;
    }

    public Set<UUID> collectEmployeeUUIDtoSet(Statement statement) {
        Set<UUID> set = new HashSet<>();
        set.add(statement.getLogistId());
        set.add(statement.getJourney().getDriverId());
        return set;
    }

    public Set<UUID> collectEmployeeUUIDtoSet(Fuelling fuelling) {
        Set<UUID> set = new HashSet<>();
        set.add(fuelling.getDriverId());
        set.add(fuelling.getOperatorId());
        return set;
    }

    public Set<UUID> collectEmployeesUUIDFromJourneysIterabletoSet(Iterable<Journey> journeys) {
        Set<UUID> set = new HashSet<>();
        for (Journey journey : journeys) {
            set.add(journey.getDriverId());
            set.add(journey.getStatement().getLogistId());
        }
        return set;
    }

    public Set<UUID> collectEmployeesUUIDFromStatementsIterabletoSet(Iterable<Statement> statements) {
        Set<UUID> set = new HashSet<>();
        for (Statement statement : statements) {
            set.add(statement.getLogistId());
            set.add(statement.getJourney().getDriverId());
        }
        return set;
    }

    public Set<UUID> collectEmployeeUUIDFromFuellingsIterabletoSet(Iterable<Fuelling> fuellings) {
        Set<UUID> set = new HashSet<>();
        for (Fuelling fuelling : fuellings) {
            set.add(fuelling.getDriverId());
            set.add(fuelling.getOperatorId());
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
