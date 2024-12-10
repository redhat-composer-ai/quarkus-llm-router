package com.redhat.composer.api.mapper;

import com.redhat.composer.api.model.BaseRetrieverConnection;
import com.redhat.composer.api.model.CreateRetrieverConnectionRequest;
import com.redhat.composer.api.model.ElasticsearchConnection;
import com.redhat.composer.api.model.Neo4JConnection;
import com.redhat.composer.api.model.RetrieverConnection;
import com.redhat.composer.api.model.WeaviateConnection;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.BaseRetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.ElasticsearchConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.Neo4jEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.WeaviateConnectionEntity;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.request.retriever.BaseRetrieverRequest;
import com.redhat.composer.model.request.retriever.ElasticsearchRequest;
import com.redhat.composer.model.request.retriever.Neo4JRequest;
import com.redhat.composer.model.request.retriever.WeaviateRequest;
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
public interface ContentRetrieverConnectionAdminMapper {

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
  @SubclassMapping(target = ElasticsearchRequest.class, source = ElasticsearchConnection.class)
  @SubclassMapping(target = Neo4JRequest.class, source = Neo4JConnection.class)
  @SubclassMapping(target = WeaviateRequest.class, source = WeaviateConnection.class)
  BaseRetrieverRequest fromRest(BaseRetrieverConnection request);

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
  @SubclassMapping(target = ElasticsearchConnection.class, source = ElasticsearchConnectionEntity.class)
  @SubclassMapping(target = Neo4JConnection.class, source = Neo4jEntity.class)
  @SubclassMapping(target = WeaviateConnection.class, source = WeaviateConnectionEntity.class)
  BaseRetrieverConnection toRest(BaseRetrieverConnectionEntity baseEntity);

}
