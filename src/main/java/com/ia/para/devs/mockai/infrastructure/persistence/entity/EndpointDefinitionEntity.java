package com.ia.para.devs.mockai.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

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
    List<TagEntity> tags;

    @OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PathParameterEntity> pathParameters;

    @OneToMany(mappedBy = "endpointDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    List<EndpointResponseEntity> responses;
}
