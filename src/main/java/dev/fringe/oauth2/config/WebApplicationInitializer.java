package dev.fringe.oauth2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import dev.fringe.oauth2.config.security.ResourceOwner;
import dev.fringe.oauth2.config.security.UserAgent;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "dev.fringe.oauth2")
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {WebApplicationInitializer.class, ResourceOwner.class, UserAgent.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
