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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

    //unit test on getALL employee
     @Test
     public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeeList(){
     //given - precondition or setup
     Employee employee1 =  Employee.builder().
                 id(2)
                 .firstName("Amit1")
                 .lastName("Nandan1").email("a.amitnandan1@gmail.com")
                 .build();

     given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

         // when - action or the behaviour we are going to test

         List<Employee> employeeList = employeeService.getAllEmployee();

     //then verify the ouput

         assertThat(employeeList).isNotNull();

         assertThat(employeeList.size()).isEqualTo(2);

    }

    //test for negative scenerio
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeeList(){
        //given - precondition or setup
        Employee employee1 =  Employee.builder().
                id(2)
                .firstName("Amit1")
                .lastName("Nandan1").email("a.amitnandan1@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour we are going to test

        List<Employee> employeeList = employeeService.getAllEmployee();

        //then verify the ouput

        assertThat(employeeList).isEmpty();

        assertThat(employeeList.size()).isEqualTo(0);

    }

    //check employeeById
     @Test
     public void givenEmployeeId_whenFindByEmployeeId_thenReturnEmployee(){
     //given - precondition or setup


     given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

     // when - action or the behaviour we are going to test
         Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

     //then verify the ouput
        assertThat(savedEmployee).isNotNull();
     }




    //check update employee
    @Test
    public void givenEmployeeObject_whenFindByUpdate_thenReturnUpdatedEmployee(){
        //given - precondition or setup
        Employee employee1 =  Employee.builder().
                id(2)
                .firstName("Amit1")
                .lastName("Nandan1").email("a.amitnandan1@gmail.com")
                .build();

        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("amitnandan@gmail.com");

        // when - action or the behaviour we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then verify the ouput
        assertThat(updatedEmployee.getEmail()).isEqualTo("amitnandan@gmail.com");
    }

    //delete by id
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnVoid(){
        long employeeId = 1;
        //given - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when - action or the behaviour we are going to test
        employeeService.deleteEmployee(employeeId);

        //then verify the ouput
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }


}
