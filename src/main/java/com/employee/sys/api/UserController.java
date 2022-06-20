package com.employee.sys.api;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.employee.sys.entitey.Role;
import com.employee.sys.entitey.User;
import com.employee.sys.service.UserService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok(userService.getUsers());
	}

	@PostMapping("/new-user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		logger.info("Creating new user with id : {}", user.getId());

		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/new-user").toString());
		return ResponseEntity.created(uri).body(userService.saveUser(user));

	}

	@PostMapping("/new-role")
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/new-role").toString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));

	}

	@PostMapping("/new-role-user")
	public ResponseEntity<?> addNewRoleToUser(@RequestBody RoleToUserForm roleToUserForm) {
		userService.addRoleToUser(roleToUserForm.getUserName(), roleToUserForm.getRoleName());
		return ResponseEntity.ok().build();

	}

	@GetMapping("/user{userName}")
	public User getUserByName(@PathVariable(value = "userName") String userName) {
		return userService.getUser(userName);

	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());// need to load from some where
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodeJWT = verifier.verify(refresh_token);
				String userName = decodeJWT.getSubject();
				User user = userService.getUser(userName);

				String access_token = JWT.create().withSubject(user.getUserName())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10000 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);

				Map<String, String> tokens = new HashMap<String, String>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				logger.error("Error while logging in : {}", e.getMessage());
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
//				response.sendError(HttpStatus.FORBIDDEN.value());
				Map<String, String> errors = new HashMap<String, String>();
				errors.put("erro_msg", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}

		} else {
			throw new RuntimeException("Missing refresh token");
		}

	}

	class RoleToUserForm {

		private String userName;
		private String roleName;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

	}
}
