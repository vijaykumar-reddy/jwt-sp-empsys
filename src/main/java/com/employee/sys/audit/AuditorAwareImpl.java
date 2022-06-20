package com.employee.sys.audit;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.getProperty("networkId");
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		return Optional.of(authentication.getName());
	}
//	@Override
//	public Optional<User> getCurrentAuditor() {
//
//		HashMap<Integer, String> previllageUsers = new HashMap<>();
//		previllageUsers.put(2, "vijay kumar reddy");
//		previllageUsers.put(1, "ganesh");
//		previllageUsers.put(3, "viek");
//		previllageUsers.put(5, "ravi");
//		previllageUsers.put(6, "abhi");
//
//		Object[] rendomKey = previllageUsers.keySet().toArray();
//
//		return Optional.of(previllageUsers.get(rendomKey[new Random().nextInt(rendomKey.length)]));
//		
//	}

}
