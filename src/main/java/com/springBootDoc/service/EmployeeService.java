package com.springBootDoc.service;

import java.util.List;

import com.springBootDoc.entity.Employee;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findOne(String id);

    Employee create(Employee emp);

    Employee update(String id, Employee emp);

    void delete(String id);
}