package com.redhat.composer.api;

import java.util.List;

import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.request.AssistantCreationRequest;
import com.redhat.composer.model.request.LLMRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.response.AssistantResponse;
import com.redhat.composer.services.AssistantInfoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.bson.types.ObjectId;

/**
 * Admin API for Creating and Managing Assistants.
 */
@Path("/admin/assistant")
public class AssistantAdminApi {

  @Inject
  AssistantInfoService assistantService;

  /**
   * Create a LLM Connection.
   *
   * @param entity the LLMRequest
   * @return the LLMConnectionEntity
   */
  @POST
  @Path("llm")
  public LlmConnectionEntity createLlm(LlmConnectionEntity entity) {
    return assistantService.createUpdateLlmConnection(entity);
  }


  /**
   * Delete an LLM Connection.
   *
   * @param llmConnectionObjectIdHexString the llmConnectionId
   */
  @DELETE
  @Path("llm/{llmConnectionObjectIdHexString}")
  public void deleteLlm(@PathParam("llmConnectionObjectIdHexString") String llmConnectionObjectIdHexString) {
    // Attempt to remove the llmConnection if it does not exist raise NotFound exception.
    if (!assistantService.deleteLlmConnection(new ObjectId(llmConnectionObjectIdHexString))) {
      throw new NotFoundException("Llm with object id {" + llmConnectionObjectIdHexString + "} not found");
    }
  }

  /**
   * Get single LLM Connections.
   *
   * @return the list of LLMConnectionEntity
   */
  @GET
  @Path("llm/{llmConnectionObjectIdHexString}")
  public LlmConnectionEntity getLlm(
      @PathParam("llmConnectionObjectIdHexString") String llmConnectionObjectIdHexString) {
    return assistantService.getLlmConnection(new ObjectId(llmConnectionObjectIdHexString)).orElseThrow(
        () -> new NotFoundException("Llm with object id {" + llmConnectionObjectIdHexString + "} not found"));
  }

  /**
   * Get all LLM Connections.
   *
   * @return the list of LLMConnectionEntity
   */
  @GET
  @Path("llm")
  public List<LlmConnectionEntity> getLlms() {
    return assistantService.getLlmConnections();
  }

  /**
   * Create a Retriever Connection.
   *
   * @param request the RetrieverRequest
   * @return the RetrieverConnectionEntity
   */
  @POST
  @Path("retrieverConnection")
  public RetrieverConnectionEntity createRetrieverConnection(RetrieverRequest request) {
    return assistantService.createRetrieverConnectionEntity(request);
  }

  /**
   * Get all Retriever Connections.
   *
   * @return the list of RetrieverConnectionEntity
   */
  @GET
  @Path("retrieverConnection")
  public List<RetrieverConnectionEntity> getRetrieverConnections() {
    return assistantService.getRetrieverConnections();
  }

  /**
   * Delete a Retriever Connection.
   *
   * @param retrieverConnectionObjectIdHexString the retrieverConnectionObjectId
   */
  @DELETE
  @Path("retrieverConnection/{retrieverConnectionObjectIdHexString}")
  public void deleteRetrieverConnection(
      @PathParam("retrieverConnectionObjectIdHexString") String retrieverConnectionObjectIdHexString) {
    // Attempt to remove the llmConnection if it does not exist raise NotFound exception.
    if (!assistantService.deleteRetrieverConnection(new ObjectId(retrieverConnectionObjectIdHexString))) {
      throw new NotFoundException(
          "Retriever Connection with object id {" + retrieverConnectionObjectIdHexString + "} not found");
    }
  }

  /**
   * Get single LLM Connections.
   *
   * @return the list of LLMConnectionEntity
   */
  @GET
  @Path("retrieverConnection/{retrieverConnectionObjectIdHexString}")
  public RetrieverConnectionEntity getRetrieverConnection(
      @PathParam("retrieverConnectionObjectIdHexString") String retrieverConnectionObjectIdHexString) {
    return assistantService.getRetrieverConnection(new ObjectId(retrieverConnectionObjectIdHexString));
  }

  /**
   * Create an Assistant.
   *
   * @param request the AssistantCreationRequest
   * @return the AssistantEntity
   */
  @POST
  public AssistantEntity createAssistant(AssistantCreationRequest request) {
    return assistantService.createAssistant(request);
  }

  /**
   * Get all Assistants.
   *
   * @return the list of AssistantResponse
   */
  @GET
  public List<AssistantResponse> getAssistants() {
    return assistantService.getAssistants();
  }

  /**
   * Delete a Assistant.
   *
   * @param assistantObjectIdHexString the retrieverConnectionObjectId
   */
  @DELETE
  @Path("/{assistantObjectIdHexString}")
  public void deleteAssistant(
      @PathParam("assistantObjectIdHexString") String assistantObjectIdHexString) {
    // Attempt to remove the llmConnection if it does not exist raise NotFound exception.
    if (!assistantService.deleteAssistant(new ObjectId(assistantObjectIdHexString))) {
      throw new NotFoundException(
          "Assistant with object id {" + assistantObjectIdHexString + "} not found");
    }
  }

  /**
   * Get single LLM Connections.
   *
   * @return the list of LLMConnectionEntity
   */
  @GET
  @Path("/{assistantObjectIdHexString}")
  public AssistantEntity getAssistant(
      @PathParam("assistantObjectIdHexString") String assistantObjectIdHexString) {
    return assistantService.getAssistant(new ObjectId(assistantObjectIdHexString)).orElseThrow(
        () -> new NotFoundException(
            "Assistant with object id {" + assistantObjectIdHexString + "} not found"));
  }


}
