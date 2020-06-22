package dev.fringe.oauth2.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import dev.fringe.oauth2.model.User;

@Configuration
@EnableWebSecurity
public class AuthenticationManagerBuilderConfig extends WebSecurityConfigurerAdapter {

//  @Autowired UserSSOService userService;
//	@Autowired CustomInMemoryUserDetailsManager customInMemoryUserDetailsManager;
    @Autowired private UserDetailsService userDetailsService;    

	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		 auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
		auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN").and().withUser("bob").password("abc123").roles("USER");
//		 auth.userDetailsService(customInMemoryUserDetailsManager);
//		 auth.userDetailsService(userDetailsService);
//		userDetailsService

	}
	
	@Bean
	public List<User> users(){
		return Arrays.asList(new User("bob", "abc123", Arrays.asList(new SimpleGrantedAuthority("USER")),"hdleebob"));
	}
}
