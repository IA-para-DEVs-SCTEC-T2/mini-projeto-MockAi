package com.ia.para.devs.mockai.application.util;

import java.util.Locale;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Utilitário de mapeamento de métodos HTTP definidos na especificação
 * para o enum RequestMethod do Spring MVC.
 */
public final class HttpMethodMapper {

    private HttpMethodMapper() {
        // Classe utilitária não instanciável.
    }

    public static RequestMethod map(String httpMethod) {
        if (httpMethod == null || httpMethod.isBlank()) {
            throw new IllegalArgumentException("HTTP method must not be null or blank");
        }

        switch (httpMethod.trim().toUpperCase(Locale.ROOT)) {
            case "GET":
                return RequestMethod.GET;
            case "POST":
                return RequestMethod.POST;
            case "PUT":
                return RequestMethod.PUT;
            case "DELETE":
                return RequestMethod.DELETE;
            case "PATCH":
                return RequestMethod.PATCH;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
        }
    }
}
