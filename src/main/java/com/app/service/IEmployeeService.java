package com.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.app.pojos.Employee;

public interface IEmployeeService {
     List<Employee> getAllEmployees();
     
     Employee addOrUpdateEmployeeDetails(Employee   e);
     
     String deleteEmpDetails(int id);

	Employee fetchEmpDetails(int empId);
	//find all emps drawing salary greater than specific values
	List<Employee> findEmpsBySalary(double minSal);
	
	List<Employee> findEmpsByJoiningDate(LocalDate begin,LocalDate end);
	
	List<Employee> findEmpsByDeptAndLocation(String dept, String location);
	
	 Optional<Employee> findEmpByLastName(String lastName);
	 
	 int updateEmployeeSalary( double salIncr, int empId);
	 
	  List<Employee> getEmployeesByDepartmentSortedBySalary(String deptId);
	  
	  List<Employee> getEmpNamesByDept(String department);
}
