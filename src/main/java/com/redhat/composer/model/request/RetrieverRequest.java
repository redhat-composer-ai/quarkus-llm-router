package com.redhat.composer.model.request;

import com.redhat.composer.model.request.retriever.BaseRetrieverRequest;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

@SuppressWarnings("all")
public class RetrieverRequest {

  BaseRetrieverRequest baseRetrieverRequest;
  String id;
  String embeddingType;

  String name;
  String description;


  public RetrieverRequest() {
  }

  public RetrieverRequest(BaseRetrieverRequest baseRetrieverRequest, String id,
                          String embeddingType, String name, String description) {
    this.baseRetrieverRequest = baseRetrieverRequest;
    this.id = id;
    this.embeddingType = embeddingType;
    this.name = name;
    this.description = description;
  }

  public BaseRetrieverRequest getBaseRetrieverRequest() {
    return this.baseRetrieverRequest;
  }

  public void setBaseRetrieverRequest(BaseRetrieverRequest baseRetrieverRequest) {
    this.baseRetrieverRequest = baseRetrieverRequest;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmbeddingType() {
    return this.embeddingType;
  }

  public void setEmbeddingType(String embeddingType) {
    this.embeddingType = embeddingType;
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

  public RetrieverRequest baseRetrieverRequest(BaseRetrieverRequest baseRetrieverRequest) {
    setBaseRetrieverRequest(baseRetrieverRequest);
    return this;
  }

  public RetrieverRequest id(String id) {
    setId(id);
    return this;
  }

  public RetrieverRequest embeddingType(String embeddingType) {
    setEmbeddingType(embeddingType);
    return this;
  }

  public RetrieverRequest name(String name) {
    setName(name);
    return this;
  }

  public RetrieverRequest description(String description) {
    setDescription(description);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseRetrieverRequest, id, embeddingType, name, description);
  }

  @Override
  public String toString() {
    return "{" +
        " baseRetrieverRequest='" + getBaseRetrieverRequest() + "'" +
        ", id='" + getId() + "'" +
        ", embeddingType='" + getEmbeddingType() + "'" +
        ", name='" + getName() + "'" +
        ", description='" + getDescription() + "'" +
        "}";
  }


}
