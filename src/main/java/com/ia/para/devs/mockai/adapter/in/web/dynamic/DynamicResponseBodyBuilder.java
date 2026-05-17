package com.ia.para.devs.mockai.adapter.in.web.dynamic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Constrói um payload de resposta de amostra a partir do schema JSON
 * persistido em EndpointResponseEntity.responseSchema.
 *
 * Suporta resolução de $ref local (ex: "#/components/schemas/Animal"),
 * allOf, oneOf, anyOf e geração de arrays com variação de enums.
 *
 * Comportamento de arrays:
 *   - Quando o schema items resolve para um objeto com campos enum,
 *     gera um item por valor de enum do primeiro campo enum encontrado,
 *     variando o valor em cada item.
 *   - Quando não há enum, gera 3 itens com valores distintos por índice.
 */
@Component
public class DynamicResponseBodyBuilder {

    private static final int MAX_REF_DEPTH = 10;
    /** Quantidade padrão de itens gerados em arrays sem enum discriminante. */
    private static final int DEFAULT_ARRAY_SIZE = 3;

    private final ObjectMapper objectMapper;

    public DynamicResponseBodyBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** Constrói o body sem resolução de $ref (compatibilidade retroativa). */
    public Object buildResponseBody(String schemaJson) {
        return buildResponseBody(schemaJson, null);
    }

    /**
     * Constrói o body resolvendo $ref usando o bloco components da spec.
     *
     * @param schemaJson     JSON do schema do endpoint (pode conter $ref)
     * @param componentsJson JSON do bloco "components" da spec (pode ser null)
     */
    public Object buildResponseBody(String schemaJson, String componentsJson) {
        if (schemaJson == null || schemaJson.isBlank()) {
            return null;
        }
        try {
            JsonNode schema     = objectMapper.readTree(schemaJson);
            JsonNode components = parseComponents(componentsJson);
            return buildSample(schema, components, new HashSet<>(), 0);
        } catch (IOException ex) {
            return null;
        }
    }

    // -------------------------------------------------------------------------
    // Parsing auxiliar
    // -------------------------------------------------------------------------

    private JsonNode parseComponents(String componentsJson) {
        if (componentsJson == null || componentsJson.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readTree(componentsJson);
        } catch (IOException ex) {
            return null;
        }
    }

    // -------------------------------------------------------------------------
    // Construção recursiva do sample
    // -------------------------------------------------------------------------

    /**
     * @param enumIndex índice cíclico usado para selecionar o valor de enum
     *                  quando o campo está dentro de um item de array.
     */
    private Object buildSample(JsonNode schema, JsonNode components, Set<String> visitedRefs, int enumIndex) {
        if (schema == null || schema.isNull()) {
            return null;
        }

        if (schema.isObject()) {
            // 1. $ref — resolve antes de tudo
            if (schema.has("$ref")) {
                return resolveRef(schema.get("$ref").asText(), components, visitedRefs, enumIndex);
            }

            // 2. allOf — mescla todas as propriedades
            if (schema.has("allOf")) {
                return buildAllOf(schema.get("allOf"), components, visitedRefs, enumIndex);
            }

            // 3. oneOf / anyOf — usa o primeiro schema
            if (schema.has("oneOf")) {
                JsonNode first = schema.get("oneOf").get(0);
                return first != null ? buildSample(first, components, visitedRefs, enumIndex) : null;
            }
            if (schema.has("anyOf")) {
                JsonNode first = schema.get("anyOf").get(0);
                return first != null ? buildSample(first, components, visitedRefs, enumIndex) : null;
            }

            // 4. enum — seleciona o valor pelo índice cíclico
            if (schema.has("enum") && schema.get("enum").isArray() && schema.get("enum").size() > 0) {
                JsonNode enumArray = schema.get("enum");
                int idx = enumIndex % enumArray.size();
                return sampleFromNode(enumArray.get(idx));
            }

            // 5. Tipos primitivos e compostos
            String type = extractType(schema);
            if ("object".equals(type) || schema.has("properties")) {
                return buildObject(schema, components, visitedRefs, enumIndex);
            }
            if ("array".equals(type) || schema.has("items")) {
                return buildArray(schema, components, visitedRefs);
            }
            if ("string".equals(type))  { return buildString(schema); }
            if ("integer".equals(type)) { return enumIndex; }
            if ("number".equals(type))  { return (double) enumIndex; }
            if ("boolean".equals(type)) { return (enumIndex % 2 == 0); }

            // 6. Fallback sem type declarado
            if (schema.has("properties")) {
                return buildObject(schema, components, visitedRefs, enumIndex);
            }
            if (schema.has("items")) {
                return buildArray(schema, components, visitedRefs);
            }

            return schema.toString();
        }

        if (schema.isArray()) {
            if (schema.size() > 0) {
                List<Object> values = new ArrayList<>();
                values.add(buildSample(schema.get(0), components, visitedRefs, enumIndex));
                return values;
            }
            return List.of();
        }

        if (schema.isTextual()) { return schema.asText(); }
        if (schema.isNumber())  { return schema.numberValue(); }
        if (schema.isBoolean()) { return schema.booleanValue(); }

        return null;
    }

