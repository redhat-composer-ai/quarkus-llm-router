package com.redhat.composer.model.mongo;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.redhat.composer.model.mongo.contentretrieverentites.BaseRetrieverConnectionEntity;

import io.quarkus.mongodb.panache.common.MongoEntity;

/**
 * RetrieverConnectionEntity
 */
@SuppressWarnings("all")
@MongoEntity(collection = "retriever_connection")
public class RetrieverConnectionEntity extends BaseEntity {


  BaseRetrieverConnectionEntity connectionEntity;
  String embeddingType;

  String name;
  String description;
  Integer maxResults;
  Double  minScore;

  public RetrieverConnectionEntity() {
  }

  public RetrieverConnectionEntity(BaseRetrieverConnectionEntity connectionEntity, String embeddingType, String name, String description) {
    this.connectionEntity = connectionEntity;
    this.embeddingType = embeddingType;
    this.name = name;
    this.description = description;
    this.maxResults  = connectionEntity.getMaxResults();
    this.minScore    = connectionEntity.getMinScore();
  }

  public BaseRetrieverConnectionEntity getConnectionEntity() {
    return this.connectionEntity;
  }

  public void setConnectionEntity(BaseRetrieverConnectionEntity connectionEntity) {
    this.connectionEntity = connectionEntity;
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

  public RetrieverConnectionEntity connectionEntity(BaseRetrieverConnectionEntity connectionEntity) {
    setConnectionEntity(connectionEntity);
    return this;
  }

  public RetrieverConnectionEntity embeddingType(String embeddingType) {
    setEmbeddingType(embeddingType);
    return this;
  }

  public RetrieverConnectionEntity name(String name) {
    setName(name);
    return this;
  }

  public RetrieverConnectionEntity description(String description) {
    setDescription(description);
    return this;
  }

  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connectionEntity, embeddingType, name, description);
  }

  @Override
  public String toString() {
    return "{" +
      " connectionEntity='" + getConnectionEntity() + "'" +
      ", embeddingType='" + getEmbeddingType() + "'" +
      ", name='" + getName() + "'" +
      ", description='" + getDescription() + "'" +
      "}";
  }

}
