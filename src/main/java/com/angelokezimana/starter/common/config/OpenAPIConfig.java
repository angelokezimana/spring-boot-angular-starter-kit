package com.angelokezimana.starter.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("kezangelo@gmail.com");
        contact.setName("Kezimana Aimé Angelo");
        contact.setUrl("https://angelokezimana.github.io/#/");

        License mitLicense = new License().name("MIT License");

        Info info = new Info()
                .title("Posta API")
                .version("0.1")
                .contact(contact)
                .description("RESTFul API using Spring Boot for learning purposes only")
                .license(mitLicense);

        Components components = new Components()
                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");


        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(info);
    }
}
