package com.ia.para.devs.mockai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuração do ObjectMapper Jackson (com.fasterxml.jackson 2.x).
 *
 * O Spring Boot 4.0.6 autoconfigura o tools.jackson.ObjectMapper (Jackson 3.x),
 * mas os adapters e serviços do projeto utilizam com.fasterxml.jackson 2.x.
 * Este bean registra explicitamente o ObjectMapper 2.x no contexto Spring.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
