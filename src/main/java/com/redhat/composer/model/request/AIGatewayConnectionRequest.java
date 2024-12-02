package com.redhat.composer.model.request;

import com.redhat.composer.model.request.retriever.BaseRetrieverRequest;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

@SuppressWarnings("all")
public class AIGatewayConnectionRequest  {

  String host;
  String path;
  String name;
  String description;

  Map<String, Object> messageProperties;


  public AIGatewayConnectionRequest() {
  }

  public AIGatewayConnectionRequest(String host, String path, String name, String description, Map<String,Object> messageProperties) {
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

  public AIGatewayConnectionRequest host(String host) {
    setHost(host);
    return this;
  }

  public AIGatewayConnectionRequest path(String path) {
    setPath(path);
    return this;
  }

  public AIGatewayConnectionRequest name(String name) {
    setName(name);
    return this;
  }

  public AIGatewayConnectionRequest description(String description) {
    setDescription(description);
    return this;
  }

  public AIGatewayConnectionRequest messageProperties(Map<String,Object> messageProperties) {
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
