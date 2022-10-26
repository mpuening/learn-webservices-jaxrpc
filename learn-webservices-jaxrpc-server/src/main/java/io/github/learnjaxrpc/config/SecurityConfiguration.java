package io.github.learnjaxrpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

	@Bean
	@SuppressWarnings("deprecation")
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User
				.withDefaultPasswordEncoder()
				.username("service")
				.password("secretpassword")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.httpBasic()
				.and()
				.csrf().disable()
				.authorizeHttpRequests(
						authz -> authz.anyRequest().authenticated()
				)
				.build();
	}
}
