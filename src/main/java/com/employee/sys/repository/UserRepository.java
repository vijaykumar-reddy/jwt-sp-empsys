package com.employee.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.sys.entitey.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);
}
