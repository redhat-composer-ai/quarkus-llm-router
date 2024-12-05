package com.redhat.composer.services;

import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.request.AssistantCreationRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.response.AssistantResponse;
import com.redhat.composer.util.mappers.MapperUtil;
import io.quarkus.runtime.util.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Stream;

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
  public AssistantResponse createUpdateAssistant(AssistantCreationRequest request) {
    AssistantEntity assistant = new AssistantEntity();
    LlmConnectionEntity llm = (LlmConnectionEntity) LlmConnectionEntity.findByIdOptional(
            new ObjectId(request.getLlmConnectionId()))
        .orElseThrow(() -> new IllegalArgumentException("LLM Connection not found"));

    assistant.setLlmConnectionId(llm.id);

    RetrieverConnectionEntity retriever = null;
    if (request.getRetrieverConnectionId() != null) {
      retriever = (RetrieverConnectionEntity) RetrieverConnectionEntity
          .findByIdOptional(new ObjectId(request.getRetrieverConnectionId()))
          .orElseThrow(() -> new IllegalArgumentException("Retriever Connection not found"));
      assistant.setRetrieverConnectionId(retriever.id);
    }
    assistant.setName(request.getName());
    assistant.setDisplayName(request.getDisplayName());
    assistant.setDescription(request.getDescription());
    assistant.setUserPrompt(request.getUserPrompt());
    assistant.setExampleQuestions(request.getExampleQuestions());


    if (!StringUtil.isNullOrEmpty(request.getId())) {
      assistant.id = new ObjectId(request.getId());
      assistant.update();
    } else {
      assistant.persist();
    }


    AssistantResponse response = new AssistantResponse();
    response.id = assistant.id;
    response.setName(assistant.getName());
    response.setDisplayName(assistant.getDisplayName());
    response.setDescription(assistant.getDescription());
    response.setUserPrompt(assistant.getUserPrompt());
    response.setExampleQuestions(assistant.getExampleQuestions());
    response.setLlmConnection(llm);
    response.setRetrieverConnection(retriever);

    return response;
  }

  /**
   * Get all Assistants.
   *
   * @return a list of AssistantResponse
   */
  public List<AssistantResponse> getAssistants() {
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
   * Delete assistant.
   *
   * @param assistantObjectId the id
   */
  public void deleteAssistant(ObjectId assistantObjectId) {
    getAssistant(assistantObjectId);
    AssistantEntity.deleteById(assistantObjectId);
  }

  /**
   * Get single assistant.
   *
   * @param assistantObjectId the objectId
   * @return entity
   */
  public AssistantEntity getAssistant(ObjectId assistantObjectId) {
    return (AssistantEntity) AssistantEntity.findByIdOptional(assistantObjectId)
        .orElseThrow(
            () -> new NotFoundException(
                "Assistant with object id {" + assistantObjectId.toHexString() + "} not found"));
  }

  /**
   * Create a RetrieverConnectionEntity.
   *
   * @param request the RetrieverRequest
   * @return the RetrieverConnectionEntity
   */
  public RetrieverConnectionEntity createUpdateRetrieverConnectionEntity(RetrieverRequest request) {
    RetrieverConnectionEntity entity = mapperUtil.toEntity(request);
    if (!StringUtil.isNullOrEmpty(request.getId())) {
      entity.id = new ObjectId(request.getId());
      entity.update();
    } else {
      entity.persist();
    }
    return entity;
  }

  public List<RetrieverConnectionEntity> getRetrieverConnections() {
    return RetrieverConnectionEntity.listAll();
  }

  /**
   * Create a LLMConnectionEntity.
   *
   * @param entity the LlmConnectionEntity
   * @return the LLMConnectionEntity
   */
  public LlmConnectionEntity createUpdateLlmConnection(LlmConnectionEntity entity) {
    if (entity.id != null) {
      getLlmConnection(entity.id);
      entity.update();
    } else {
      entity.persist();
    }
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

  /**
   * Delete existing llmConnection by object id.
   *
   * @param llmConnectionObjectId the object id
   */
  public void deleteLlmConnection(ObjectId llmConnectionObjectId) {
    getLlmConnection(llmConnectionObjectId);
    LlmConnectionEntity.deleteById(llmConnectionObjectId);
  }

  /**
   * Get single llm connection.
   *
   * @param llmConnectionObjectId the llmConnectionObjectId
   * @return entity
   */
  public LlmConnectionEntity getLlmConnection(ObjectId llmConnectionObjectId) {
    return (LlmConnectionEntity) LlmConnectionEntity.findByIdOptional(llmConnectionObjectId)
        .orElseThrow(
            () -> new NotFoundException(
                "Retriever Connection with object id {" + llmConnectionObjectId.toHexString() + "} not found"));
  }

  /**
   * Delete Retriever Connection.
   *
   * @param retrieverConnectionObjectId the Retriever Connection Id
   */
  public void deleteRetrieverConnection(ObjectId retrieverConnectionObjectId) {
    getRetrieverConnection(retrieverConnectionObjectId);
    RetrieverConnectionEntity.deleteById(retrieverConnectionObjectId);
  }


  /**
   * Get single Retriever Connection.
   *
   * @param retrieverConnectionObjectId the id
   * @return entity
   */
  public RetrieverConnectionEntity getRetrieverConnection(ObjectId retrieverConnectionObjectId) {
    return (RetrieverConnectionEntity) RetrieverConnectionEntity.findByIdOptional(retrieverConnectionObjectId)
        .orElseThrow(
            () -> new NotFoundException(
                "Retriever Connection with object id {" + retrieverConnectionObjectId.toHexString() + "} not found"));
  }


}
