package com.hooyu.exercise.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The ApplicationConfiguration class is a class that provide some configuration for the server
 * In this case is telling for the server where the views are and that they have a different extension.
 * 
 * @author geovanefilho
 *
 */
@Configuration
@EnableWebMvc
public class ApplicationConfiguration implements WebMvcConfigurer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureViewResolvers(final ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
}
