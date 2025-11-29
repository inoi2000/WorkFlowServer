package com.petproject.workflow.store.repositories;

import com.petproject.workflow.store.entities.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<Employee, UUID> {
    Optional<Employee> findById(UUID id);

    List<Employee> findAllById(Iterable<UUID> ids);

    @Query("SELECT e FROM Employee e WHERE e.position.level < " +
            "(SELECT emp.position.level FROM Employee emp WHERE emp.id = :currentEmployeeId)")
    List<Employee> findAllEmployeesWithLowerPositionLevel(@Param("currentEmployeeId") UUID currentEmployeeId);

    @Query("SELECT e FROM Employee e JOIN e.position p WHERE p.name = :positionName")
    List<Employee> findAllEmployeesWithPositionName(@Param("positionName") String positionName);

    @Query("SELECT e FROM Employee e LEFT JOIN e.position p " +
            "WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchString, '%'))")
    List<Employee> findAllEmployeesByNameOrPositionName(@Param("searchString") String searchString);
}