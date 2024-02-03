package com.sampleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
	@Bean
	public Docket swaggerConfig() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				   .select()
				   .apis(RequestHandlerSelectors.basePackage("com.sampleproject"))
				   .paths(PathSelectors.any())
				   .build()
				   .apiInfo(Data());
	}
	
	private ApiInfo Data() {
		return new  ApiInfoBuilder().title("Add_To_Cart - spring boot swagger configuration").description("").version("2.7.0")
				    .license("Apache License Version 2.0").licenseUrl("http://www.apche.org/licenses/LICENSE-2.0\"")
				    .contact(new Contact("", "", "")).build();
	}
	
}
