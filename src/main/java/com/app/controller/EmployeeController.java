package com.app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.pojos.Employee;
import com.app.service.IEmployeeService;

@CrossOrigin(origins = "http://localhost:3000") // for frontend // if origins not provided then internally (origins =
												// "*") ==>to accept response from any server
@RestController
@RequestMapping("/api/employees") // optional but recommended
public class EmployeeController {
	@Autowired // if not used will get null pointer exception
	private IEmployeeService employeeService;

	public EmployeeController() {
		System.out.println("in ctor of " + getClass());
	}
	// 1)add request handling method to send all employees to the caller frontend
//    @GetMapping
//    public List<Employee> getAllEmpDetails()
//    {
//    	System.out.println("in get all emps");
//    	return employeeService.getAllEmployees();
//  } 

	@GetMapping
	public ResponseEntity<?> getAllEmpDetails() // ?==common super class bought in for inheritance//
	{// ? is replaced by List<Employee> internally
		System.out.println("in get all emps");
		return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);// <> is replaced by
																						// List<Employee> internally

	}

	// 2)add request handling method to insert new details(create new resource):POST
	@PostMapping
	public Employee addEmpDetails(@RequestBody Employee e) {
		System.out.println("in add emp " + e);
		return employeeService.addOrUpdateEmployeeDetails(e);
	}

	// 3)add request handling method to delete employee details by employee id
	// Request URL sent by front end: http://host:port/api/employees/1234,
	// method=DELETE
	@DeleteMapping("/{id}")
	public String deleteEmpDetails(@PathVariable int id) {
		System.out.println("in del emp dtls" + id);
		return employeeService.deleteEmpDetails(id);
	}

	// 4)add request handling method to get selected emp details by id.
	// URL : http://host:port/api/employees/1234, method=GET
//    @GetMapping("/{empId}")
//    public Employee getEmpDetails(@PathVariable int empId)
//    {
//    	System.out.println("in get emp details"+empId);
//    	return employeeService.fetchEmpDetails(empId);
//    }

	@GetMapping("/{empId}")
	public ResponseEntity<?> getEmpDetails(@PathVariable int empId) {
		System.out.println("in get emp details" + empId);
		try {
			return new ResponseEntity<>(employeeService.fetchEmpDetails(empId), HttpStatus.OK);// <> is replaced by
																								// Employee internally
		} catch (RuntimeException e) {
			System.out.println("Error in get emp deteials" + e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);// <> is replaced by String internally
																				// //javac will figure it out
		}

	}

	// add request handling method to update existing details(update a resource):put
	@PutMapping
	public Employee updateEmpDetails(@RequestBody Employee e)// deserial (un marshalling)
	{
		// e: Detached pojo,containing updated state
		System.out.println("in add emp " + e);
		return employeeService.addOrUpdateEmployeeDetails(e);
	}

	// add request handling method to find all employees drawing salary greater than
	// specific value
	@GetMapping("/salary/{minSal}")
	public ResponseEntity<?> getAllEmpsBySalary(@PathVariable double minSal) {
		System.out.println("in get all emps by salary");
		// API OF ResponseEntity
		// public static ResponseEntity.ok(T body):sets status code=200 with specified
		// body content
		return ResponseEntity.ok(employeeService.findEmpsBySalary(minSal));
	}
	//add request handling method to find all emps joined between 2 dates
	@GetMapping("/employees/{start}/{end}")
	public ResponseEntity<?> getEmployeesByJoinDate(@PathVariable("start") LocalDate start,
			@PathVariable("end") LocalDate end) {
		try {
			List<Employee> employees = employeeService.findEmpsByJoiningDate(start, end);

			if (employees.isEmpty()) {
				return new ResponseEntity<>("No employees found within the specified date ", HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(employees, HttpStatus.OK);
			}
		} catch (RuntimeException e) {
			// Log the error message (optional) and return a response with an error message
			System.err.println("Error while fetching employees: " + e.getMessage());
			return new ResponseEntity<>("An error occurred while fetching employee data ",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//add request handling method to find all emps from specific department and location
	 @GetMapping("/employees/dept/{dept}/location/{location}")
	    public ResponseEntity<?> getEmployeesByDeptAndLocation(
	            @PathVariable("dept") String dept, 
	            @PathVariable("location") String location) {
		 try {
				List<Employee> employees = employeeService.findEmpsByDeptAndLocation(dept, location);

				if (employees.isEmpty()) {
					return new ResponseEntity<>("No employees found by specified department and location ", HttpStatus.NO_CONTENT);
				} else {
					return new ResponseEntity<>(employees, HttpStatus.OK);
				}
			} catch (RuntimeException e) {
				// Log the error message (optional) and return a response with an error message
				System.err.println("Error while fetching employees: " + e.getMessage());
				return new ResponseEntity<>("An error occurred while fetching employee data ",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	    }
	//add request handling method to find employee by last name
	  @GetMapping("/lastname/{lastName}")
	    public ResponseEntity<?> getEmployeesByLastName(@PathVariable String lastName) {
	        Optional<Employee> employees = employeeService.findEmpByLastName(lastName);
	        
	        if (employees.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        } else {
	            return ResponseEntity.ok(employees);
	        }
	    }
	 
	 
	//add request handling method to update employee salary for specific id
	  @PutMapping("/salary/{salIncr}/{id}")
	    public ResponseEntity<?> updateSalary(
	            @PathVariable("id") int empId, 
	            @PathVariable double salIncr) {
	        try {
	            int updated = employeeService.updateEmployeeSalary(salIncr, empId);

	            if (updated == 1) {
	                return ResponseEntity.ok("Employee salary updated successfully.");
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body("Employee with ID " + empId + " not found.");
	            }
	        } catch (Exception e) {

	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("An error occurred while updating the employee's salary.");
	        }
	    }
	  
	  //add request handling method to sort all  emps from specific department as per salary
	  @GetMapping("/department/{dept}/sorted_by_salary")
	    public ResponseEntity<?> getEmployeesByDepartmentSortedBySalary(@PathVariable String dept) {
	        List<Employee> employees = employeeService.getEmployeesByDepartmentSortedBySalary(dept);
	        return new ResponseEntity<>(employees, HttpStatus.OK);
	    }
	  
	  //add request handling method to Display name n last name of emps from specific dept
	  @GetMapping("/department/{dept}")
	    public ResponseEntity<List<Employee>> getEmpFullNamesByDept(@PathVariable String dept) {
	        List<Employee> employees = employeeService.getEmpNamesByDept(dept);
	        return ResponseEntity.ok(employees);
	    }
}
