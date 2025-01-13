package com.redhat.composer.model.request;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

@SuppressWarnings("all")
public class LLMRequest {

  private String name;
  private String description;

  private String servingRuntimeType;
  private String modelType;

  private String url;
  private String apiKey;
  private String modelName;

  private Double tempature;
  private Integer maxTokens;


  public LLMRequest() {
  }

  public LLMRequest(String name, String description, String servingRuntimeType, String modelType, String url, String apiKey, String modelName, Double tempature, Integer maxTokens) {
    this.name = name;
    this.description = description;
    this.servingRuntimeType = servingRuntimeType;
    this.modelType = modelType;
    this.url = url;
    this.apiKey = apiKey;
    this.modelName = modelName;
    this.tempature = tempature;
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

  public String getServingRuntimeType() {
    return this.servingRuntimeType;
  }

  public void setServingRuntimeType(String servingRuntimeType) {
    this.servingRuntimeType = servingRuntimeType;
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

  public Double getTempature() {
    return this.tempature;
  }

  public void setTempature(Double tempature) {
    this.tempature = tempature;
  }

  public Integer getMaxTokens() {
    return this.maxTokens;
  }

  public void setMaxTokens(Integer maxTokens) {
    this.maxTokens = maxTokens;
  }

  public LLMRequest name(String name) {
    setName(name);
    return this;
  }

  public LLMRequest description(String description) {
    setDescription(description);
    return this;
  }

  public LLMRequest servingRuntimeType(String servingRuntimeType) {
    setServingRuntimeType(servingRuntimeType);
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

  public LLMRequest tempature(Double tempature) {
    setTempature(tempature);
    return this;
  }

  public LLMRequest maxTokens(Integer maxTokens) {
    setMaxTokens(maxTokens);
    return this;
  }

  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, servingRuntimeType, modelType, url, apiKey, modelName, tempature, maxTokens);
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", description='" + getDescription() + "'" +
      ", servingRuntimeType='" + getServingRuntimeType() + "'" +
      ", modelType='" + getModelType() + "'" +
      ", url='" + getUrl() + "'" +
      ", apiKey='" + getApiKey() + "'" +
      ", modelName='" + getModelName() + "'" +
      ", tempature='" + getTempature() + "'" +
      ", maxTokens='" + getMaxTokens() + "'" +
      "}";
  }


}