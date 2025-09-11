package com.petproject.workflow.store;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, UUID> {
    Optional<Employee> findById(UUID id);

    List<Employee> findAllById(Iterable<UUID> ids);

    @Query("SELECT e FROM Employee e WHERE e.position.level < (SELECT emp.position.level FROM Employee emp WHERE emp.id = :currentEmployeeId)")
    List<Employee> findAllEmployeesWithLowerPositionLevel(@Param("currentEmployeeId") UUID currentEmployeeId);
}