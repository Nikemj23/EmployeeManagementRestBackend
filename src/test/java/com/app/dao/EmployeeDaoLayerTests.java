package com.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.Employee;
import static java.time.LocalDate.parse;
@SpringBootTest//entire SC is up n running in debug mode==>all spring beans(controllers,service dao)
//can be autowired directly
class EmployeeDaoLayerTests {
	@Autowired
  private EmployeeRepository empRepo;
  
  
	@Test
	void testFindBySalaryGreaterThan() {
		List<Employee> list =empRepo.findBySalaryGreaterThan(1234);
		System.out.println(list);
		assertEquals(3,list.size());
	}

	@Test
	void testfindByJoinDateBetween() {
		List<Employee> list =empRepo.findByJoinDateBetween(parse("2020-01-01"), parse("2023-02-03"));
	System.out.println(list);
	assertEquals(3,list.size());
	}
	
	@Test
	void testByDeptAndLocation() {
		List<Employee> list =empRepo.findByDeptAndLocation("HR", "Mumbai");
		assertEquals("Rama",list.get(0).getName());
		assertEquals("Karan",list.get(3).getName());
	}
	

	@Test
	void testFindByLastName()
	{
		Optional<Employee> optional = empRepo.findByLastName("kale");
		assertEquals("Rama",optional.get().getName());
	}
	
	@Test
	@Transactional
	@Rollback(value=false)
	void testUpdateEmpSalary()
	{
		assertEquals(1,empRepo.updateEmpSalary(1000,2));
	} 
}
