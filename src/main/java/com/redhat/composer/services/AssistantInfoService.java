package com.redhat.composer.services;

import java.util.List;
import java.util.stream.Stream;

import org.bson.types.ObjectId;

import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.request.AssistantCreationRequest;
import com.redhat.composer.model.request.LLMRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.response.AssistantResponse;
import com.redhat.composer.util.mappers.MapperUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * AssistantInfoService.
 */
@ApplicationScoped
public class AssistantInfoService {

  @Inject
  MapperUtil mapperUtil;

  /**
   * Create an Assistant.
   *
   * @param request the AssistantCreationRequest
   * @return the AssistantEntity
   */
  public AssistantEntity createAssistant(AssistantCreationRequest request) {
    AssistantEntity assistant = new AssistantEntity();
    LlmConnectionEntity llm = (LlmConnectionEntity) LlmConnectionEntity.findByIdOptional(
            new ObjectId(request.getLlmConnectionId()))
        .orElseThrow(() -> new IllegalArgumentException("LLM Connection not found"));

    assistant.setLlmConnectionId(llm.id);

    if (request.getRetrieverConnectionId() != null) {
      RetrieverConnectionEntity retriever = (RetrieverConnectionEntity) RetrieverConnectionEntity
          .findByIdOptional(new ObjectId(request.getRetrieverConnectionId()))
          .orElseThrow(() -> new IllegalArgumentException("Retriever Connection not found"));
      assistant.setRetrieverConnectionId(retriever.id);
    }
    assistant.setName(request.getName());
    assistant.setDisplayName(request.getDisplayName());
    assistant.setDescription(request.getDescription());
    assistant.setUserPrompt(request.getUserPrompt());
    assistant.setExampleQuestions(request.getExampleQuestions());
    assistant.persist();
    return assistant;
  }

  /**
   * Get all Assistants.
   *
   * @return a list of AssistantResponse
   */
  public List<AssistantResponse> getAssistant() {
    Stream<AssistantEntity> stream = AssistantEntity.streamAll();
    return stream.map(entity -> {
          AssistantResponse response = new AssistantResponse();
          response.id = entity.id;
          response.setName(entity.getName());
          response.setDisplayName(entity.getDisplayName());
          response.setDescription(entity.getDescription());
          response.setUserPrompt(entity.getUserPrompt());
          response.setExampleQuestions(entity.getExampleQuestions());
          response.setLlmConnection(LlmConnectionEntity.findById(entity.getLlmConnectionId()));
          response.setRetrieverConnection(RetrieverConnectionEntity.findById(entity.getRetrieverConnectionId()));
          return response;
        }
    ).toList();
  }

  /**
   * Create a RetrieverConnectionEntity.
   *
   * @param request the RetrieverRequest
   * @return the RetrieverConnectionEntity
   */
  public RetrieverConnectionEntity createRetrieverConnectionEntity(RetrieverRequest request) {
    RetrieverConnectionEntity entity = mapperUtil.toEntity(request);
    entity.persist();
    return entity;
  }

  public List<RetrieverConnectionEntity> getRetrieverConnections() {
    return RetrieverConnectionEntity.listAll();
  }

  /**
   * Create a LLMConnectionEntity.
   *
   * @param request the LLMRequest
   * @return the LLMConnectionEntity
   */
  public LlmConnectionEntity createLlmConnection(LLMRequest request) {
    LlmConnectionEntity entity = new LlmConnectionEntity();
    entity.setName(request.getName());
    entity.setDescription(request.getDescription());
    entity.setUrl(request.getUrl());
    entity.setApiKey(request.getApiKey());
    entity.setModelName(request.getModelName());
    entity.persist();
    return entity;
  }

  /**
   * Get all LLMConnections.
   *
   * @return a list of LlmConnectionEntity
   */
  public List<LlmConnectionEntity> getLlmConnections() {
    return LlmConnectionEntity.listAll();
  }

}
