package com.redhat.composer.model.mongo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.redhat.composer.model.mongo.contentretrieverentites.BaseRetrieverConnectionEntity;

import io.quarkus.mongodb.panache.common.MongoEntity;

/**
 * AIGatewayConnectionEntity.
 */
@SuppressWarnings("all")
@MongoEntity(collection = "ai_gateway_connection")
public class AIGatewayConnectionEntity extends BaseEntity {


  String host;
  String path;
  String name;
  String description;

  Map<String, Object> messageProperties = new HashMap<>();


  public AIGatewayConnectionEntity() {
  }

  public AIGatewayConnectionEntity(String host, String path, String name, String description, Map<String,Object> messageProperties) {
    this.host = host;
    this.path = path;
    this.name = name;
    this.description = description;
    this.messageProperties = messageProperties;
  }

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPath() {
    return this.path;
  }

  public void setPath(String path) {
    this.path = path;
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

  public Map<String,Object> getMessageProperties() {
    return this.messageProperties;
  }

  public void setMessageProperties(Map<String,Object> messageProperties) {
    this.messageProperties = messageProperties;
  }

  public AIGatewayConnectionEntity host(String host) {
    setHost(host);
    return this;
  }

  public AIGatewayConnectionEntity path(String path) {
    setPath(path);
    return this;
  }

  public AIGatewayConnectionEntity name(String name) {
    setName(name);
    return this;
  }

  public AIGatewayConnectionEntity description(String description) {
    setDescription(description);
    return this;
  }

  public AIGatewayConnectionEntity messageProperties(Map<String,Object> messageProperties) {
    setMessageProperties(messageProperties);
    return this;
  }

  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, path, name, description, messageProperties);
  }

  @Override
  public String toString() {
    return "{" +
      " host='" + getHost() + "'" +
      ", path='" + getPath() + "'" +
      ", name='" + getName() + "'" +
      ", description='" + getDescription() + "'" +
      ", messageProperties='" + getMessageProperties() + "'" +
      "}";
  }

}
