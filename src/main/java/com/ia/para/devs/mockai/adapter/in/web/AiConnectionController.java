package com.ia.para.devs.mockai.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ia.para.devs.mockai.domain.port.in.CheckAiConnectionUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller REST responsável por expor o endpoint de verificação de conectividade
 * com o serviço de IA. Depende exclusivamente do contrato {@link CheckAiConnectionUseCase},
 * sem referências a implementações concretas ou frameworks de IA.
 */
@Tag(name = "AI Connection", description = "Verificação de conectividade com o serviço de IA")
@RestController
public class AiConnectionController {

    private final CheckAiConnectionUseCase checkAiConnectionUseCase;

    /**
     * Cria uma instância do controller com o caso de uso de verificação de conexão.
     *
     * @param checkAiConnectionUseCase caso de uso responsável por verificar a conectividade com a IA
     */
    public AiConnectionController(CheckAiConnectionUseCase checkAiConnectionUseCase) {
        this.checkAiConnectionUseCase = checkAiConnectionUseCase;
    }

    /**
     * Verifica se a integração com o serviço de IA está operacional.
     * <p>
     * Retorna HTTP 200 com mensagem de sucesso se a conexão estiver funcional,
     * ou HTTP 503 com mensagem de indisponibilidade caso contrário.
     * </p>
     *
     * @return {@code 200 OK} com "Conexão com o serviço de IA está funcional" se conectado;
     *         {@code 503 Service Unavailable} com "Conexão com o serviço de IA está indisponível" caso contrário
     */
    @Operation(summary = "Verifica conectividade com o serviço de IA",
               description = "Testa se a integração com o Groq está operacional")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conexão com o serviço de IA está funcional"),
        @ApiResponse(responseCode = "503", description = "Conexão com o serviço de IA está indisponível")
    })
    @GetMapping("/test-ai-connection")
    public ResponseEntity<String> testAiConnection() {
        boolean connected = checkAiConnectionUseCase.checkConnection();
        if (connected) {
            return ResponseEntity.ok("Conexão com o serviço de IA está funcional");
        }
        return ResponseEntity.status(503).body("Conexão com o serviço de IA está indisponível");
    }
}
