package dev.fringe.oauth2.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AuthenticationManagerBuilderConfig extends WebSecurityConfigurerAdapter {

//    @Autowired UserSSOService userService;
    
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		 auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
		auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN").and().withUser("bob").password("abc123").roles("USER");
	}
}
