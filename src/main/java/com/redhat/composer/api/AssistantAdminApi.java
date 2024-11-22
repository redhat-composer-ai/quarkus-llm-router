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
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * Admin API for Creating and Managing Assistants.
 */
@Path("/admin/assistant")
public class AssistantAdminApi {

  @Inject
  AssistantInfoService assistantService;

  /**
   * Create a LLM Connection.
   * @param request the LLMRequest
   * @return the LLMConnectionEntity
   */
  @POST
  @Path("llm")
  public LlmConnectionEntity createLlm(LLMRequest request) {
    return assistantService.createLlmConnection(request);
  }

  /**
   * Get all LLM Connections.
   * @return the list of LLMConnectionEntity
   */
  @GET
  @Path("llm")
  public List<LlmConnectionEntity> getLlms() {
    return assistantService.getLlmConnections();
  }

  /**
   * Create a Retriever Connection.
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
   * @return the list of RetrieverConnectionEntity
   */
  @GET
  @Path("retrieverConnection")
  public List<RetrieverConnectionEntity> getRetrieverConnection() {
    return assistantService.getRetrieverConnections();
  }

  /**
   * Create an Assistant.
   * @param request the AssistantCreationRequest
   * @return the AssistantEntity
   */
  @POST
  public AssistantEntity createAssistant(AssistantCreationRequest request) {
    return assistantService.createAssistant(request);
  }

  /**
   * Get all Assistants.
   * @return the list of AssistantResponse
   */
  @GET
  public List<AssistantResponse> getAssistant() {
    return assistantService.getAssistant();
  }
  
}
