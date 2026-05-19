package com.ia.para.devs.mockai.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "path_parameter")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class PathParameterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String name;

    /** Localização do parâmetro conforme OpenAPI: "path", "query", "header" ou "cookie". */
    @Column(nullable = false, length = 20)
    String paramIn;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(nullable = false, length = 100)
    String type;

    /** Formato do schema conforme OpenAPI (ex: "uuid", "int64", "date-time"). Pode ser nulo. */
    @Column(length = 100)
    String format;

    @Column(nullable = false)
    Boolean required;

    @ManyToOne(optional = false)
    @JoinColumn(name = "endpoint_definition_id", nullable = false)
    EndpointDefinitionEntity endpointDefinition;
}
