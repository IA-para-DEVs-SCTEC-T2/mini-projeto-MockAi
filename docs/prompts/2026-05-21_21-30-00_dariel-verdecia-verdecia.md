Prompt: Criar testes unitários para SwaggerSpecDeletionAdapter validando ordem de deleção e tratamento de erros
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 21:30:00

## Prompt original

Implementar testes unitários para a classe SwaggerSpecDeletionAdapter da camada infrastructure.persistence.adapter. Os testes devem cobrir: verificação da ordem correta de deleção usando Mockito InOrder (apiSpecificationRepository.deleteAll() → entityManager.flush() → tagRepository.deleteAllInBatch() → entityManager.flush()); lançamento de PersistenceDeletionException quando deleteAll() falha com DataAccessException; e lançamento de PersistenceDeletionException quando flush() falha. Utilizar Mockito com @Mock para ApiSpecificationRepository, TagRepository e EntityManager, e doThrow() com QueryTimeoutException para simular falhas.
