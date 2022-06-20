package com.employee.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.sys.entitey.Employee;
import com.employee.sys.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public List<Employee> getListEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getSpecificEmployee(Integer id) {

		return employeeRepository.findById(id).get();
	}

	public Employee updateEmployee(Integer id, Employee employee) {

		Employee updateEmployee = employeeRepository.findById(id).get();

		updateEmployee.setEmail(employee.getEmail());
		updateEmployee.setName(employee.getName());
		updateEmployee.setSalary(employee.getSalary());

		return employeeRepository.save(updateEmployee);

	}

	public Map<String, Boolean> deleteEmployee(Integer id) {

		Employee updateEmployee = employeeRepository.findById(id).get();

		employeeRepository.delete(updateEmployee);

		HashMap<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}

}
