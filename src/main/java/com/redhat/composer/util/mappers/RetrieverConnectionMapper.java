package com.redhat.composer.util.mappers;

import org.mapstruct.Mapper;

import com.redhat.composer.model.enums.ContentRetrieverType;
import com.redhat.composer.model.mongo.contentretrieverentites.Neo4jEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.WeaviateConnectionEntity;
import com.redhat.composer.model.mongo.contentretrieverentites.ElasticsearchConnectionEntity;
import com.redhat.composer.model.request.retriever.Neo4JRequest;
import com.redhat.composer.model.request.retriever.WeaviateRequest;
import com.redhat.composer.model.request.retriever.ElasticsearchRequest;

/**
 * RetrieverConnectionMapper interface.
 */
@Mapper(config = QuarkusMapperConfig.class)
public interface RetrieverConnectionMapper {

  /**
   * Maps a WeaviateConnectionEntity to a WeaviateRequest.

   * @param request the WeaviateConnectionEntity to map
   * @return the WeaviateRequest
   */
  WeaviateConnectionEntity toEntity(WeaviateRequest request);

  /**
   * Maps a Neo4JEntity to a Neo4JRequest.

   * @param request the Neo4JEntity to map
   * @return the Neo4JRequest
   */
  Neo4jEntity toEntity(Neo4JRequest request);

  /**
   * Maps a ElasticsearchConnectionEntity to a ElasticsearchRequest.

   * @param request the ElasticsearchConnectionEntity to map
   * @return the WeaviateRequest
   */
  ElasticsearchConnectionEntity toEntity(ElasticsearchRequest request);

  /**
   * Maps a WeaviateEntity to a WeaviateRequest.

   * @param entity the WeaviateEntity to map
   * @return the WeaviateRequest
   */
  WeaviateRequest toRequest(WeaviateConnectionEntity entity);

  /**
   * Maps a Neo4JEntity to a Neo4JRequest.

   * @param entity the Neo4JEntity to map
   * @return the Neo4JRequest
   */
  Neo4JRequest toRequest(Neo4jEntity entity);

  /**
   * Maps a ElasticsearchEntity to a ElasticsearchRequest.

   * @param entity the ElasticsearchEntity to map
   * @return the ElasticsearchRequest
   */
  ElasticsearchRequest toRequest(ElasticsearchConnectionEntity entity);

  /**
   * Maps a ContentRetrieverType to a String.

   * @param contentRetrieverType the ContentRetrieverType to map
   * @return type value
   */
  default String toString(ContentRetrieverType contentRetrieverType) {
    return contentRetrieverType.getType();
  }

  /**
   * Maps a String to a ContentRetrieverType.
   * @param value the String to map
   * @return the ContentRetrieverType
   */
  default ContentRetrieverType toContentRetrieverType(String value) {
    return ContentRetrieverType.fromString(value);
  }
}
