package com.hawkscheck.hawkscheck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Marca a classe como de configuração do Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configura o CORS para permitir requisições do frontend
        registry.addMapping("/api/**")  // Aplique o CORS para todas as rotas que começam com "/api"
                .allowedOrigins("http://localhost:5173")  // Permite o acesso do frontend rodando em http://localhost:5173
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permite os métodos HTTP
                .allowedHeaders("Content-Type", "Authorization")  // Permite os cabeçalhos necessários
                .allowCredentials(true);  // Permite o envio de credenciais, como cookies, se necessário
    }
}
