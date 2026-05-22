package com.ia.para.devs.mockai.adapter.in.web.dynamic;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Testes unitários para DynamicResponseBodyBuilder.
 * Valida a construção de payloads de resposta a partir de schemas JSON.
 */
class DynamicResponseBodyBuilderTest {

    private final DynamicResponseBodyBuilder builder = new DynamicResponseBodyBuilder(new ObjectMapper());

    @Test
    @DisplayName("Deve retornar null para schema null")
    void shouldReturnNullForNullSchema() {
        Object result = builder.buildResponseBody(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve retornar null para schema vazio")
    void shouldReturnNullForBlankSchema() {
        Object result = builder.buildResponseBody("   ");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve retornar null para JSON inválido")
    void shouldReturnNullForInvalidJson() {
        Object result = builder.buildResponseBody("not-json");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Deve construir objeto simples com propriedades string e integer")
    @SuppressWarnings("unchecked")
    void shouldBuildSimpleObject() {
        String schema = "{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"age\":{\"type\":\"integer\"}}}";

        Object result = builder.buildResponseBody(schema);

        assertThat(result).isInstanceOf(Map.class);
        Map<String, Object> map = (Map<String, Object>) result;
        assertThat(map).containsKey("name");
        assertThat(map).containsKey("age");
        assertThat(map.get("name")).isEqualTo("string");
        assertThat(map.get("age")).isEqualTo(0);
    }

    @Test
    @DisplayName("Deve construir array com 3 itens por padrão")
    @SuppressWarnings("unchecked")
    void shouldBuildArrayWithDefaultSize() {
        String schema = "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\"}}}}";

        Object result = builder.buildResponseBody(schema);

        assertThat(result).isInstanceOf(List.class);
        List<Object> list = (List<Object>) result;
        assertThat(list).hasSize(3);
    }

    @Test
    @DisplayName("Deve resolver $ref local quando componentsJson é fornecido")
    @SuppressWarnings("unchecked")
    void shouldResolveLocalRef() {
        String schema = "{\"$ref\":\"#/components/schemas/Pet\"}";
        String components = "{\"schemas\":{\"Pet\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"}}}}}";

        Object result = builder.buildResponseBody(schema, components);

        assertThat(result).isInstanceOf(Map.class);
        Map<String, Object> map = (Map<String, Object>) result;
        assertThat(map).containsKey("name");
    }

    @Test
    @DisplayName("Deve gerar valor boolean")
    void shouldBuildBoolean() {
        String schema = "{\"type\":\"boolean\"}";

        Object result = builder.buildResponseBody(schema);

        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("Deve gerar valor number")
    void shouldBuildNumber() {
        String schema = "{\"type\":\"number\"}";

        Object result = builder.buildResponseBody(schema);

        assertThat(result).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Deve usar primeiro valor de enum")
    void shouldUseFirstEnumValue() {
        String schema = "{\"type\":\"string\",\"enum\":[\"active\",\"inactive\"]}";

        Object result = builder.buildResponseBody(schema);

        assertThat(result).isEqualTo("active");
    }

    @Test
    @DisplayName("Deve resolver allOf mesclando propriedades")
    @SuppressWarnings("unchecked")
    void shouldResolveAllOf() {
        String schema = "{\"allOf\":[{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\"}}},{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"}}}]}";

        Object result = builder.buildResponseBody(schema);

        assertThat(result).isInstanceOf(Map.class);
        Map<String, Object> map = (Map<String, Object>) result;
        assertThat(map).containsKeys("id", "name");
    }
}
