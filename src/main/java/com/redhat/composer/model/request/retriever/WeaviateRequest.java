package com.redhat.composer.model.request.retriever;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.redhat.composer.model.enums.ContentRetrieverType;

@SuppressWarnings("all")
public class WeaviateRequest extends BaseRetrieverRequest {

  // Key of the value containing the text used for retrieval and passed into the LLM
  String textKey;

  // List of metadata fields to be retrieved as part of the content
  List<String> metadataFields = List.of("source","title");

  String index;

  String scheme;

  String host;

  String apiKey;
  

  public WeaviateRequest() {
    contentRetrieverType = ContentRetrieverType.WEAVIATE.getType();
  }

  public WeaviateRequest(String textKey, List<String> metadataFields, String index, String scheme, String host, String apiKey) {
    this.textKey = textKey;
    this.metadataFields = metadataFields;
    this.index = index;
    this.scheme = scheme;
    this.host = host;
    this.apiKey = apiKey;
  }

  public String getTextKey() {
    return this.textKey;
  }

  public void setTextKey(String textKey) {
    this.textKey = textKey;
  }

  public List<String> getMetadataFields() {
    return this.metadataFields;
  }

  public void setMetadataFields(List<String> metadataFields) {
    this.metadataFields = metadataFields;
  }

  public String getIndex() {
    return this.index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getScheme() {
    return this.scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getApiKey() {
    return this.apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public Integer getMaxResults() {
    return this.maxResults;
  }

  public void setMaxResults(Integer maxResults) {
    this.maxResults = maxResults;
  }

  public Double getMinScore() {
    return this.minScore;
  }

  public void setMinScore(Double minScore) {
    this.minScore = minScore;
  }


  public WeaviateRequest textKey(String textKey) {
    setTextKey(textKey);
    return this;
  }

  public WeaviateRequest metadataFields(List<String> metadataFields) {
    setMetadataFields(metadataFields);
    return this;
  }

  public WeaviateRequest index(String index) {
    setIndex(index);
    return this;
  }

  public WeaviateRequest scheme(String scheme) {
    setScheme(scheme);
    return this;
  }

  public WeaviateRequest host(String host) {
    setHost(host);
    return this;
  }

  public WeaviateRequest apiKey(String apiKey) {
    setApiKey(apiKey);
    return this;
  }

  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(textKey, metadataFields, index, scheme, host, apiKey);
  }

  @Override
  public String toString() {
    return "{" +
      " textKey='" + getTextKey() + "'" +
      ", metadataFields='" + getMetadataFields() + "'" +
      ", index='" + getIndex() + "'" +
      ", scheme='" + getScheme() + "'" +
      ", host='" + getHost() + "'" +
      ", apiKey='" + getApiKey() + "'" +
      "}";
  }

  
  
}
