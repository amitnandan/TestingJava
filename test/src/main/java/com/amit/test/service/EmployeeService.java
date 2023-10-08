package com.amit.test.service;


import com.amit.test.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployee();

    Optional<Employee> getEmployeeById( long employeeId);

    Employee updateEmployee( Employee updatedEmployee);

    void deleteEmployee( long employeeId);

}
