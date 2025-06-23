package com.hawkscheck.hawkscheck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI hawksOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HawksCheck API")
                        .description("API documentation for HawksCheck")
                        .version("v1.0"));
    }
}
