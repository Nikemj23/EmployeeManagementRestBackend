package com.app.controller;

import java.util.List;

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

@CrossOrigin(origins="http://localhost:3000") //for frontend // if origins not provided then internally (origins = "*") ==>to accept response from any server 
@RestController
@RequestMapping("/api/employees")      //optional but recommended
public class EmployeeController {
	@Autowired//if not used will get null pointer exception
	private IEmployeeService employeeService;
    public EmployeeController() {
		System.out.println("in ctor of "+getClass());
	}
    //1)add request handling method to send all employees to the  caller frontend
//    @GetMapping
//    public List<Employee> getAllEmpDetails()
//    {
//    	System.out.println("in get all emps");
//    	return employeeService.getAllEmployees();
//  } 
    
    @GetMapping
    public ResponseEntity<?> getAllEmpDetails()  //?==common super class bought in for inheritance//
    {//? is replaced by  List<Employee> internally
    	System.out.println("in get all emps");
    	return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);//<> is replaced by  List<Employee> internally
    		
    } 
    
    //2)add request handling method to insert new details(create new resource):POST
    @PostMapping
    public Employee addEmpDetails(@RequestBody Employee e)
    {
    	System.out.println("in add emp "+e);
    	return employeeService.addOrUpdateEmployeeDetails(e);
    }
    
    //3)add request handling method to delete employee details by employee id
    //Request URL sent by front end: http://host:port/api/employees/1234,  method=DELETE
    @DeleteMapping("/{id}")
    public String deleteEmpDetails(@PathVariable int id)
    {
    	System.out.println("in del emp dtls"+id);
    	return employeeService.deleteEmpDetails(id);
    } 
    
    //4)add request handling method to     get selected emp details by id.
    //URL : http://host:port/api/employees/1234, method=GET
//    @GetMapping("/{empId}")
//    public Employee getEmpDetails(@PathVariable int empId)
//    {
//    	System.out.println("in get emp details"+empId);
//    	return employeeService.fetchEmpDetails(empId);
//    }
    
    @GetMapping("/{empId}")
    public ResponseEntity<?> getEmpDetails(@PathVariable int empId)
    {
    	System.out.println("in get emp details"+empId);
    	try {
			return new ResponseEntity<>(employeeService.fetchEmpDetails(empId), HttpStatus.OK);//<> is replaced by  Employee internally
		} catch (RuntimeException e) {
			System.out.println("Error in get emp deteials"+e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);//<> is replaced by  String internally //javac will figure it out
		}
    	
    }
    
    
    //add request handling method to update existing details(update a  resource):put
    @PutMapping
    public Employee updateEmpDetails(@RequestBody Employee e)//deserial (un marshalling)
    {
    	//e: Detached pojo,containing updated state
    	System.out.println("in add emp "+e);
    	return employeeService.addOrUpdateEmployeeDetails(e);
    }
}
