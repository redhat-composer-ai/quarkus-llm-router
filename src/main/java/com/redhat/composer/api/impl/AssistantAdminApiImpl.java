package com.redhat.composer.api.impl;

import com.redhat.composer.api.AssistantAdminApi;
import com.redhat.composer.api.mapper.AssistantAdminMapper;
import com.redhat.composer.api.model.Assistant;
import com.redhat.composer.api.model.CreateAssistantRequest;
import com.redhat.composer.services.AssistantInfoService;
import com.redhat.composer.util.mappers.BsonMapper;
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
  BsonMapper bsonMapper;

  @Inject
  AssistantInfoService assistantService;

  @Override
  public List<Assistant> listAssistants() {
    return assistantService.getAssistants()
        .stream().map(restMapper::toRest)
        .toList();
  }

  @Override
  public Assistant createOrUpdateAssistant(CreateAssistantRequest request) {
    return restMapper.toRest(
        assistantService.createUpdateAssistant(
            restMapper.fromRest(request)
        )
    );
  }

  @Override
  public Assistant getAssistant(String assistantObjectIdHexString) {
    return restMapper.toRest(
        assistantService.getAssistant(
            bsonMapper.toBsonObjectId(assistantObjectIdHexString)
        )
    );
  }

  @Override
  public void deleteAssistant(String assistantObjectIdHexString) {
    assistantService.deleteAssistant(
        bsonMapper.toBsonObjectId(assistantObjectIdHexString)
    );
  }

}
