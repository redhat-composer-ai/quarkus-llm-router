package com.redhat.composer.model.mongo.contentretrieverentites;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import com.redhat.composer.model.enums.ContentRetrieverType;

import java.util.List;
import java.util.Objects;

/**
 * Elasticsearch Connection Entity.
 */
@SuppressWarnings("all")
@BsonDiscriminator("Elasticsearch")
public class ElasticsearchConnectionEntity extends BaseRetrieverConnectionEntity {

  // Key of the value containing the text used for retrieval and passed into the LLM
  String textKey;

  // List of metadata fields to be retrieved as part of the content
  List<String> metadataFields = List.of("source");

  String index;

  String host;

  String user;

  String password;

  public ElasticsearchConnectionEntity() {
    contentRetrieverType = ContentRetrieverType.ELASTICSEARCH;
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

  public ElasticsearchConnectionEntity textKey(String textKey) {
    setTextKey(textKey);
    return this;
  }

  public ElasticsearchConnectionEntity metadataFields(List<String> metadataFields) {
    setMetadataFields(metadataFields);
    return this;
  }

  public ElasticsearchConnectionEntity index(String index) {
    setIndex(index);
    return this;
  }

  public ElasticsearchConnectionEntity host(String host) {
    setHost(host);
    return this;
  }

  public ElasticsearchConnectionEntity user(String user) {
    setUser(user);
    return this;
  }

  public ElasticsearchConnectionEntity password(String password) {
    setPassword(password);
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
      ", apiKey='" + getUser() + "'" +
      ", apiKey='" + getPassword() + "'" +
      "}";
  }
  
  
}
