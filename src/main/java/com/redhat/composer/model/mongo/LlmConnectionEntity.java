package com.redhat.composer.model.mongo;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import io.quarkus.mongodb.panache.common.MongoEntity;

/**
 * LlmConnectionEntity
 */
// CHECKSTYLE 
@SuppressWarnings("all")
@MongoEntity(collection = "llm_connection")
public class LlmConnectionEntity extends BaseEntity {

  
  private String name;
  private String description;

  private String modelType;
  
  private String url;
  private String apiKey;
  private String modelName;

  private Double temperature;
  private Integer maxTokens;


  public LlmConnectionEntity() {
  }

  public LlmConnectionEntity(String name, String description, String modelType, String url, String apiKey, String modelName, Double temperature, Integer maxTokens) {
    this.name = name;
    this.description = description;
    this.modelType = modelType;
    this.url = url;
    this.apiKey = apiKey;
    this.modelName = modelName;
    this.temperature = temperature;
    this.maxTokens = maxTokens;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getModelType() {
    return this.modelType;
  }

  public void setModelType(String modelType) {
    this.modelType = modelType;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getApiKey() {
    return this.apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getModelName() {
    return this.modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public Double getTemperature() {
    return this.temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public Integer getMaxTokens() {
    return this.maxTokens;
  }

  public void setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
  }

  public LlmConnectionEntity name(String name) {
    setName(name);
    return this;
  }

  public LlmConnectionEntity description(String description) {
    setDescription(description);
    return this;
  }

  public LlmConnectionEntity modelType(String modelType) {
    setModelType(modelType);
    return this;
  }

  public LlmConnectionEntity url(String url) {
    setUrl(url);
    return this;
  }

  public LlmConnectionEntity apiKey(String apiKey) {
    setApiKey(apiKey);
    return this;
  }

  public LlmConnectionEntity modelName(String modelName) {
    setModelName(modelName);
    return this;
  }

  public LlmConnectionEntity temperature(Double temperature) {
    setTemperature(temperature);
    return this;
  }

  public LlmConnectionEntity maxTokens(Integer maxTokens) {
    setMaxTokens(maxTokens);
    return this;
  }

  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, modelType, url, apiKey, modelName, temperature, maxTokens);
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", description='" + getDescription() + "'" +
      ", modelType='" + getModelType() + "'" +
      ", url='" + getUrl() + "'" +
      ", apiKey='" + getApiKey() + "'" +
      ", modelName='" + getModelName() + "'" +
      ", temperature='" + getTemperature() + "'" +
      ", maxTokens='" + getMaxTokens() + "'" +
      "}";
  }


}