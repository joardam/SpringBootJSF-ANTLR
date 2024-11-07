package com.manager.food_manager;


import jakarta.faces.webapp.FacesServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FoodManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodManagerApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<FacesServlet> facesServletRegistration() {
		ServletRegistrationBean<FacesServlet> registration = new ServletRegistrationBean<>(
				new FacesServlet(),
				"*.xhtml"  // Corrigido o padrão do mapeamento
		);
		registration.setLoadOnStartup(1);
		registration.addUrlMappings("*.jr");  // Corrigido o padrão do mapeamento
		registration.setName("FacesServlet");
		return registration;
	}

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			// Configurações básicas do JSF
			servletContext.setInitParameter("jakarta.faces.FACELETS_SKIP_COMMENTS", "true");
			servletContext.setInitParameter("jakarta.faces.PROJECT_STAGE", "Development");
			servletContext.setInitParameter("jakarta.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL", "true");

			// Configurações do PrimeFaces
			servletContext.setInitParameter("primefaces.THEME", "saga"); // Tema moderno do PrimeFaces
			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
			servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}