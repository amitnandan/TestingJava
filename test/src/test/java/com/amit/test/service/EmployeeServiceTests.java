package com.amit.test.service;

import com.amit.test.exception.ResourceNotFoundException;
import com.amit.test.model.Employee;
import com.amit.test.repository.EmployeeRepository;
import com.amit.test.service.impl.EmployeeServiceImpl;
import org.aspectj.lang.annotation.Before;
import static  org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static  org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    //using mock method we needed this ..
    //private EmployeeService employeeService;

    private  Employee employee ;

    @BeforeEach
    public void setup(){
        //this is using the mock method
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder().
                id(1)
                .firstName("Amit")
                .lastName("Nandan").email("a.amitnandan@gmail.com")
                .build();
    }

    //save Employee method
     @Test
     public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
             //given - precondition or setup

         given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

         given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());


         given(employeeRepository.save(employee)).willReturn(employee);

         // when - action or the behaviour we are going to test
         Employee savedEmployee = employeeService.saveEmployee(employee);

         //then verify the ouput

         assertThat(savedEmployee).isNotNull();

         }
    //save employee test throwing exception
    @Test
    public void givenExisitingEmailObject_whenSaveEmployee_thenThrowsException(){
        //given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        //given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when - action or the behaviour we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });
        //Employee savedEmployee = employeeService.saveEmployee(employee);

        //then verify the ouput

        verify(employeeRepository, never()).save(any(Employee.class));
    }

}
