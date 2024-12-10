package com.redhat.composer.api.impl;

import com.redhat.composer.api.LlmConnectionAdminApi;
import com.redhat.composer.api.mapper.LlmConnectionAdminMapper;
import com.redhat.composer.api.model.CreateLlmConnectionRequest;
import com.redhat.composer.api.model.LLMConnection;
import com.redhat.composer.services.AssistantInfoService;
import com.redhat.composer.util.mappers.BsonMapper;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Admin API for Creating and Managing Assistants.
 */
@Authenticated
public class LlmConnectionAdminApiImpl implements LlmConnectionAdminApi {

  @Inject
  LlmConnectionAdminMapper restMapper;

  @Inject
  BsonMapper bsonMapper;

  @Inject
  AssistantInfoService assistantService;

  @Override
  public List<LLMConnection> listLlmConnections() {
    return assistantService.getLlmConnections()
        .stream().map(restMapper::toRest)
        .toList();
  }

  @Override
  public LLMConnection createOrUpdateLlmConnection(CreateLlmConnectionRequest request) {
    return restMapper.toRest(
        assistantService.createUpdateLlmConnection(
            restMapper.fromRest(request)
        )
    );
  }

  @Override
  public LLMConnection getLlmConnection(String llmConnectionObjectIdHexString) {
    return restMapper.toRest(
        assistantService.getLlmConnection(
            bsonMapper.toBsonObjectId(llmConnectionObjectIdHexString)
        )
    );
  }

  @Override
  public void deleteLlmConnection(String llmConnectionObjectIdHexString) {
    assistantService.deleteLlmConnection(
        bsonMapper.toBsonObjectId(llmConnectionObjectIdHexString)
    );
  }

}
