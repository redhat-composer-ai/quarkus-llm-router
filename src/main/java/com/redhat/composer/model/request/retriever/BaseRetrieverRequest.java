package com.redhat.composer.model.request.retriever;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@SuppressWarnings("all")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "contentRetrieverType", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = WeaviateRequest.class, name = "weaviate"),
    @JsonSubTypes.Type(value = ElasticsearchRequest.class, name = "elasticsearch")
})
public class BaseRetrieverRequest {

  String contentRetrieverType;
  Integer maxResults;
  Double  minScore;

  public BaseRetrieverRequest() {
  }


  
  public BaseRetrieverRequest(String contentRetrieverType, Integer maxResults, Double minScore) {
    this.contentRetrieverType = contentRetrieverType;
    this.maxResults = maxResults;
    this.minScore = minScore;
  }



  public BaseRetrieverRequest(String contentRetrieverType) {
    this.contentRetrieverType = contentRetrieverType;
  }

  public String getContentRetrieverType() {
    return this.contentRetrieverType;
  }

  public void setContentRetrieverType(String contentRetrieverType) {
    this.contentRetrieverType = contentRetrieverType;
  }

  public BaseRetrieverRequest contentRetrieverType(String contentRetrieverType) {
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
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }
}