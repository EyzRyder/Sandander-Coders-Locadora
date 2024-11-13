package com.ada.santander.coders.locadora.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${app.url}")
    private String serverUrl;

    @Value("${app.env}")
    private String environment;

    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";
    private static Info infos;

    private static void setInfos() {
        infos = new Info()
                .title("Sandander-Coders-Locadora")
                .version("1.0")
                .description("""
                    Este projeto foi desenvolvido para aplicar técnicas avançadas com frameworks modernos, com foco na construção de APIs REST seguras e escaláveis usando o ecossistema Spring. Ele abrange conteúdos fundamentais, como o Spring Framework, Spring Boot e Spring Data JPA, para gerenciar dados de forma eficiente. Utilizando o Spring Web para expor endpoints e o Spring Security para controle de acesso, o sistema também integra consultas customizadas com Query DSL, manipulação de expressões regulares (RegEx) e o uso de REST Clients para comunicações externas, proporcionando uma arquitetura flexível e adaptável para atender a diversas necessidades.
                    
                    Desenvolvida por:
                      - Gabriel Bessi
                      - Guilherme Bandeira
                      - Vinícios Augusto
                      - Vinícios Guedes
                    """);
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("""
                    Informe o token JWT para autenticação.
                    
                    Como obter o token:
                    1. Faça login através da rota /auth/login
                    2. Copie o token retornado
                    3. Clique no botão 'Authorize' acima
                    4. Cole o token no campo 'Value' (sem a palavra 'Bearer')
                    5. Clique em 'Authorize' e depois em 'Close'
                    """);
    }


    @Bean
    public OpenAPI customOpenAPI() {
        setInfos();

        List<Server> servers = new ArrayList<>();
        servers.add(new Server()
                .url(serverUrl)
                .description(environment + " Server"));

        return new OpenAPI()
                .info(infos)
                .servers(servers)
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, createSecurityScheme()));
    }
}
