package com.ia.para.devs.mockai.infrastructure.persistence.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name = "endpoint_definition")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class EndpointDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String path;

    @Column(nullable = false, length = 20)
    String httpMethod;

    String summary;

    @Column(columnDefinition = "TEXT")
    String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "api_specification_id", nullable = false)
    ApiSpecificationEntity apiSpecification;

    @ManyToMany
    @JoinTable(
        name = "endpoint_tags",
        joinColumns = @JoinColumn(name = "endpoint_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<TagEntity> tags = new LinkedHashSet<>();

    @OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<PathParameterEntity> pathParameters = new LinkedHashSet<>();

    @OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<EndpointResponseEntity> responses = new LinkedHashSet<>();
}
