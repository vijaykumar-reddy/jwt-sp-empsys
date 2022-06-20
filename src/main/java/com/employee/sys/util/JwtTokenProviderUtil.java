package com.employee.sys.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtTokenProviderUtil implements Serializable {

	private static final long serialVersionUID = -8516186395530694979L;

	public static Map<String, String> generateToken(User user, HttpServletRequest request) {
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

		String access_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())/* Issuer can be configured */
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		String refresh_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString()).sign(algorithm);

//		response.setHeader("access_token", access_token);
//		response.setHeader("refresh_token", refresh_token);
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);

		return tokens;
	}

	public static UsernamePasswordAuthenticationToken validateToken(String token) {
		// Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());// need to
		// configure secret some where
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret".getBytes())).build();
		DecodedJWT decodeJWT = verifier.verify(token);
		String userName = decodeJWT.getSubject();
		String[] roles = decodeJWT.getClaim("roles").asArray(String.class);

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Arrays.stream(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});

		UsernamePasswordAuthenticationToken athenticationToken = new UsernamePasswordAuthenticationToken(userName, null,
				authorities);
		return athenticationToken;
	}

}
