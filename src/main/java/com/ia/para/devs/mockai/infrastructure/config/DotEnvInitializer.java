package com.ia.para.devs.mockai.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

/**
 * Inicializador de contexto que carrega variáveis do arquivo {@code .env} na raiz do projeto
 * e as disponibiliza como propriedades do Spring antes da inicialização completa da aplicação.
 *
 * <p>As variáveis definidas no {@code .env} são adicionadas com menor precedência do que
 * variáveis de ambiente reais do sistema operacional, garantindo que o ambiente de produção
 * possa sobrescrever os valores locais sem alteração de código.</p>
 *
 * <p>Se o arquivo {@code .env} não for encontrado, o inicializador é ignorado silenciosamente,
 * permitindo que a aplicação suba normalmente em ambientes onde as variáveis já estão
 * configuradas no sistema operacional (ex: CI/CD, containers).</p>
 */
public class DotEnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            Map<String, Object> envVars = new HashMap<>();
            dotenv.entries().forEach(entry -> envVars.put(entry.getKey(), entry.getValue()));

            if (!envVars.isEmpty()) {
                applicationContext.getEnvironment()
                        .getPropertySources()
                        .addLast(new MapPropertySource("dotenvProperties", envVars));
            }
        } catch (DotenvException e) {
            // arquivo .env ausente ou inválido — ignora silenciosamente
        }
    }
}
