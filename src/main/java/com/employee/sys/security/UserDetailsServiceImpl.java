package com.employee.sys.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employee.sys.entitey.User;
import com.employee.sys.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUserName(username);

		if (user == null) {
			logger.error("No user exist with name : {}", username);
			throw new UsernameNotFoundException("User not found in the database");
		} else {
			logger.info("loading th user details from the database with name :{} ", username);
		}

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);
	}

}
