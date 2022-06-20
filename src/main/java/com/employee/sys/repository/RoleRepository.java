package com.employee.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.sys.entitey.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String roleName);

}
