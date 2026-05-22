package com.ia.para.devs.mockai.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.QueryTimeoutException;

import com.ia.para.devs.mockai.domain.exception.PersistenceDeletionException;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.ApiSpecificationRepository;
import com.ia.para.devs.mockai.infrastructure.persistence.repository.TagRepository;

import jakarta.persistence.EntityManager;

/**
 * Testes unitários para SwaggerSpecDeletionAdapter.
 * Valida a ordem de deleção e tratamento de erros.
 */
@ExtendWith(MockitoExtension.class)
class SwaggerSpecDeletionAdapterTest {

    @Mock
    private ApiSpecificationRepository apiSpecificationRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SwaggerSpecDeletionAdapter adapter;

    @Test
    @DisplayName("Deve deletar na ordem correta: apiSpec → flush → tags → flush")
    void shouldDeleteInCorrectOrder() {
        assertThatCode(() -> adapter.deleteAll()).doesNotThrowAnyException();

        InOrder inOrder = inOrder(apiSpecificationRepository, entityManager, tagRepository);
        inOrder.verify(apiSpecificationRepository).deleteAll();
        inOrder.verify(entityManager).flush();
        inOrder.verify(tagRepository).deleteAllInBatch();
        inOrder.verify(entityManager).flush();
    }

    @Test
    @DisplayName("Deve lançar PersistenceDeletionException quando deleteAll falha")
    void shouldThrowPersistenceDeletionExceptionOnFailure() {
        doThrow(new QueryTimeoutException("timeout"))
                .when(apiSpecificationRepository).deleteAll();

        assertThatThrownBy(() -> adapter.deleteAll())
                .isInstanceOf(PersistenceDeletionException.class)
                .hasMessageContaining("Falha ao deletar dados anteriores");
    }

    @Test
    @DisplayName("Deve lançar PersistenceDeletionException quando flush falha")
    void shouldThrowPersistenceDeletionExceptionOnFlushFailure() {
        doThrow(new QueryTimeoutException("flush timeout"))
                .when(entityManager).flush();

        assertThatThrownBy(() -> adapter.deleteAll())
                .isInstanceOf(PersistenceDeletionException.class)
                .hasMessageContaining("Falha ao deletar dados anteriores");
    }
}