    // -------------------------------------------------------------------------
    // Resolução de $ref
    // -------------------------------------------------------------------------

    private Object resolveRef(String ref, JsonNode components, Set<String> visitedRefs, int enumIndex) {
        if (ref == null || !ref.startsWith("#/")) {
            return null;
        }
        if (visitedRefs.size() >= MAX_REF_DEPTH || visitedRefs.contains(ref)) {
            return null;
        }

        JsonNode resolved = navigatePath(ref.substring(2), components);
        if (resolved == null) {
            return null;
        }

        Set<String> newVisited = new HashSet<>(visitedRefs);
        newVisited.add(ref);
        return buildSample(resolved, components, newVisited, enumIndex);
    }

    /**
     * Navega pelo caminho separado por "/" dentro do nó components.
     * Ex: "components/schemas/Animal" → components → schemas → Animal
     */
    private JsonNode navigatePath(String path, JsonNode components) {
        if (components == null) {
            return null;
        }
        String[] parts = path.split("/");
        if (parts.length < 2 || !"components".equals(parts[0])) {
            return null;
        }
        JsonNode current = components;
        for (int i = 1; i < parts.length; i++) {
            if (current == null || !current.isObject()) {
                return null;
            }
            current = current.get(parts[i]);
        }
        return current;
    }

    // -------------------------------------------------------------------------
    // allOf — mescla propriedades de todos os sub-schemas
    // -------------------------------------------------------------------------

    private Object buildAllOf(JsonNode allOfArray, JsonNode components, Set<String> visitedRefs, int enumIndex) {
        Map<String, Object> merged = new LinkedHashMap<>();
        for (JsonNode subSchema : allOfArray) {
            Object subSample = buildSample(subSchema, components, visitedRefs, enumIndex);
            if (subSample instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> subMap = (Map<String, Object>) subSample;
                merged.putAll(subMap);
            }
        }
        return merged.isEmpty() ? null : merged;
    }

    // -------------------------------------------------------------------------
    // Arrays — gera múltiplos itens variando enums
    // -------------------------------------------------------------------------

