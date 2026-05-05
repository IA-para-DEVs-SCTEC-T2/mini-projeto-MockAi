package com.ia.para.devs.mockai.application.service;

import com.ia.para.devs.mockai.domain.port.MockDefinitionRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

/**
 * Serviço responsável por gerar slugs únicos e amigáveis para URLs a partir do título da spec.
 *
 * Regras:
 * - Converte o título para minúsculas
 * - Remove acentos e caracteres especiais
 * - Substitui espaços e separadores por hífen
 * - Se o slug base já existe, adiciona sufixo incremental: usuarios → usuarios-2 → usuarios-3
 *
 * Exemplos:
 *   "Usuarios API"     → usuarios
 *   "Usuarios API" (2ª vez) → usuarios-2
 *   "Fiscalização API" → fiscalizacao
 */
@Service
public class SlugGeneratorService {

    private final MockDefinitionRepository repository;

    public SlugGeneratorService(MockDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * Gera um slug único baseado no título da spec.
     *
     * @param title título extraído da spec OpenAPI (info.title)
     * @return slug único pronto para uso na URL
     */
    public String generateUniqueSlug(String title) {
        String base = toSlug(title);
        if (base.isBlank()) {
            base = "mock";
        }

        long count = repository.countBySlugStartingWith(base);
        if (count == 0) {
            return base;
        }
        // Incrementa até encontrar um slug livre
        long suffix = count + 1;
        while (repository.countBySlugStartingWith(base + "-" + suffix) > 0
                || repository.findBySlug(base + "-" + suffix).isPresent()) {
            suffix++;
        }
        return base + "-" + suffix;
    }

    /**
     * Converte um texto qualquer em slug URL-safe.
     * Ex: "Usuarios API v2" → "usuarios-api-v2"
     */
    public static String toSlug(String text) {
        if (text == null || text.isBlank()) return "";

        // Normaliza unicode e remove diacríticos (acentos)
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return normalized
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")   // remove caracteres não alfanuméricos
                .trim()
                .replaceAll("[\\s-]+", "-");         // espaços e hífens múltiplos → hífen único
    }
}
