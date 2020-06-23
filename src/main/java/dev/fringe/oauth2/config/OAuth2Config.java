package dev.fringe.oauth2.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import dev.fringe.oauth2.model.User;
import dev.fringe.oauth2.service.ClientAndUserDetailsService;

@Configuration
public class OAuth2Config {

	public static final String CLIENT = "test";
	public static final String SECRET = "{bcrypt}$2a$10$adrdcp86oVWJRaz5nCoNyOf7w4ZCjRTlcqu8QgM4hVPJmGSmby6wu";

	@Configuration
	@EnableWebSecurity
	public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Autowired
		private UserDetailsService userDetailsService;

		@Autowired
		protected void registerAuthentication(final AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}
	}

	@Configuration
	@EnableResourceServer
	public class ResourceServer extends ResourceServerConfigurerAdapter {

		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/oauth/token").anonymous();
			http.authorizeRequests().antMatchers("/customers/**").access("#oauth2.hasScope('write')");
		}
	}

	@Configuration
	@EnableAuthorizationServer
	@Order(Ordered.LOWEST_PRECEDENCE - 100)
	public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

		@Bean
		public ClientDetailsService clientDetailsService() throws Exception {
			return combinedService;
		}

		@Bean
		public UserDetailsService userDetailsService() {
			return combinedService;
		}

		@Autowired
		AuthenticationManager authenticationManagerBean;

		private ClientAndUserDetailsService combinedService;

		private final List<UserDetails> users = Arrays.asList(User.create("bill", "abc123", "USER"),
				User.create("bob", "abc123", "USER"), User.create("sophia", "three", "NONE"),
				User.create("olivia", "four", "USER"));

		public AuthorizationServer() throws Exception {
			ClientDetailsService csvc = new InMemoryClientDetailsServiceBuilder().withClient(CLIENT)
					.authorizedGrantTypes("password").authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
					.scopes("read", "write", "trust")
					.secret("{bcrypt}$2a$10$adrdcp86oVWJRaz5nCoNyOf7w4ZCjRTlcqu8QgM4hVPJmGSmby6wu")
					//.and().withClient("")
					.accessTokenValiditySeconds(3600).and().build();
			UserDetailsService svc = new InMemoryUserDetailsManager(users);
			this.combinedService = new ClientAndUserDetailsService(csvc, svc);
		}

		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManagerBean);
		}

		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(clientDetailsService());
		}
	}

	public static void main(String[] args) {
		final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		System.out.println(encoder.encode("Passw@rd"));
	}
}
