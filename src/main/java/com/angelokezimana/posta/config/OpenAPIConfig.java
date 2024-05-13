package com.angelokezimana.posta.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

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

        return new OpenAPI().info(info);
    }
}
