package com.redhat.composer.model.request.retriever;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.redhat.composer.model.enums.ContentRetrieverType;

@SuppressWarnings("all")
public class ElasticsearchRequest extends BaseRetrieverRequest {

  // Key of the value containing the text used for retrieval and passed into the LLM
  String textKey;

  // List of metadata fields to be retrieved as part of the content
  List<String> metadataFields = List.of("source","title");

  String index;

  String host;

  String user;

  String password;
  

  public ElasticsearchRequest() {
    contentRetrieverType = ContentRetrieverType.ELASTICSEARCH.getType();
  }

  public ElasticsearchRequest(String textKey, List<String> metadataFields, String index, String host, String user, String password) {
    this.textKey = textKey;
    this.metadataFields = metadataFields;
    this.index = index;
    this.host = host;
    this.user = user;
    this.password = password;
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

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getUser() {
    return this.user;
  }

  public String getPassword() {
    return this.password;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ElasticsearchRequest textKey(String textKey) {
    setTextKey(textKey);
    return this;
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
    password(password);
    return this;
  }
  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(textKey, metadataFields, index, host, user, password);
  }

  @Override
  public String toString() {
    return "{" +
      " textKey='" + getTextKey() + "'" +
      ", metadataFields='" + getMetadataFields() + "'" +
      ", index='" + getIndex() + "'" +
      ", host='" + getHost() + "'" +
      ", user='" + getUser() + "'" +
      ", password='" + getPassword() + "'" +
      "}";
  }

  
  
}
