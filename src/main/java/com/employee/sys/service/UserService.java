package com.employee.sys.service;

import java.util.List;

import com.employee.sys.entitey.Role;
import com.employee.sys.entitey.User;

public interface UserService {

	User saveUser(User user);

	Role saveRole(Role role);

	void addRoleToUser(String userName, String roleName);

	User getUser(String userName);

	List<User> getUsers();

}
