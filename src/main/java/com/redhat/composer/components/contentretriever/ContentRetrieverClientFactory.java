package com.redhat.composer.components.contentretriever;

import com.redhat.composer.model.enums.ContentRetrieverType;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Factory for Content Retriever Clients.
 */
@ApplicationScoped
public class ContentRetrieverClientFactory {

  @Inject
  WeaviateContentRetrieverClient weaviateEmbeddingStoreClient;

  @Inject
  ElasticsearchContentRetrieverClient elasticsearchContentRetrieverClient;

  @ConfigProperty(name = "vector.store.default.type", defaultValue = "ELASTICSEARCH")
  String defaultContentRetrieverType;

  /**
   * Get the Content Retriever Client.
   *
   * @param contentRetrieverType the ContentRetrieverType
   * @return the Content Retriever Client
   */
  public BaseContentRetrieverClient getContentRetrieverClient(ContentRetrieverType contentRetrieverType) {
    if (contentRetrieverType == null) {
      contentRetrieverType = ContentRetrieverType.fromString(defaultContentRetrieverType);;
    }

    switch (contentRetrieverType) {
      case WEAVIATE:
        return weaviateEmbeddingStoreClient;
      case ELASTICSEARCH:
        return elasticsearchContentRetrieverClient;
      default:
        throw new RuntimeException("Content Retriever type not found: " + contentRetrieverType);
    }
  }

}
