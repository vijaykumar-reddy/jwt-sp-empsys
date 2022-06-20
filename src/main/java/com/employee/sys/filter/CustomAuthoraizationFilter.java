package com.employee.sys.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employee.sys.util.JwtTokenProviderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthoraizationFilter extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(CustomAuthoraizationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
			// letting user to login and also letting to get the another access token using
			// refresh token
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					SecurityContextHolder.getContext().setAuthentication(JwtTokenProviderUtil.validateToken(token));
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					logger.error("Error while  accessing : {}", e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
//					response.sendError(HttpStatus.FORBIDDEN.value());
					Map<String, String> errors = new HashMap<String, String>();
					errors.put("erro_msg", e.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), errors);
				}

			} else {
				filterChain.doFilter(request, response);
			}
		}

	}
}
