package com.redhat.composer.config.retriever.contentretriever;

import com.redhat.composer.model.enums.ContentRetrieverType;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Factory for Content Retriever Clients.
 */
@ApplicationScoped
public class ContentRetrieverClientFactory {

  @Inject
  WeaviateContentRetrieverClient weaviateEmbeddingStoreClient;
  
  @Inject
  Neo4jContentRetrieverClient neo4jContentRetrieverClient;

  @Inject
  ElasticsearchContentRetrieverClient elasticsearchContentRetrieverClient;
  
  static final ContentRetrieverType DEFAULT_CONTENT_RETRIEVER = ContentRetrieverType.WEAVIATE;

  /**
   * Get the Content Retriever Client.
   * @param contentRetrieverType the ContentRetrieverType
   * @return the Content Retriever Client
   */
  public BaseContentRetrieverClient getContentRetrieverClient(ContentRetrieverType contentRetrieverType) {
    if (contentRetrieverType == null) {
      contentRetrieverType = DEFAULT_CONTENT_RETRIEVER;
    }
    
    switch (contentRetrieverType) {
      case ContentRetrieverType.WEAVIATE:
        return weaviateEmbeddingStoreClient;
      case ContentRetrieverType.NEO4J:
        return neo4jContentRetrieverClient;
      case ContentRetrieverType.ELASTICSEARCH:
        return elasticsearchContentRetrieverClient;
      default:
        throw new RuntimeException("Content Retriever type not found: " + contentRetrieverType);
    }
  }
  
}
