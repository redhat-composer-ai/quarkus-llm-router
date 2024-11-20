package com.redhat.composer.api.impl;

import com.redhat.composer.api.AssistantAdminApi;
import com.redhat.composer.api.mapper.AssistantAdminMapper;
import com.redhat.composer.api.model.Assistant;
import com.redhat.composer.api.model.CreateAssistantRequest;
import com.redhat.composer.api.model.CreateLlmConnectionRequest;
import com.redhat.composer.api.model.CreateRetrieverConnectionRequest;
import com.redhat.composer.api.model.LLMConnection;
import com.redhat.composer.api.model.RetrieverConnection;
import com.redhat.composer.services.AssistantInfoService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Admin API for Creating and Managing Assistants.
 */
@Authenticated
public class AssistantAdminApiImpl implements AssistantAdminApi {

  @Inject
  AssistantAdminMapper restMapper;

  @Inject
  AssistantInfoService assistantService;

  /**
   * Create a LLM Connection.
   *
   * @param request the LLMRequest
   * @return the LLMConnectionEntity
   */
  @Override
  public LLMConnection createLlmConnection(CreateLlmConnectionRequest request) {
    return restMapper.toRest(
        assistantService.createLlmConnection(
            restMapper.fromRest(request)
        )
    );
  }

  /**
   * Get all LLM Connections.
   *
   * @return the list of LLMConnectionEntity
   */
  @Override
  public List<LLMConnection> listLlmConnections() {
    return assistantService.getLlmConnections()
        .stream().map(restMapper::toRest)
        .toList();
  }

  /**
   * Create a Retriever Connection.
   *
   * @param request the RetrieverRequest
   * @return the RetrieverConnectionEntity
   */
  @Override
  public RetrieverConnection createRetrieverConnection(CreateRetrieverConnectionRequest request) {
    return restMapper.toRest(
        assistantService.createRetrieverConnectionEntity(
            restMapper.fromRest(request)
        )
    );
  }

  /**
   * Get all Retriever Connections.
   *
   * @return the list of RetrieverConnectionEntity
   */
  @Override
  public List<RetrieverConnection> listRetrieverConnections() {
    return assistantService.getRetrieverConnections()
        .stream().map(restMapper::toRest)
        .toList();
  }

  /**
   * Create an Assistant.
   *
   * @param request the AssistantCreationRequest
   * @return the AssistantEntity
   */
  @Override
  public Assistant createAssistant(CreateAssistantRequest request) {
    return restMapper.toRest(
        assistantService.createAssistant(
            restMapper.fromRest(request)
        )
    );
  }

  /**
   * Get all Assistants.
   *
   * @return the list of AssistantResponse
   */
  @Override
  public List<Assistant> listAssistants() {
    return assistantService.getAssistant()
        .stream().map(restMapper::toRest)
        .toList();
  }

}
