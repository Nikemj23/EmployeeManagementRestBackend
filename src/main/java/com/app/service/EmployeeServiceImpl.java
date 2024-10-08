package com.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.EmployeeRepository;
import com.app.pojos.Employee;
@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {
      //dependency: dao layer interface
	@Autowired
	private EmployeeRepository employeeRepo;
	@Override
	public List<Employee> getAllEmployees() {
		//method of JpaRepository: super i/f dao layer i/f
		//Inherited API: public List<T> findAll();
		return employeeRepo.findAll();//txn over ==>session closed,returns list of detached entities to the caller
	}
	@Override
	public Employee addOrUpdateEmployeeDetails(Employee emp) {  //transient :-not part of L1 cache not exist in db its just part of java heap
		//it returns the persistence entity
		return employeeRepo.save(emp);  //save working ass save or update
		//CrudRepository methd: save(T entity)
		//checks if id==null=> transient entity, will fire insert upon commit
		//if id!= null==> detached entity, will fire update upon commit
	}// what will this method return ?Detached emp to the controller
	
	
	@Override
	public String deleteEmpDetails(int id) {
		// service layer invoke dao layer
		 employeeRepo.deleteById(id);
		 return "Emp Details with ID "+id+" deleted succesfully .....  ";
	}
	
	
	@Override
	public Employee fetchEmpDetails(int empId) {
		//invoke dao's findById(Id) method
		//Optional<Employee> optional = employeeRepo.findById(empId);  //optional=holder(empty/populated)
		return employeeRepo.findById(empId)   //inherited from crud repo      //()-> lambda corresponding to get method of supplier interface
				.orElseThrow(()-> new ResourceNotFoundException("Emp by Id"+ empId +" not found!!!!" ));
		//cretaing an unchecked exception so that compiler doesnt force handling of it
	}
	@Override
	public List<Employee> findEmpsBySalary(double minSal) {
		// TODO Auto-generated method stub
		return employeeRepo.findBySalaryGreaterThan(minSal);
	}
	@Override
	public List<Employee> findEmpsByJoiningDate(LocalDate begin, LocalDate end) {
		return employeeRepo.findByJoinDateBetween(begin, end);
	}
	@Override
	public List<Employee> findEmpsByDeptAndLocation(String dept, String location) {
		
		return employeeRepo.findByDeptAndLocation(dept, location);
	}
	@Override
	public Optional<Employee> findEmpByLastName(String lastName) {
		
		return employeeRepo.findByLastName(lastName);
	}
	@Override
	public int updateEmployeeSalary( double salIncr,int empId) {
		
		return employeeRepo.updateEmpSalary(salIncr, empId);
	}
	@Override
	public List<Employee> getEmployeesByDepartmentSortedBySalary(String deptId) {
		
		return employeeRepo.findByDeptOrderBySalary(deptId);
	}
	@Override
	public List<Employee> getEmpNamesByDept(String department) {
		return employeeRepo.getEmpFullNamesByDept(department);
	}
	

	// to send status code,header and response body==>Response Entity from Spring framework javadoc
	//Response Entity==>class which represent the entire response packet
}
