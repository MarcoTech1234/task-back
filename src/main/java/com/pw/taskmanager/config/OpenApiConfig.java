package com.pw.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Security scheme example for Bearer JWT
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement secReq = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info()
                        .title("TaskManager API")
                        .version("v1")
                        .description("API para gestão de tasks")
                        .contact(new Contact().name("Equipe Dev").email("dev@example.com"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                )
                // Registrar tags globais (ordem/descrição)
                .tags(List.of(
                        new Tag().name("Tasks").description("Operações relacionadas a tarefas")
                ))
                // Security (declare o nome usado nos controllers via @SecurityRequirement)
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", bearerScheme)
                )
                .addSecurityItem(secReq);
    }
}
