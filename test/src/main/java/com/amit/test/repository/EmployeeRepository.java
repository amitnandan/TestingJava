package com.amit.test.repository;

import com.amit.test.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee , Long > {


    Optional<Employee> findByEmail( String email );


    //defined custom query using JPQL with index parameters.
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL( String firstName , String lastName);


    //defined custom query using JPQL with named parameters.

    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName ,@Param("lastName") String lastName);


    @Query( nativeQuery = true , value = "Select * from employees e where e.first_name = ?1 and e.last_name = ?2")
    Employee findByNativeSQL( String firstName , String lastName);

    @Query( nativeQuery = true , value = "Select * from employees e where e.first_name =:firstName and e.last_name =:lastName")
    Employee findByNativeSQLNamed( @Param("firstName") String firstName , @Param("lastName") String lastName);

}
