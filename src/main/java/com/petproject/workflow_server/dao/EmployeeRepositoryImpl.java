package com.petproject.workflow_server.dao;

import com.petproject.workflow_server.entities.BusinessTrip;
import com.petproject.workflow_server.entities.Employee;
import com.petproject.workflow_server.entities.Vacation;
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
    public List<BusinessTrip> getAllBusinessTrips(UUID employeeId) {
        Employee employee = getEmployeeById(employeeId);
        return employee.getBusinessTrips();
    }

    @Override
    public BusinessTrip addBusinessTripToEmployee(UUID employeeId, BusinessTrip businessTrip) {
        Employee employee = getEmployeeById(employeeId);
        businessTrip.setId(UUID.randomUUID());
        employee.addBusinessTrip(businessTrip);
        entityManager.merge(employee);
        return businessTrip;
    }

    @Override
    public List<Vacation> getAllVacations(UUID employeeId) {
        Employee employee = getEmployeeById(employeeId);
        return employee.getVacations();
    }

    @Override
    public Vacation addVacationToEmployee(UUID employeeId, Vacation vacation) {
        Employee employee = getEmployeeById(employeeId);
        vacation.setId(UUID.randomUUID());
        employee.addVacation(vacation);
        entityManager.merge(employee);
        return vacation;
    }

    @Override
    public List<Employee> findEmployeesByPatternName(String patternName) {
        Query query = entityManager.createQuery("from Employee where name like :patternName");
        query.setParameter("patternName", "%" + patternName + "%");
        return query.getResultList();
    }
}
