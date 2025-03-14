package com.redhat.composer.model.mongo.contentretrieverentites;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.redhat.composer.config.application.ContentRetrieverConfig;
import com.redhat.composer.model.enums.ContentRetrieverType;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

//import jakarta.inject.Singleton;

/**
 * Base Retriever Connection Entity.
 */
//@SuppressWarnings("all")
@BsonDiscriminator
@ApplicationScoped
public class BaseRetrieverConnectionEntity {


  // @Inject 
  // ContentRetrieverConfig config;

  ContentRetrieverType contentRetrieverType;

  // @ConfigProperty(name="conductor.retriever.document.max-results")
  // Integer confMaxResults;

  Integer maxResults;

  // @ConfigProperty(name="conductor.retriever.document.min-score")
  // Double confMinScore;

  Double  minScore;

  /**
   * Constructor.
   */
  public BaseRetrieverConnectionEntity() {
    // this.maxResults = config.document().maxResults();
    // this.minScore   = config.document().minScore();
    // this.maxResults = confMaxResults != null ? confMaxResults : maxResults;
    // this.minScore   = confMinScore   != null ? confMinScore   : minScore;
    // Log.debugf( "%n~~~~~~~~~~~~~~ maxResults[%d]  minScore[%f] %n", maxResults, minScore);
  }

  /**
   * Constructor.
   * @param contentRetrieverType the ContentRetrieverType
   */
  public BaseRetrieverConnectionEntity(ContentRetrieverType contentRetrieverType) {
    this.contentRetrieverType = contentRetrieverType;
  }

  @PostConstruct
  void init() {
    //this.maxResults = config.document().maxResults();
    //this.minScore   = config.document().minScore();
    // this.maxResults = confMaxResults != null ? confMaxResults : maxResults;
    // this.minScore   = confMinScore   != null ? confMinScore   : minScore;
    // Log.debugf( "%n+++++++++++++ maxResults[%d]  minScore[%f] %n", maxResults, minScore);

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
