package dev.fringe.oauth2.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

import dev.fringe.oauth2.service.CustomInMemoryUserDetailsManager;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	public static String CLIENT = "test";
	public static String SECRET = "test";
	
	@Autowired TokenStore tokenStore;
	@Autowired UserApprovalHandler userApprovalHandler;
	@Autowired AuthenticationManager authenticationManagerBean;
	@Autowired CustomInMemoryUserDetailsManager customInMemoryUserDetailsManager;	
	 @Autowired
	    private UserDetailsService userDetailsService;	

	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler).authenticationManager(authenticationManagerBean);
//		.userDetailsService(userDetailsService);
	}

	/**
	 * customize need
	 */
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(CLIENT).authorities("ROLE_CLIENT").scopes("read","write","trust").secret(SECRET);
	}
}
