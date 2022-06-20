package com.employee.sys.entitey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.employee.sys.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({ "id", "email", "name", "empSalary" })
public class Employee extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "emp_name", nullable = false)
	private String name;

	@Column(unique = true)
	private String email;

	@Column(name = "emp_salary")
	private double empSalary;

	public Employee() {

	}

	public Employee(int id, String name, String email, double salary) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.empSalary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSalary() {
		return empSalary;
	}

	public void setSalary(double salary) {
		this.empSalary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", salary=" + empSalary + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + "]";
	}

}
