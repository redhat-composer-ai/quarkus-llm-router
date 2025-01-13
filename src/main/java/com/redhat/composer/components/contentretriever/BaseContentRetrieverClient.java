package com.redhat.composer.components.contentretriever;

import com.redhat.composer.components.embeddingmodel.EmbeddingModelFactory;
import com.redhat.composer.model.request.RetrieverRequest;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import jakarta.inject.Inject;

/**
 * Base Content Retriever Client.
 */
public class BaseContentRetrieverClient {

  @Inject
  EmbeddingModelFactory embeddingModelFactory;

  /**
   * Get Content Retriever.
   * @param request the RetrieverRequest
   * @return ContentRetriever
   */
  public ContentRetriever getContentRetriever(RetrieverRequest request) {
    throw new UnsupportedOperationException("Unimplemented method 'getContentRetriever'");
  }

  /**
   * Get Embedding Model.
   * @param embeddingType the String
   * @return EmbeddingModel
   */
  protected EmbeddingModel getEmbeddingModel(String embeddingType) {
    return embeddingModelFactory.getEmbeddingModel(embeddingType);
  }

}
