package com.ia.para.devs.mockai.domain.model;

/**
 * Representa um parâmetro de um endpoint extraído da especificação OpenAPI.
 * Pode ser de localização: path, query, header ou cookie.
 */
public class EndpointParameter {

    private String name;
    private String in;        // path | query | header | cookie
    private String description;
    private boolean required;
    private String type;

    public EndpointParameter() {}

    public EndpointParameter(String name, String in, String description, boolean required, String type) {
        this.name = name;
        this.in = in;
        this.description = description;
        this.required = required;
        this.type = type;
    }

    public String getName() { return name; }
    public String getIn() { return in; }
    public String getDescription() { return description; }
    public boolean isRequired() { return required; }
    public String getType() { return type; }

    public void setName(String name) { this.name = name; }
    public void setIn(String in) { this.in = in; }
    public void setDescription(String description) { this.description = description; }
    public void setRequired(boolean required) { this.required = required; }
    public void setType(String type) { this.type = type; }
}
