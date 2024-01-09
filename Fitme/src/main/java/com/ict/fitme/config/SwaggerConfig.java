package com.ict.fitme.config;

import java.util.Vector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost:8080/swagger-ui/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ict.fitme.account.controller")
						// 컨트롤러 추가 등록하는 방법
						//.or(RequestHandlerSelectors.basePackage("com.ict.fitme.account.controller")) 
						//.or(RequestHandlerSelectors.basePackage("com.ict.fitme.account.controller"))
				)
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("REST API Documentation")
				.description("REST API 사용 설명서")
				.version("1.0")
				.termsOfServiceUrl("http://fitme.com/terms")
				.contact(new Contact("ICT 4!", "http://fitme.com", "your.email@example.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.extensions(new Vector<VendorExtension>())
				.build();
	}
	
}
