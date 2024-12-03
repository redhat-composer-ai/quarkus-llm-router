package com.redhat.composer.model.request;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

@SuppressWarnings("all")
public class ChatBotRequest{

  private String message = "";
  // This is where you can pass in chat history or other context
  private String context = "";
  private String systemMessage = "";
  private RetrieverRequest retrieverRequest = new RetrieverRequest();
  private LLMRequest modelRequest = new LLMRequest();


  public ChatBotRequest() {
  }

  public ChatBotRequest(String message, String context, String systemMessage, RetrieverRequest retrieverRequest, LLMRequest modelRequest) {
    this.message = message;
    this.context = context;
    this.systemMessage = systemMessage;
    this.retrieverRequest = retrieverRequest;
    this.modelRequest = modelRequest;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getContext() {
    return this.context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getSystemMessage() {
    return this.systemMessage;
  }

  public void setSystemMessage(String systemMessage) {
    this.systemMessage = systemMessage;
  }

  public RetrieverRequest getRetrieverRequest() {
    return this.retrieverRequest;
  }

  public void setRetrieverRequest(RetrieverRequest retrieverRequest) {
    this.retrieverRequest = retrieverRequest;
  }

  public LLMRequest getModelRequest() {
    return this.modelRequest;
  }

  public void setModelRequest(LLMRequest modelRequest) {
    this.modelRequest = modelRequest;
  }

  public ChatBotRequest message(String message) {
    setMessage(message);
    return this;
  }

  public ChatBotRequest context(String context) {
    setContext(context);
    return this;
  }

  public ChatBotRequest systemMessage(String systemMessage) {
    setSystemMessage(systemMessage);
    return this;
  }

  public ChatBotRequest retrieverRequest(RetrieverRequest retrieverRequest) {
    setRetrieverRequest(retrieverRequest);
    return this;
  }

  public ChatBotRequest modelRequest(LLMRequest modelRequest) {
    setModelRequest(modelRequest);
    return this;
  }

  @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, context, systemMessage, retrieverRequest, modelRequest);
  }

  @Override
  public String toString() {
    return "{" +
      " message='" + getMessage() + "'" +
      ", context='" + getContext() + "'" +
      ", systemMessage='" + getSystemMessage() + "'" +
      ", retrieverRequest='" + getRetrieverRequest() + "'" +
      ", modelRequest='" + getModelRequest() + "'" +
      "}";
  }

}