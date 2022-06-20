package com.employee.sys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.employee.sys.exception.CustomAccessDeniedHandler;
import com.employee.sys.filter.CustomAuthenticationFilter;
import com.employee.sys.filter.CustomAuthoraizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
				authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/api/login/**");// by default it is /login overriding default
																			// one
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/login**", "/api/token/refresh/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("ROLE_USER");//.and()
			//	.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/new/employee**").hasAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/employees/**").hasAuthority("ROLE_ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
//		http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAuthoraizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}

//	@Bean
//	public AccessDeniedHandler accessDeniedHandler() {
//		return new CustomAccessDeniedHandler();
//	}

}
