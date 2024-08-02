package com.shiv;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "payload")
public class JsonPayload {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    // Default constructor needed for JAXB
    public JsonPayload() {
    }

    // Parameterized constructor
    public JsonPayload(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "JsonPayload{" +
               "name='" + name + '\'' +
               ", value='" + value + '\'' +
               '}';
    }
}
