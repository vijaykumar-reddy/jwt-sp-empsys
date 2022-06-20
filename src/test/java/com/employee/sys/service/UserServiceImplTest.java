package com.employee.sys.service;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.employee.sys.entitey.Role;
import com.employee.sys.entitey.User;

@SpringBootTest
class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	void testSaveUserData() {
//		userService.saveUser(new User(null, "ganesh raj", "ganesh", "ganesh738", new ArrayList<>()));
//		userService.saveUser(new User(null, "akshara tejasri", "akshara", "aksha890", new ArrayList<>()));
//		userService.saveUser(new User(null, "abhi jnan", "abhi", "abhi@sy", new ArrayList<>()));
//		userService.saveUser(new User(null, "Veerababu", "mravi", "ravi@9494", new ArrayList<>()));
		
//		userService.addRoleToUser("vkreddy", "ROLE_USER");
//		userService.addRoleToUser("akshara", "ROLE_ADMIN");
		userService.addRoleToUser("mravi", "ROLE_USER");
//		userService.addRoleToUser("ganesh", "ROLE_MANAGER");
//		userService.addRoleToUser("abhi", "ROLE_USER");

	}

	@Test
	void testSaveUserRoles() {
		userService.saveRole(new Role("ROLE_USER"));
		userService.saveRole(new Role("ROLE_MANAGER"));
		userService.saveRole(new Role("ROLE_ADMIN"));
		userService.saveRole(new Role("ROLE_SUPER_ADMIN"));
	}
}
