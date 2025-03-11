package com.example.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("lofee-shop API Documentation")
				.description("lofee-shop Swagger API 명세서입니다.")
				.version("1.0.0"))

			//security 설정 추
			.addSecurityItem(new SecurityRequirement().addList("accessToken"))
			.addSecurityItem(new SecurityRequirement().addList("refreshToken"))
			.components(new Components()
				.addSecuritySchemes("accessToken",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
						.in(SecurityScheme.In.HEADER)
						.name("Authorization")) // Authorization 헤더를 통해 Access Token 사용
				.addSecuritySchemes("refreshToken",
					new SecurityScheme()
						.type(SecurityScheme.Type.APIKEY)
						.in(SecurityScheme.In.COOKIE)
						.name("refreshToken"))); // 쿠키 기반 Refresh Token 사용

	}

}
