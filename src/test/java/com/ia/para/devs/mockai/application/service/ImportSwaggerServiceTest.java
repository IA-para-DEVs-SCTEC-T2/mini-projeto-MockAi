package com.ia.para.devs.mockai.application.service;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;
import com.ia.para.devs.mockai.domain.exception.InvalidSwaggerContentException;
import com.ia.para.devs.mockai.domain.model.FileData;
import com.ia.para.devs.mockai.domain.port.in.DynamicRouteRegistrationUseCase;
import com.ia.para.devs.mockai.domain.port.in.PersistSwaggerSpecUseCase;
import com.ia.para.devs.mockai.domain.port.in.ValidateSwaggerContentUseCase;

/**
 * Testes unitários para ImportSwaggerService.
 * Valida a orquestração do fluxo de importação.
 */
@ExtendWith(MockitoExtension.class)
class ImportSwaggerServiceTest {

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PersistSwaggerSpecUseCase persistSwaggerSpecUseCase;

    @Mock
    private DynamicRouteRegistrationUseCase dynamicRouteRegistrationUseCase;

    @Mock
    private ValidateSwaggerContentUseCase validateSwaggerContentUseCase;

    @InjectMocks
    private ImportSwaggerService service;

    @Test
    @DisplayName("Deve orquestrar importação com sucesso: deserializar, validar, persistir e registrar rotas")
    void shouldOrchestrateImportSuccessfully() {
        String json = "{\"openapi\":\"3.0.1\",\"info\":{\"title\":\"Test\",\"version\":\"1.0\",\"description\":\"desc\"},\"paths\":{\"/pets\":{\"get\":{\"responses\":{\"200\":{\"description\":\"ok\"}}}}}}";
        FileData file = new FileData("spec.json", json.getBytes());
        UUID specId = UUID.randomUUID();

        when(persistSwaggerSpecUseCase.persist(any(OpenApiSpecDto.class))).thenReturn(specId);

        service.importSpec(file);

        verify(validateSwaggerContentUseCase).validate(any(OpenApiSpecDto.class));
        verify(persistSwaggerSpecUseCase).persist(any(OpenApiSpecDto.class));
        verify(dynamicRouteRegistrationUseCase).registerRoutes(specId);
    }

    @Test
    @DisplayName("Deve lançar InvalidSwaggerContentException quando JSON é inválido")
    void shouldThrowWhenJsonIsInvalid() {
        FileData file = new FileData("spec.json", "not-json".getBytes());

        assertThatThrownBy(() -> service.importSpec(file))
                .isInstanceOf(InvalidSwaggerContentException.class)
                .hasMessageContaining("inválido");

        verifyNoInteractions(validateSwaggerContentUseCase);
        verifyNoInteractions(persistSwaggerSpecUseCase);
    }

    @Test
    @DisplayName("Deve propagar exceção quando validação de conteúdo falha")
    void shouldPropagateWhenValidationFails() {
        String json = "{\"openapi\":\"3.0.1\",\"info\":{\"title\":\"Test\",\"version\":\"1.0\",\"description\":\"desc\"},\"paths\":{\"/pets\":{\"get\":{\"responses\":{\"200\":{\"description\":\"ok\"}}}}}}";
        FileData file = new FileData("spec.json", json.getBytes());

        doThrow(new InvalidSwaggerContentException("Campos ausentes"))
                .when(validateSwaggerContentUseCase).validate(any(OpenApiSpecDto.class));

        assertThatThrownBy(() -> service.importSpec(file))
                .isInstanceOf(InvalidSwaggerContentException.class);

        verifyNoInteractions(persistSwaggerSpecUseCase);
    }
}
