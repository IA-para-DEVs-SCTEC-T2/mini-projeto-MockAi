package com.ia.para.devs.mockai.api.controller;

import com.ia.para.devs.mockai.application.service.MockResolverService;
import com.ia.para.devs.mockai.domain.model.MockEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller responsável por executar chamadas aos endpoints mock cadastrados.
 * Recebe requisições dinâmicas e retorna as respostas simuladas definidas na spec.
 */
@RestController
@RequestMapping("/mock")
@Tag(name = "Executor", description = "Execução de endpoints mock simulados")
public class MockExecutorController {

    private final MockResolverService mockResolverService;

    public MockExecutorController(MockResolverService mockResolverService) {
        this.mockResolverService = mockResolverService;
    }

    @RequestMapping(value = "/{mockId}/**", method = {
            RequestMethod.GET, RequestMethod.POST,
            RequestMethod.PUT, RequestMethod.DELETE,
            RequestMethod.PATCH
    })
    @Operation(summary = "Executar endpoint mock", description = "Executa uma chamada a um endpoint mock e retorna a resposta simulada")
    @ApiResponse(responseCode = "200", description = "Resposta simulada retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Mock ou endpoint não encontrado")
    public ResponseEntity<String> execute(
            @PathVariable UUID mockId,
            @RequestAttribute("javax.servlet.forward.request_uri") String fullPath,
            @RequestHeader(value = "X-HTTP-Method", defaultValue = "GET") String httpMethod,
            jakarta.servlet.http.HttpServletRequest request) {

        String path = extractPath(request.getRequestURI(), mockId.toString());
        String method = request.getMethod();

        MockEndpoint endpoint = mockResolverService.resolve(mockId, path, method);

        return ResponseEntity
                .status(endpoint.getResponseStatus())
                .body(endpoint.getResponseBody());
    }

    private String extractPath(String requestUri, String mockId) {
        String prefix = "/mock/" + mockId;
        return requestUri.startsWith(prefix) ? requestUri.substring(prefix.length()) : requestUri;
    }
}
