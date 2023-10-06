package com.amit.test.service.impl;

import com.amit.test.exception.ResourceNotFoundException;
import com.amit.test.model.Employee;
import com.amit.test.repository.EmployeeRepository;
import com.amit.test.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> employee1 = employeeRepository.findByEmail(employee.getEmail());
        if(employee1.isPresent())
                throw new ResourceNotFoundException("employee already exist with given email:"+employee.getEmail());

        return employeeRepository.save(employee);
    }
}
