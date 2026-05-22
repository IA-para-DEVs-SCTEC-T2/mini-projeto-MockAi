package com.ia.para.devs.mockai.application.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Testes unitários para HttpMethodMapper.
 * Valida o mapeamento de strings HTTP para RequestMethod do Spring.
 */
class HttpMethodMapperTest {

    @ParameterizedTest
    @CsvSource({
            "GET, GET",
            "POST, POST",
            "PUT, PUT",
            "DELETE, DELETE",
            "PATCH, PATCH",
            "get, GET",
            "post, POST",
            "  GET  , GET"
    })
    @DisplayName("Deve mapear métodos HTTP válidos corretamente")
    void shouldMapValidHttpMethods(String input, RequestMethod expected) {
        RequestMethod result = HttpMethodMapper.map(input);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException para método null")
    void shouldThrowForNullMethod() {
        assertThatThrownBy(() -> HttpMethodMapper.map(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null or blank");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException para método vazio")
    void shouldThrowForEmptyMethod() {
        assertThatThrownBy(() -> HttpMethodMapper.map(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null or blank");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException para método não suportado")
    void shouldThrowForUnsupportedMethod() {
        assertThatThrownBy(() -> HttpMethodMapper.map("OPTIONS"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unsupported HTTP method");
    }
}
