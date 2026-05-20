package com.todoapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestion de Tâches")
                        .version("1.0.0")
                        .description("API REST permettant de gérer une liste de tâches (To-Do List). "
                                + "Fonctionnalités : créer, lire, mettre à jour et supprimer des tâches "
                                + "avec filtrage par statut.")
                        .contact(new Contact()
                                .name("KFOKAM48")
                                .url("https://github.com/kfokam48")));
    }
}
