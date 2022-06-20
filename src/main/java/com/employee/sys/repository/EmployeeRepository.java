package com.employee.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.sys.entitey.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
