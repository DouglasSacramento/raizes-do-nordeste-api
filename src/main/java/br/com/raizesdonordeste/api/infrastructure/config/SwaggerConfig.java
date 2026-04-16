package br.com.raizesdonordeste.api.infrastructure.config;

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
                        .title("API - Raízes do Nordeste")
                        .description("API REST para gestão de pedidos e cozinha do restaurante Raízes do Nordeste.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Douglas Sacramento")
                                .email("douglasj.sacramento@gmail.com")));
    }
}