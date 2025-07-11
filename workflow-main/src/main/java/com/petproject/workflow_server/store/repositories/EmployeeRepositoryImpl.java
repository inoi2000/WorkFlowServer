package com.petproject.workflow_server.store.repositories;

import com.petproject.workflow_server.store.entities.Absence;
import com.petproject.workflow_server.store.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee getEmployeeById(UUID id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        Query query = entityManager.createQuery("from Employee");
        return query.getResultList();
    }

    @Override
    public void deleteEmployee(UUID id) {
        Query query = entityManager.createQuery("delete from Employee where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(UUID.randomUUID());
            entityManager.persist(employee);
        } else {
            entityManager.merge(employee);
        }
        return employee;
    }

    @Override
    public List<Absence> getAllAbsences(UUID employeeId) {
        Employee employee = getEmployeeById(employeeId);
        return employee.getAbsences();
    }

    @Override
    public Absence addAbsenceToEmployee(UUID employeeId, Absence absence) {
        Employee employee = getEmployeeById(employeeId);
        absence.setId(UUID.randomUUID());
        employee.addAbsence(absence);
        entityManager.merge(employee);
        return absence;
    }

    @Override
    public List<Employee> findEmployeesByPatternName(String patternName) {
        Query query = entityManager.createQuery("from Employee where name like :patternName");
        query.setParameter("patternName", "%" + patternName + "%");
        return query.getResultList();
    }
}
