package com.redhat.composer.api.mapper;

import com.redhat.composer.api.model.Assistant;
import com.redhat.composer.api.model.BaseRetrieverConnection;
import com.redhat.composer.api.model.CreateAssistantRequest;
import com.redhat.composer.api.model.CreateLlmConnectionRequest;
import com.redhat.composer.api.model.CreateRetrieverConnectionRequest;
import com.redhat.composer.api.model.LLMConnection;
import com.redhat.composer.api.model.Neo4JConnection;
import com.redhat.composer.api.model.RetrieverConnection;
import com.redhat.composer.api.model.WeaviateConnection;
import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.BaseRetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.Neo4jEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.WeaviateConnectionEntity;
import com.redhat.composer.model.request.AssistantCreationRequest;
import com.redhat.composer.model.request.LLMRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.request.retriever.BaseRetrieverRequest;
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
public interface AssistantAdminMapper {

  /**
   * Map assistant creation requests.
   *
   * @param request the REST representation of a request
   * @return the internal representation of a request
   */
  AssistantCreationRequest fromRest(CreateAssistantRequest request);

  /**
   * Map LLM creation requests.
   *
   * @param request the REST representation of a request
   * @return the internal representation of a request
   */
  LLMRequest fromRest(CreateLlmConnectionRequest request);

  /**
   * Map retriever creation requests.
   *
   * @param request the REST representation of a request
   * @return the internal representation of a request
   */
  RetrieverRequest fromRest(CreateRetrieverConnectionRequest request);

  /**
   * Map base retriever creation requests.
   *
   * @param request the REST representation of a request
   * @return the internal representation of a request
   */
  @SubclassMapping(target = WeaviateRequest.class, source = WeaviateConnection.class)
  @SubclassMapping(target = Neo4JRequest.class, source = Neo4JConnection.class)
  BaseRetrieverRequest fromRest(BaseRetrieverConnection request);

  /**
   * Map assistant.
   *
   * @param assistant the internal entity
   * @return a REST representation of the entity
   */
  @SubclassMapping(target = Assistant.class, source = AssistantResponse.class)
  Assistant toRest(AssistantEntity assistant);

  /**
   * Map LLM connection.
   *
   * @param llmConnectionEntity the internal entity
   * @return a REST representation of the entity
   */
  LLMConnection toRest(LlmConnectionEntity llmConnectionEntity);

  /**
   * Map retriever connection.
   *
   * @param retrieverConnectionEntity the internal entity
   * @return a REST representation of the entity
   */
  RetrieverConnection toRest(RetrieverConnectionEntity retrieverConnectionEntity);

  /**
   * Map base retriever connection.
   *
   * @param baseEntity the internal entity
   * @return a REST representation of the entity
   */
  @SubclassMapping(target = WeaviateConnection.class, source = WeaviateConnectionEntity.class)
  @SubclassMapping(target = Neo4JConnection.class, source = Neo4jEntity.class)
  BaseRetrieverConnection toRest(BaseRetrieverConnectionEntity baseEntity);

}
