package com.amit.test.controller;

import com.amit.test.model.Employee;
import com.amit.test.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc ; //to call rest api's

    @MockBean // create a instance of employeeService and registered in employee Controller
    private EmployeeService employeeService ;

    @Autowired
    private ObjectMapper objectMapper ; // serialize and deserialize objects




    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
     //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Amit")
                .lastName("Nandan")
                .email("amitnandan@gmail.com")
                .build();

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(
                (invocation)-> invocation.getArgument(0)
        );

     // when - action or the behaviour we are going to test
    ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.post("/api/employees").contentType(MediaType.APPLICATION_JSON).
     content(objectMapper.writeValueAsString(employee)));
     //then verify the ouput
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));
     }


     //get all employees REST API
      @Test
      public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
      //given - precondition or setup
      List<Employee> listOfEmployees = new ArrayList<>();
      listOfEmployees.add(Employee.builder().firstName("Amit").lastName("Nandan").email("amitnandan@gmail.com").build());
      listOfEmployees.add(Employee.builder().firstName("Amit2").lastName("Nandan2").email("amitnandan@gmail2.com").build());
      BDDMockito.given(employeeService.getAllEmployee()).willReturn(listOfEmployees);


          // when - action or the behaviour we are going to test
          ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));


          //then verify the ouput

          response.andExpect(MockMvcResultMatchers.status().isOk())
                  .andDo(MockMvcResultHandlers.print())
                  .andExpect(MockMvcResultMatchers.jsonPath("$.size()" , CoreMatchers.is(2)));
      }


    //positive scenario - valid employee id
    //get employee by id
       @Test
        public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given - precondition or setup
           Employee employee = Employee.builder()
                   .firstName("Amit")
                   .lastName("Nandan")
                   .email("amitnandan@gmail.com")
                   .build();
           long employeeId = 1;

           BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
           // when - action or the behaviour we are going to test
           ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",employeeId));
            //then verify the ouput
           response.andExpect(MockMvcResultMatchers.status().isOk())
                   .andDo(MockMvcResultHandlers.print())
                   .andExpect(MockMvcResultMatchers.jsonPath("$.firstName" , CoreMatchers.is(employee.getFirstName())))

                   .andExpect(MockMvcResultMatchers.jsonPath("$.lastName" , CoreMatchers.is(employee.getLastName())));
        }



    //negative scenario - ifInvalid employee id
    //get employee by id
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeObjectEmpty() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Amit")
                .lastName("Nandan")
                .email("amitnandan@gmail.com")
                .build();
        long employeeId = 1;

        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        // when - action or the behaviour we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",employeeId));
        //then verify the ouput
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName" , CoreMatchers.is("")))
//
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName" , CoreMatchers.is("")));
    }

    //Junit test for update employee REST API
     @Test
     public void givenUpdateEmployee_whenUpdatedEmployee_thenReturnUpdateEmployeeObject() throws Exception {
     //given - precondition or setup
     long employeeId = 1 ;
         Employee savedEmployee = Employee.builder()
                 .firstName("Amit")
                 .lastName("Nandan")
                 .email("amitnandan@gmail.com")
                 .build();
         Employee updateEmployee = Employee.builder()
                 .firstName("Amit1")
                 .lastName("Nandan1")
                 .email("amitnandan@gmail1.com")
                 .build();
         BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));

         BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(
                 (invocation)-> invocation.getArgument(0)
         );


         // when - action or the behaviour we are going to test


            ResultActions response = mockMvc.perform( MockMvcRequestBuilders.put("/api/employee/{id}",employeeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateEmployee))
            );


     //then verify the ouput

         response.andExpect(MockMvcResultMatchers.status().isOk())
                 .andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.firstName" , CoreMatchers.is(updateEmployee.getFirstName())))

                 .andExpect(MockMvcResultMatchers.jsonPath("$.lastName" , CoreMatchers.is(updateEmployee.getLastName())));
     }

    @Test
    public void givenUpdateEmployee_whenUpdatedEmployee_thenReturnUpdateEmployeeObjectEmpty() throws Exception {
        //given - precondition or setup
        long employeeId = 1 ;
        Employee savedEmployee = Employee.builder()
                .firstName("Amit")
                .lastName("Nandan")
                .email("amitnandan@gmail.com")
                .build();
        Employee updateEmployee = Employee.builder()
                .firstName("Amit1")
                .lastName("Nandan1")
                .email("amitnandan@gmail1.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(
                (invocation)-> invocation.getArgument(0)
        );


        // when - action or the behaviour we are going to test


        ResultActions response = mockMvc.perform( MockMvcRequestBuilders.put("/api/employee/{id}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateEmployee))
        );


        //then verify the ouput

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnStatus200() throws Exception {
        //given - precondition or setup
        long employeeId = 1;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);
        // when - action or the behaviour we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}",employeeId));
        //then verify the ouput
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }


}
