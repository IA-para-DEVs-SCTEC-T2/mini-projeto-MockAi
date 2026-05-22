Prompt: Configurar plugin JaCoCo no pom.xml para cobertura de testes com mínimo de 40%
Responsável: Dariel Verdecia Verdecia
Usuário: dariel-verdecia-verdecia
Data/hora: 2026-05-21 20:30:00

## Prompt original

Configurar o plugin JaCoCo (Java Code Coverage) no pom.xml do projeto MockAI para gerar relatórios de cobertura de código e validar que a cobertura mínima de 40% das linhas seja atingida. O plugin deve incluir as execuções prepare-agent (instrumentação), report (geração de relatório HTML na fase test) e check (validação de cobertura mínima na fase verify). A regra de cobertura deve ser aplicada no nível BUNDLE com counter LINE e value COVEREDRATIO com minimum 0.40. O relatório deve ser gerado em target/site/jacoco/index.html.
