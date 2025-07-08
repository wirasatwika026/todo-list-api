package com.example.todo_list_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Todo List API")
                        .description("REST API untuk mengelola daftar tugas (Todo List)")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Developer Team")
                                .email("developer@todoapi.com")
                                .url("https://github.com/yourrepo/todo-list-api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.todoapp.com")
                                .description("Production Server")));
    }
}
