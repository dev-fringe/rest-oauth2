package dev.fringe.oauth2.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.fringe.oauth2.config.security.AuthorizationServerConfiguration;
import dev.fringe.oauth2.model.Customer;
import dev.fringe.oauth2.service.support.ApiRestLoggingRequestInterceptor;
import lombok.extern.log4j.Log4j2;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerRestOAuthTest.class)
@TestMethodOrder(OrderAnnotation.class)
@Log4j2
public class CustomerRestOAuthTest {

	@Value("${api.access.token.uri:http://localhost:8080/rest-oauth2/oauth/token}")
	private String accessTokenUri;

	@Value("${api.username:bob}")
	private String username;

	@Value("${api.password:abc123}")
	private String password;

	@Bean(name = "oAuth2RestTemplate")
	public OAuth2RestTemplate oAuth2RestTemplate() {
		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setClientId(AuthorizationServerConfiguration.CLIENT);
		resource.setClientSecret(AuthorizationServerConfiguration.SECRET);
		resource.setAccessTokenUri(accessTokenUri);
		resource.setUsername(username);
		resource.setPassword(password);
		OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, new DefaultAccessTokenRequest());
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(accessToken));
		restTemplate.setRequestFactory(new InterceptingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(), Arrays.asList(new ApiRestLoggingRequestInterceptor())));
		return restTemplate;
	}

	@Autowired
	OAuth2RestTemplate oAuth2RestTemplate;

	@Test
	public void test() {
		log.info("customer = " + oAuth2RestTemplate.postForObject("http://localhost:8080/rest-oauth2/customers", new Customer("k", "d", "kd@g.com", 0L),Customer.class));
		log.info("customers = " + oAuth2RestTemplate.exchange("http://localhost:8080/rest-oauth2/customers", HttpMethod.GET, null,new ParameterizedTypeReference<List<Customer>>() {}).getBody());
	}

}
