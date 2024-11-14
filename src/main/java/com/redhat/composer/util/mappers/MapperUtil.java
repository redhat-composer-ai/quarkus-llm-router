package com.redhat.composer.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.redhat.composer.model.enums.ContentRetrieverType;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.BaseRetrieverConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.ElasticsearchConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.Neo4jEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.WeaviateConnectionEntity;
import com.redhat.composer.model.request.LLMRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.request.retriever.BaseRetrieverRequest;
import com.redhat.composer.model.request.retriever.Neo4JRequest;
import com.redhat.composer.model.request.retriever.WeaviateRequest;
import com.redhat.composer.model.request.retriever.ElasticsearchRequest;

import jakarta.enterprise.inject.Default;

/**
 * MapperUtil interface.
 */
@Default
@Mapper(config = QuarkusMapperConfig.class)
public interface MapperUtil {

  RetrieverConnectionMapper retrieverConnectionMapper = Mappers.getMapper(RetrieverConnectionMapper.class);

  /**
   * Maps a RetrieverRequest to a RetrieverConnectionEntity.
   */
  @Mappings({
    @Mapping(target = "connectionEntity", source = "baseRetrieverRequest"),
    @Mapping(target = "id", ignore = true)
  })
  RetrieverConnectionEntity toEntity(RetrieverRequest request);

  /**
   * Maps a LLMRequest to a LLMConnectionEntity.
   * @param request the LLMRequest to map
   * @return the LLMConnectionEntity
   */
  @Mapping(target = "id", ignore = true)
  LlmConnectionEntity toEntity(LLMRequest request);


  /**
   * Maps a RetrieverConnectionEntity to a RetrieverRequest.
   * @param entity the RetrieverConnectionEntity to map
   * @return the RetrieverRequest
   */
  @Mapping(source = "connectionEntity", target = "baseRetrieverRequest")
  RetrieverRequest toRequest(RetrieverConnectionEntity entity);
  
  /**
   * Maps a LLMConnectionEntity to a LLMRequest.
   * @param entity the LLMConnectionEntity to map
   * @return the LLMRequest
   */
  LLMRequest toRequest(LlmConnectionEntity entity);

  /**
   * Maps a BaseRetrieverRequest to a BaseRetrieverConnectionEntity.
   * @param request the BaseRetrieverRequest to map
   * @return the BaseRetrieverConnectionEntity
   */
  default BaseRetrieverConnectionEntity mapToBaseEntity(BaseRetrieverRequest request) {

    if (request == null) {
      return null;
    }
    
    switch (ContentRetrieverType.fromString(request.getContentRetrieverType())) {
      case ContentRetrieverType.WEAVIATE:
        return retrieverConnectionMapper.toEntity((WeaviateRequest) request);
      case ContentRetrieverType.NEO4J:
        return retrieverConnectionMapper.toEntity((Neo4JRequest) request);
      case ContentRetrieverType.ELASTICSEARCH:
        return retrieverConnectionMapper.toEntity((ElasticsearchRequest) request);
      default:
        return null;
    }
  }

  /**
   * Maps a BaseRetrieverConnectionEntity to a BaseRetrieverRequest.

   * @param entity the BaseRetrieverConnectionEntity to map
   * @return the BaseRetrieverRequest
   */
  default BaseRetrieverRequest mapToBaseRequest(BaseRetrieverConnectionEntity entity) {
    
    if (entity == null || entity.getContentRetrieverType() == null) {
      return null;
    }

    switch (entity.getContentRetrieverType()) {
      case ContentRetrieverType.WEAVIATE:
        return retrieverConnectionMapper.toRequest((WeaviateConnectionEntity) entity);
      case ContentRetrieverType.NEO4J:
        return retrieverConnectionMapper.toRequest((Neo4jEntity) entity);
      case ContentRetrieverType.ELASTICSEARCH:
        return retrieverConnectionMapper.toRequest((ElasticsearchConnectionEntity) entity);
      default:
        return null;
    }
  }

 
}
