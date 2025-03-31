package com.redhat.composer.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.SubclassMapping;

import com.redhat.composer.api.model.BaseRetrieverConnection;
import com.redhat.composer.api.model.CreateRetrieverConnectionRequest;
import com.redhat.composer.api.model.ElasticsearchConnection;
import com.redhat.composer.api.model.RetrieverConnection;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.BaseRetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.ElasticsearchConnectionEntity;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.request.retriever.BaseRetrieverRequest;
import com.redhat.composer.model.request.retriever.ElasticsearchRequest;
import com.redhat.composer.util.mappers.BsonMapper;
import com.redhat.composer.util.mappers.QuarkusMapperConfig;

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
  @Mappings({
    @Mapping(target = "apiKey", ignore = true),
    @Mapping(target = "password", ignore = true)
  })
  BaseRetrieverConnection toRest(BaseRetrieverConnectionEntity baseEntity);

}
