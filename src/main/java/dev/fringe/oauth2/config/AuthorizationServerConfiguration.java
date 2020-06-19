package dev.fringe.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired TokenStore tokenStore;
	
	@Autowired UserApprovalHandler userApprovalHandler;
	
	@Autowired @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager;
	
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler).authenticationManager(authenticationManager);
	}

	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.realm("MY_OAUTH_REALM/client");
	}

	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("guest").authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit").authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read","write","trust")
			.secret("guest").accessTokenValiditySeconds(1000).refreshTokenValiditySeconds(3000);
	}

	
	
}
