package com.redhat.composer.util.mappers;

import org.mapstruct.Mapper;

import com.redhat.composer.model.enums.ContentRetrieverType;
import com.redhat.composer.model.mongo.contentretrieverentites.ElasticsearchConnectionEntity;
import com.redhat.composer.model.request.retriever.ElasticsearchRequest;

/**
 * RetrieverConnectionMapper interface.
 */
@Mapper(config = QuarkusMapperConfig.class)
public interface RetrieverConnectionMapper {

  /**
   * Maps a ElasticsearchConnectionEntity to a ElasticsearchRequest.

   * @param request the ElasticsearchConnectionEntity to map
   * @return the WeaviateRequest
   */
  ElasticsearchConnectionEntity toEntity(ElasticsearchRequest request);

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
