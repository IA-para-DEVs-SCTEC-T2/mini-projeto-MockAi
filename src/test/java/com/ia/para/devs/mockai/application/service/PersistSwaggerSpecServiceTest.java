package com.ia.para.devs.mockai.application.service;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ia.para.devs.mockai.adapter.in.web.dto.OpenApiSpecDto;
import com.ia.para.devs.mockai.domain.port.out.PersistSwaggerSpecPort;

/**
 * Testes unitários para PersistSwaggerSpecService.
 * Valida a delegação ao port de saída.
 */
@ExtendWith(MockitoExtension.class)
class PersistSwaggerSpecServiceTest {

    @Mock
    private PersistSwaggerSpecPort persistSwaggerSpecPort;

    @InjectMocks
    private PersistSwaggerSpecService service;

    @Test
    @DisplayName("Deve delegar persistência ao port e retornar UUID gerado")
    void shouldDelegatePersistenceToPort() {
        OpenApiSpecDto spec = new OpenApiSpecDto();
        UUID expectedId = UUID.randomUUID();
        when(persistSwaggerSpecPort.persist(spec)).thenReturn(expectedId);

        UUID result = service.persist(spec);

        assertThat(result).isEqualTo(expectedId);
        verify(persistSwaggerSpecPort).persist(spec);
    }
}
