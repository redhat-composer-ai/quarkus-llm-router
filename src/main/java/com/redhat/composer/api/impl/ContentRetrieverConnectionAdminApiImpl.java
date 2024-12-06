package com.redhat.composer.api.impl;

import com.redhat.composer.api.ContentRetrieverConnectionAdminApi;
import com.redhat.composer.api.mapper.ContentRetrieverConnectionAdminMapper;
import com.redhat.composer.api.model.CreateRetrieverConnectionRequest;
import com.redhat.composer.api.model.RetrieverConnection;
import com.redhat.composer.services.AssistantInfoService;
import com.redhat.composer.util.mappers.BsonMapper;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Admin API for Creating and Managing Assistants.
 */
@Authenticated
public class ContentRetrieverConnectionAdminApiImpl implements ContentRetrieverConnectionAdminApi {

  @Inject
  ContentRetrieverConnectionAdminMapper restMapper;

  @Inject
  BsonMapper bsonMapper;

  @Inject
  AssistantInfoService assistantService;

  @Override
  public List<RetrieverConnection> listRetrieverConnections() {
    return assistantService.getRetrieverConnections()
        .stream().map(restMapper::toRest)
        .toList();
  }

  @Override
  public RetrieverConnection createOrUpdateRetrieverConnection(CreateRetrieverConnectionRequest request) {
    return restMapper.toRest(
        assistantService.createUpdateRetrieverConnectionEntity(
            restMapper.fromRest(request)
        )
    );
  }

  @Override
  public RetrieverConnection getRetrieverConnection(String retrieverConnectionObjectIdHexString) {
    return restMapper.toRest(
        assistantService.getRetrieverConnection(
            bsonMapper.toBsonObjectId(retrieverConnectionObjectIdHexString)
        )
    );
  }

  @Override
  public void deleteRetrieverConnection(String retrieverConnectionObjectIdHexString) {
    assistantService.deleteRetrieverConnection(
        bsonMapper.toBsonObjectId(retrieverConnectionObjectIdHexString)
    );
  }

}