    /**
     * Gera um array de amostras.
     *
     * Estratégia:
     *   1. Resolve o schema de items (seguindo $ref se necessário).
     *   2. Se o schema resolvido contém campos enum, determina o tamanho
     *      máximo de enum encontrado e gera um item por valor, passando
     *      o índice para que cada item use um valor diferente.
     *   3. Caso contrário, gera DEFAULT_ARRAY_SIZE itens com índices distintos.
     */
    private Object buildArray(JsonNode schema, JsonNode components, Set<String> visitedRefs) {
        JsonNode items = schema.get("items");
        if (items == null) {
            return List.of();
        }

        // Resolve o schema de items para inspecionar enums
        JsonNode resolvedItems = resolveSchemaNode(items, components, visitedRefs);
        int count = detectEnumSize(resolvedItems, components, visitedRefs, new HashSet<>());
        if (count <= 0) {
            count = DEFAULT_ARRAY_SIZE;
        }

        List<Object> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(buildSample(items, components, visitedRefs, i));
        }
        return result;
    }

    /**
     * Resolve um nó de schema seguindo $ref uma vez (sem recursão profunda),
     * apenas para inspeção de estrutura.
     */
    private JsonNode resolveSchemaNode(JsonNode schema, JsonNode components, Set<String> visitedRefs) {
        if (schema == null) {
            return null;
        }
        if (schema.has("$ref")) {
            String ref = schema.get("$ref").asText();
            if (ref.startsWith("#/") && !visitedRefs.contains(ref)) {
                return navigatePath(ref.substring(2), components);
            }
        }
        return schema;
    }

    /**
     * Detecta o maior tamanho de enum encontrado nas propriedades diretas
     * do schema (incluindo propriedades que são $ref para enums).
     * Retorna 0 se nenhum enum for encontrado.
     */
    private int detectEnumSize(JsonNode schema, JsonNode components, Set<String> visitedRefs, Set<String> seen) {
        if (schema == null || schema.isNull()) {
            return 0;
        }

        // Schema é diretamente um enum
        if (schema.has("enum") && schema.get("enum").isArray()) {
            return schema.get("enum").size();
        }

        // Resolve $ref para inspecionar
        if (schema.has("$ref")) {
            String ref = schema.get("$ref").asText();
            if (ref.startsWith("#/") && !seen.contains(ref)) {
                Set<String> newSeen = new HashSet<>(seen);
                newSeen.add(ref);
                JsonNode resolved = navigatePath(ref.substring(2), components);
                return detectEnumSize(resolved, components, visitedRefs, newSeen);
            }
            return 0;
        }

        // Inspeciona propriedades do objeto
        int max = 0;
        JsonNode properties = schema.get("properties");
        if (properties != null && properties.isObject()) {
            for (JsonNode propSchema : properties) {
                int size = detectEnumSize(propSchema, components, visitedRefs, seen);
                if (size > max) {
                    max = size;
                }
            }
        }

        // allOf
        if (schema.has("allOf")) {
            for (JsonNode sub : schema.get("allOf")) {
                JsonNode resolved = resolveSchemaNode(sub, components, visitedRefs);
                int size = detectEnumSize(resolved, components, visitedRefs, seen);
                if (size > max) {
                    max = size;
                }
            }
        }

        return max;
    }

    // -------------------------------------------------------------------------
    // Construção de objetos
    // -------------------------------------------------------------------------

    private Object buildObject(JsonNode schema, JsonNode components, Set<String> visitedRefs, int enumIndex) {
        Map<String, Object> payload = new LinkedHashMap<>();
        JsonNode properties = schema.get("properties");
        if (properties != null && properties.isObject()) {
            properties.fields().forEachRemaining(entry ->
                    payload.put(entry.getKey(), buildSample(entry.getValue(), components, visitedRefs, enumIndex)));
        }

        if (payload.isEmpty() && schema.has("additionalProperties")) {
            JsonNode additionalProperties = schema.get("additionalProperties");
            if (additionalProperties.isBoolean() && additionalProperties.booleanValue()) {
                payload.put("propriedadeAdicional", "string");
            } else {
                payload.put("propriedadeAdicional",
                        buildSample(additionalProperties, components, visitedRefs, enumIndex));
            }
        }

        return payload;
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private String extractType(JsonNode schema) {
        if (schema.has("type") && schema.get("type").isTextual()) {
            return schema.get("type").asText();
        }
        return null;
    }

    private Object buildString(JsonNode schema) {
        if (schema.has("format")) {
            return schema.get("format").asText();
        }
        return "string";
    }

    private Object sampleFromNode(JsonNode node) {
        if (node == null || node.isNull())  { return null; }
        if (node.isTextual())               { return node.asText(); }
        if (node.isNumber())                { return node.numberValue(); }
        if (node.isBoolean())               { return node.booleanValue(); }
        if (node.isObject() || node.isArray()) {
            return objectMapper.convertValue(node, Object.class);
        }
        return node.toString();
    }
}
