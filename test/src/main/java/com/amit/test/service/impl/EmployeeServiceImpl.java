package com.amit.test.service.impl;

import com.amit.test.exception.ResourceNotFoundException;
import com.amit.test.model.Employee;
import com.amit.test.repository.EmployeeRepository;
import com.amit.test.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> employee1 = employeeRepository.findByEmail(employee.getEmail());
        if(employee1.isPresent())
                throw new ResourceNotFoundException("employee already exist with given email:"+employee.getEmail());

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {

        return employeeRepository.findAll();

        

    }

    @Override
    public Optional<Employee> getEmployeeById(long employeeId) {

        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(!employee.isPresent())
            throw new ResourceNotFoundException("Employee with given id not present"+employeeId);
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
