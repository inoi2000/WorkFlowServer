package com.petproject.workflow_server.store.repositories;

import com.petproject.workflow_server.store.entities.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Department getDepartmentById(UUID id) {
        return entityManager.find(Department.class, id);
    }

    @Override
    public List<Department> getAllDepartments() {
        Query query = entityManager.createQuery("from Department");
        return query.getResultList();
    }

    @Override
    public void deleteDepartment(UUID id) {
        Query query = entityManager.createQuery("delete from Department where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void createDepartment(Department department) {
        department.setId(UUID.randomUUID());
        entityManager.persist(department);
    }

    @Override
    public void updateDepartment(Department department) {
        entityManager.merge(department);
    }

    @Override
    public void addEmployeeToDepartment(UUID departmentId, UUID employeeId) {
        Department department = entityManager.find(Department.class, departmentId);
        Query query = entityManager.createQuery(
                "update Employee set department = :department where id = :id");
        query.setParameter("department", department);
        query.setParameter("id", employeeId);
        query.executeUpdate();
    }

    @Override
    public List<Department> findDepartmentsByPatternName(String patternName) {
        Query query = entityManager.createQuery("from Department where name like :patternName");
        query.setParameter("patternName", "%" + patternName + "%");
        return query.getResultList();
    }
}
