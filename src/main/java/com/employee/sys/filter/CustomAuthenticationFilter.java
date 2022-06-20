package com.employee.sys.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.employee.sys.util.JwtTokenProviderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

	private final AuthenticationManager authenticationManager;// this can be autowired if this class is a component

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		logger.info("logged in Successfully ! usernam {}", userName);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,
				password);

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		User user = (User) authentication.getPrincipal();
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), JwtTokenProviderUtil.generateToken(user, request));

	}

}
