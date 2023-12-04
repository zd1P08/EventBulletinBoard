package com.example.app.config;

import com.example.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private UserService userService;

	public static final String LOGIN_URL = "/auth/login";
	public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";
	public static final String DEFAULT_SUCCESS_URL = "/events";
	public static final String USERNAME = "loginId";
	public static final String PASSWORD = "loginPass";

	@Bean
	@Order(1)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login
					.loginProcessingUrl(LOGIN_URL)
					.loginPage(LOGIN_URL)
					.defaultSuccessUrl(DEFAULT_SUCCESS_URL)
					.failureUrl(LOGIN_FAIL_URL)
					.usernameParameter(USERNAME)
					.passwordParameter(PASSWORD)
					.permitAll());
		http.logout(logout -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
					.invalidateHttpSession(true)
					.logoutSuccessUrl(LOGIN_URL + "?logout")
					.deleteCookies("JSESSIONID")
					.permitAll());
		http.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
					.invalidSessionUrl(LOGIN_URL + "?invalidSession")
					.maximumSessions(1000000)
					.maxSessionsPreventsLogin(true));
		http.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.requestMatchers("/error", "/auth/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/guest/**").permitAll()
					.requestMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated()
				);
		http.authenticationProvider(authenticationProvider());
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Bean
	ApplicationListener<AuthenticationSuccessEvent> successListener() {
		return event -> {
			System.out.printf(
					"\uD83C\uDF89 [%s] %s%n", event.getAuthentication().getClass().getSimpleName(),
					event.getAuthentication().getName()
			);
		};
	}

}