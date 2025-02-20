package com.redhat.composer.model.mongo.contentretrieverentites;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.redhat.composer.model.enums.ContentRetrieverType;

/**
 * Base Retriever Connection Entity.
 */
@SuppressWarnings("all")
@BsonDiscriminator
public class BaseRetrieverConnectionEntity {

  ContentRetrieverType contentRetrieverType;
  Integer maxResults;
  Double  minScore;

  /**
   * Constructor.
   */
  public BaseRetrieverConnectionEntity() {
  }

  /**
   * Constructor.
   * @param contentRetrieverType the ContentRetrieverType
   */
  public BaseRetrieverConnectionEntity(ContentRetrieverType contentRetrieverType) {
    this.contentRetrieverType = contentRetrieverType;
  }

  /**
   * Get the Content Retriever Type.
   * @return the ContentRetrieverType
   */
  public ContentRetrieverType getContentRetrieverType() {
    return this.contentRetrieverType;
  }

  public void setContentRetrieverType(ContentRetrieverType contentRetrieverType) {
    this.contentRetrieverType = contentRetrieverType;
  }

  /**
   * Set the Content Retriever Type.
   * @param contentRetrieverType the ContentRetrieverType
   * @return BaseRetrieverConnectionEntity
   */
  public BaseRetrieverConnectionEntity contentRetrieverType(ContentRetrieverType contentRetrieverType) {
    setContentRetrieverType(contentRetrieverType);
    return this;
  }

  public Integer getMaxResults() {
    return maxResults;
  }

  public void setMaxResults(Integer maxResults) {
    this.maxResults = maxResults;
  }

  public Double getMinScore() {
    return minScore;
  }

  public void setMinScore(Double minScore) {
    this.minScore = minScore;
  }

  @Override
  public String toString() {
    return "BaseRetrieverConnectionEntity [contentRetrieverType=" + contentRetrieverType + ", maxResults=" + maxResults
        + ", minScore=" + minScore + "]";
  }

  
  
}
