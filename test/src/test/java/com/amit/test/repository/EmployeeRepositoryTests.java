package com.amit.test.repository;


import com.amit.test.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest // to test repository test loads only repository layer
//privde inmemory database for testing purpose
public class EmployeeRepositoryTests {

    private  Employee employee ;

    @Autowired
    private EmployeeRepository employeeRepository;



    @BeforeEach
    public void setup()
        {
        employee = Employee.builder().firstName("Amit").lastName("Nandan").email("a.amitnandan@gmail.com").build();
    }



    //Junit test for save employee operation
    @Test
    @DisplayName("Junit test for save Employee Operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployeeObject(){
        //given - precondition

//        Employee employee = Employee.builder().
//                firstName("Amit")
//                .lastName("Nandan").email("a.amitnandan@gmail.com")
//                .build();

        //when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);



    }

    //junit for get all Employees operation
    @Test
    public void givenEmployeeObject_whenGetAll_thenReturnListEmployeeObject(){
        //given - precondition

//        Employee employee1 = Employee.builder().
//                firstName("Amit")
//                .lastName("Nandan").email("a.amitnandan@gmail.com")
//                .build();
        Employee employee2 = Employee.builder().
                firstName("Amit2")
                .lastName("Nandan2").email("a.amitnandan@gmail2.com")
                .build();

        Employee employee3 = Employee.builder().
                firstName("Amit3")
                .lastName("Nandan3").email("a.amitnandan@gmail3.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        //when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(3);
    }


    //get employee by id operation
     @Test
         public void givenEmployeeObject_whenEmployeeById_thenEmployeeObject() {
         //given - precondition or setup
//         Employee employee1 = Employee.builder().
//                 firstName("Amit")
//                 .lastName("Nandan").email("a.amitnandan@gmail.com")
//                 .build();
         employeeRepository.save(employee);

         // when - action or the behaviour we are going to test
         Employee employeee = employeeRepository.findById(employee.getId()).get();

         //then verify the ouput
         assertThat(employeee).isNotNull();
         assertThat(employeee.getFirstName()).isEqualTo("Amit");

     }


     //get employee using email
      @Test
      public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
              //given - precondition or setup
//          Employee employee1 = Employee.builder().
//                  firstName("Amit")
//                  .lastName("Nandan").email("a.amitnandan@gmail.com")
//                  .build();
          employeeRepository.save(employee);

          // when - action or the behaviour we are going to test
            Employee employee = employeeRepository.findByEmail("a.amitnandan@gmail.com").get();

          //then verify the ouput
          assertThat(employee).isNotNull();
    }

     @Test
     public void givenEmployeeObject_whenUpdatedEmployeeObject_thenReturnUpdatedEmployeeObject(){
             //given - precondition or setup
//         Employee employee1 = Employee.builder().
//                 firstName("Amit")
//                 .lastName("Nandan").email("a.amitnandan@gmail.com")
//                 .build();
         employeeRepository.save(employee);

         // when - action or the behaviour we are going to test
            Employee employeee = employeeRepository.findById(employee.getId()).get();

            employeee.setEmail("amitnandan@gmail.com");
           Employee updatedEmployee =  employeeRepository.save(employeee);
            //then verify the ouput

         assertThat(updatedEmployee).isNotNull();
         assertThat(updatedEmployee.getEmail()).isEqualTo("amitnandan@gmail.com");

     }
     //to test delete an object
      @Test
      public void givenEmployeeObject_whenEmployeeDeleted_thenEmployeeList(){
              //given - precondition or setup
//          Employee employee1 = Employee.builder().
//                  firstName("Amit")
//                  .lastName("Nandan").email("a.amitnandan@gmail.com")
//                  .build();
          Employee employee2 = Employee.builder().
                  firstName("Amit2")
                  .lastName("Nandan2").email("a.amitnandan@gmail2.com")
                  .build();

          Employee employee3 = Employee.builder().
                  firstName("Amit3")
                  .lastName("Nandan3").email("a.amitnandan@gmail3.com")
                  .build();

          employeeRepository.save(employee);
          employeeRepository.save(employee2);
          employeeRepository.save(employee3);

          // when - action or the behaviour we are going to test
          employeeRepository.deleteById(employee2.getId());

          Optional<Employee> employeeOptional = employeeRepository.findById(employee2.getId());
              //then verify the ouput
          assertThat(employeeOptional).isEmpty();
          }
      //custom query generated using JPQL with index
      @Test
      public void givenEmployeeFirstAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
               //given - precondition or setup
//          Employee employee1 = Employee.builder().
//                  firstName("Amit")
//                  .lastName("Nandan").email("a.amitnandan@gmail.com")
//                  .build();
          employeeRepository.save(employee);
          String firstName = "Amit";
          String lastName = "Nandan";
               // when - action or the behaviour we are going to test

         Employee employee = employeeRepository.findByJPQL(firstName,lastName);
               //then verify the ouput
          assertThat(employee).isNotNull();

    }

    //custom query generated using JPQL with index
     @Test
     public void givenFirstAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
//         Employee employee1 = Employee.builder().
//                 firstName("Amit")
//                 .lastName("Nandan").email("a.amitnandan@gmail.com")
//                 .build();
         employeeRepository.save(employee);
         String firstName = "Amit";
         String lastName = "Nandan";
         // when - action or the behaviour we are going to test

         Employee employee = employeeRepository.findByJPQLNamedParams(firstName,lastName);
         //then verify the ouput
         assertThat(employee).isNotNull();

     }
    //custom query generated using Native SQL with index
    @Test
    public void givenFirstAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
//        Employee employee1 = Employee.builder().
//                firstName("Amit")
//                .lastName("Nandan").email("a.amitnandan@gmail.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Amit";
        String lastName = "Nandan";
        // when - action or the behaviour we are going to test

        Employee employee = employeeRepository.findByNativeSQL(firstName,lastName);
        //then verify the ouput
        assertThat(employee).isNotNull();

    }

    //custom query generated using Native SQL with named
    @Test
    public void givenFirstAndLastName_whenFindByNativeSQLNamed_thenReturnEmployeeObject(){
//        Employee employee1 = Employee.builder().
//                firstName("Amit")
//                .lastName("Nandan").email("a.amitnandan@gmail.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Amit";
        String lastName = "Nandan";
        // when - action or the behaviour we are going to test

        Employee employee = employeeRepository.findByNativeSQLNamed(firstName,lastName);
        //then verify the ouput
        assertThat(employee).isNotNull();

    }

}
