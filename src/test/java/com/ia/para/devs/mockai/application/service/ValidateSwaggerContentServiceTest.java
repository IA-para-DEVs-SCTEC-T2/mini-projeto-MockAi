package com.ia.para.devs.mockai.application.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ia.para.devs.mockai.adapter.in.web.dto.InfoDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.PathItemDto;
import com.ia.para.devs.mockai.adapter.in.web.dto.ResponseDto;
import com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException;

/**
 * Testes unitários para ValidateSwaggerContentService.
 * Valida os campos obrigatórios de uma especificação OpenAPI.
 */
class ValidateSwaggerContentServiceTest {

    private final ValidateSwaggerContentService service = new ValidateSwaggerContentService();

    @Test
    @DisplayName("Deve aceitar spec válida com todos os campos obrigatórios")
    void shouldAcceptValidSpec() {
        OpenApiSpecDto spec = buildValidSpec();

        assertThatCode(() -> service.validate(spec)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve rejeitar spec sem campo openapi")
    void shouldRejectSpecWithoutOpenapi() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.setOpenapi(null);

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("openapi");
    }

    @Test
    @DisplayName("Deve rejeitar spec com openapi em branco")
    void shouldRejectSpecWithBlankOpenapi() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.setOpenapi("   ");

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("openapi");
    }

    @Test
    @DisplayName("Deve rejeitar spec sem bloco info")
    void shouldRejectSpecWithoutInfo() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.setInfo(null);

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("info");
    }

    @Test
    @DisplayName("Deve rejeitar spec sem info.title")
    void shouldRejectSpecWithoutInfoTitle() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.getInfo().setTitle(null);

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("info.title");
    }

    @Test
    @DisplayName("Deve rejeitar spec sem info.description")
    void shouldRejectSpecWithoutInfoDescription() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.getInfo().setDescription(null);

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("info.description");
    }

    @Test
    @DisplayName("Deve rejeitar spec sem paths")
    void shouldRejectSpecWithoutPaths() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.setPaths(null);

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("paths");
    }

    @Test
    @DisplayName("Deve rejeitar spec com paths vazio")
    void shouldRejectSpecWithEmptyPaths() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.setPaths(Collections.emptyMap());

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("paths");
    }

    @Test
    @DisplayName("Deve rejeitar spec com path sem métodos HTTP")
    void shouldRejectSpecWithPathWithoutMethods() {
        OpenApiSpecDto spec = buildValidSpec();
        spec.getPaths().put("/items", Collections.emptyMap());

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("/items");
    }

    @Test
    @DisplayName("Deve rejeitar spec com método sem responses")
    void shouldRejectSpecWithMethodWithoutResponses() {
        OpenApiSpecDto spec = buildValidSpec();
        PathItemDto pathItem = new PathItemDto();
        pathItem.setResponses(null);
        Map<String, PathItemDto> methods = new HashMap<>();
        methods.put("get", pathItem);
        spec.getPaths().put("/items", methods);

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("responses");
    }

    @Test
    @DisplayName("Deve acumular múltiplos erros em uma única exceção")
    void shouldAccumulateMultipleErrors() {
        OpenApiSpecDto spec = new OpenApiSpecDto();
        spec.setOpenapi(null);
        spec.setInfo(null);
        spec.setPaths(null);

        assertThatThrownBy(() -> service.validate(spec))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("openapi")
                .hasMessageContaining("info")
                .hasMessageContaining("paths");
    }

    private OpenApiSpecDto buildValidSpec() {
        OpenApiSpecDto spec = new OpenApiSpecDto();
        spec.setOpenapi("3.0.1");

        InfoDto info = new InfoDto();
        info.setTitle("Test API");
        info.setVersion("1.0.0");
        info.setDescription("A test API");
        spec.setInfo(info);

        PathItemDto pathItem = new PathItemDto();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setDescription("Success");
        Map<String, ResponseDto> responses = new HashMap<>();
        responses.put("200", responseDto);
        pathItem.setResponses(responses);

        Map<String, PathItemDto> methods = new HashMap<>();
        methods.put("get", pathItem);

        Map<String, Map<String, PathItemDto>> paths = new HashMap<>();
        paths.put("/pets", methods);
        spec.setPaths(paths);

        return spec;
    }
}
