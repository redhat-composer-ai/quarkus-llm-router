package com.redhat.composer.api.mapper;

import com.redhat.composer.api.model.*;
import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.BaseRetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.ElasticsearchConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.Neo4jEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.WeaviateConnectionEntity;
import com.redhat.composer.model.request.AssistantCreationRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.request.retriever.BaseRetrieverRequest;
import com.redhat.composer.model.request.retriever.ElasticsearchRequest;
import com.redhat.composer.model.request.retriever.Neo4JRequest;
import com.redhat.composer.model.request.retriever.WeaviateRequest;
import com.redhat.composer.model.response.AssistantResponse;
import com.redhat.composer.util.mappers.BsonMapper;
import com.redhat.composer.util.mappers.QuarkusMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

/**
 * MapStruct mapper to facilitate decoupling of REST interface from internal processing models
 * for Assistant Admin APIs.
 */
@Mapper(
    config = QuarkusMapperConfig.class,
    uses = {
        BsonMapper.class
    }
)
public interface LlmConnectionAdminMapper {

  /**
   * Map LLM creation requests.
   *
   * @param request the REST representation of a request
   * @return the internal representation of a request
   */
  LlmConnectionEntity fromRest(CreateLlmConnectionRequest request);

  /**
   * Map LLM connection.
   *
   * @param llmConnectionEntity the internal entity
   * @return a REST representation of the entity
   */
  LLMConnection toRest(LlmConnectionEntity llmConnectionEntity);

}
