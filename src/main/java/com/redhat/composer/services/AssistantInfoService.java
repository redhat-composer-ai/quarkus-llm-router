package com.redhat.composer.services;

import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.BaseEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.request.AssistantCreationRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.response.AssistantResponse;
import com.redhat.composer.util.mappers.MapperUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
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
   * @return boolean
   */
  public boolean deleteAssistant(ObjectId assistantObjectId) {
    return AssistantEntity.deleteById(assistantObjectId);
  }

  /**
   * Get single assistant.
   *
   * @param assistantObjectId
   * @return assistantResponse
   */
  public Optional<AssistantEntity> getAssistant(ObjectId assistantObjectId) {
    return AssistantEntity.findByIdOptional(assistantObjectId);
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
   * @param entity the LlmConnectionEntity
   * @return the LLMConnectionEntity
   */
  public LlmConnectionEntity createUpdateLlmConnection(LlmConnectionEntity entity) {
    if (entity.id != null) {
      getEntity(entity.id, LlmConnectionEntity.class);
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
   * @return boolean true if the llmConnection was successfully deleted
   */
  public Boolean deleteLlmConnection(ObjectId llmConnectionObjectId) {
    return LlmConnectionEntity.deleteById(llmConnectionObjectId);
  }

  /**
   * Get single llm connection.
   *
   * @param llmConnectionObjectId the llmConnectionObjectId
   * @return Optional
   */
  public Optional<LlmConnectionEntity> getLlmConnection(ObjectId llmConnectionObjectId) {
    return LlmConnectionEntity.findByIdOptional(llmConnectionObjectId);
  }

  /**
   * Delete Retriever Connection.
   *
   * @param retrieverConnectionObjectId the Retriever Connection Id
   * @return boolean
   */
  public boolean deleteRetrieverConnection(ObjectId retrieverConnectionObjectId) {
    return RetrieverConnectionEntity.deleteById(retrieverConnectionObjectId);
  }


  /**
   * Get single Retriever Connection.
   *
   * @param retrieverConnectionObjectId the id
   * @return Optional
   */
  public RetrieverConnectionEntity getRetrieverConnection(ObjectId retrieverConnectionObjectId) {
    return getEntity(retrieverConnectionObjectId, RetrieverConnectionEntity.class);
    //    return (RetrieverConnectionEntity) RetrieverConnectionEntity.findByIdOptional(retrieverConnectionObjectId)
    //        .orElseThrow(
    //            () -> new NotFoundException(
    //           "Retriever Connection with object id {" + retrieverConnectionObjectId.toHexString() + "} not found"));
  }

  @SuppressWarnings("unchecked")
  private <T extends BaseEntity> T getEntity(ObjectId objectId, Class<T> type) {
    return (T) T.findByIdOptional(objectId)
        .orElseThrow(
            () -> new NotFoundException(
                type.getSimpleName() + " with object id {" + objectId.toHexString() + "} not found"));
  }

}
