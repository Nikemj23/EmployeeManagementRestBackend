package com.app.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.pojos.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
   //for save :--> if id=null transient --> insert a record
	
	//find all emps drawing salary> specific value:use derived query methods(finder methods)
	List<Employee> findBySalaryGreaterThan(double minSalary);
	//find all emps joined between 2 dates
	List<Employee> findByJoinDateBetween(LocalDate begin,LocalDate end);
	//find all emps from specific department and location
	List<Employee> findByDeptAndLocation(String dept, String location);
	//find employee by last name
    Optional<Employee> findByLastName(String lastName);
    //update employee salary for specific id
    @Modifying//=>DML
    @Query("update Employee e set e.salary = e.salary + :incr where e.id= :id")//=>custom query method:
    //no method naming pattern is expected
    int updateEmpSalary(@Param("incr")double  salIncr,@Param("id")int empId);
    //sort all  emps from specific department as per salary
    List<Employee> findByDeptOrderBySalary(String deptId);
    //Display name n last name of emps from specific dept: custom query with constr expression
   @Query("select new com.app.pojos.Employee.(name,lastName) from Employee e where e.dept=?1")
    List<Employee> getEmpFullNamesByDept(String department);//method name can be anything since its custom query
}
