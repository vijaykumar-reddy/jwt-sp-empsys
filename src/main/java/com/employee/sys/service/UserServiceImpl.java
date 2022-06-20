package com.employee.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.sys.entitey.Role;
import com.employee.sys.entitey.User;
import com.employee.sys.repository.RoleRepository;
import com.employee.sys.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String userName, String roleName) {

		User user = userRepo.findByUserName(userName);
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);

	}

	@Override
	public User getUser(String userName) {
		return userRepo.findByUserName(userName);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

}
