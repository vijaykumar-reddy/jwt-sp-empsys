package com.employee.sys.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.sys.entitey.Employee;
import com.employee.sys.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService empService;

	@PostMapping(value = "/new/employee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<Employee>(empService.createEmployee(employee), HttpStatus.CREATED);

	}

	@GetMapping("/403")
	public String accessDenied(HttpRequest request) {
		return "Nothing";
		
	}
	
	@GetMapping("/employees")
	public List<Employee> getListEmployees() {
		logger.info("getting list of employees");
		return empService.getListEmployees();
	}

	@GetMapping("/sp-employee/{id}")
	public ResponseEntity<Employee> getSpecificEmployee(@PathVariable(value = "id") Integer id) {
		return ResponseEntity.ok(empService.getSpecificEmployee(id));

	}

	@PutMapping("/emp-modify/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer id,
			@RequestBody Employee employee) {
		return new ResponseEntity<Employee>(empService.updateEmployee(id, employee), HttpStatus.ACCEPTED);

	}

	@DeleteMapping("/rm-employee/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Integer id) {

		return empService.deleteEmployee(id);

	}

}
