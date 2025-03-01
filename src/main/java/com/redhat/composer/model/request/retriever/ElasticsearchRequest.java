package com.redhat.composer.model.request.retriever;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.redhat.composer.model.enums.ContentRetrieverType;

@SuppressWarnings("all")
public class ElasticsearchRequest extends BaseRetrieverRequest {

  // List of metadata fields to be retrieved as part of the content
  List<String> metadataFields = List.of("source","title");

  String index;

  String host;

  String user;

  String password;


  public ElasticsearchRequest() {
    super();
  }

  public ElasticsearchRequest(List<String> metadataFields, String index, String host, String user, String password) {
    super();
    this.metadataFields = metadataFields;
    this.index = index;
    this.host = host;
    this.user = user;
    this.password = password;
  }

  public ElasticsearchRequest(String contentRetrieverType, Integer maxResults, Double minScore,
      List<String> metadataFields, String index, String host, String user, String password) {
    super(contentRetrieverType, maxResults, minScore);
    this.metadataFields = metadataFields;
    this.index = index;
    this.host = host;
    this.user = user;
    this.password = password;
  }

  public ElasticsearchRequest(String contentRetrieverType, List<String> metadataFields, String index, String host,
      String user, String password) {
    super(contentRetrieverType);
    this.metadataFields = metadataFields;
    this.index = index;
    this.host = host;
    this.user = user;
    this.password = password;
  }

  public ElasticsearchRequest(String contentRetrieverType) {
    super(contentRetrieverType);
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

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public ElasticsearchRequest metadataFields(List<String> metadataFields) {
    setMetadataFields(metadataFields);
    return this;
  }

  public ElasticsearchRequest index(String index) {
    setIndex(index);
    return this;
  }

  public ElasticsearchRequest host(String host) {
    setHost(host);
    return this;
  }

  public ElasticsearchRequest user(String user) {
    setUser(user);
    return this;
  }

  public ElasticsearchRequest password(String password) {
    setPassword(password);
    return this;
  }




  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadataFields, index, host, user, password);
  }

  @Override
  public String toString() {
    return "ElasticsearchRequest [metadataFields=" + metadataFields + ", index=" + index + ", host=" + host + ", user="
        + user + ", password=" + password + ", contentRetrieverType=" + contentRetrieverType + ", maxResults="
        + maxResults + ", minScore=" + minScore + "]";
  }



}
