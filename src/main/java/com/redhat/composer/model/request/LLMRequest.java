package com.redhat.composer.model.request;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.redhat.composer.config.llm.models.streaming.StreamingModelFactory;

@SuppressWarnings("all")
public class LLMRequest {

  private String id;
  
  private String name;
  private String description;

  private String modelType = StreamingModelFactory.DEFAULT_MODEL;
  
  private String url;
  private String apiKey;
  private String modelName;


  public LLMRequest() {
  }

  public LLMRequest(String name, String description, String modelType, String url, String apiKey, String modelName) {
    this.name = name;
    this.description = description;
    this.modelType = modelType;
    this.url = url;
    this.apiKey = apiKey;
    this.modelName = modelName;
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

  public LLMRequest name(String name) {
    setName(name);
    return this;
  }

  public LLMRequest description(String description) {
    setDescription(description);
    return this;
  }

  public LLMRequest modelType(String modelType) {
    setModelType(modelType);
    return this;
  }

  public LLMRequest url(String url) {
    setUrl(url);
    return this;
  }

  public LLMRequest apiKey(String apiKey) {
    setApiKey(apiKey);
    return this;
  }

  public LLMRequest modelName(String modelName) {
    setModelName(modelName);
    return this;
  }

  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, modelType, url, apiKey, modelName);
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
      "}";
  }

}