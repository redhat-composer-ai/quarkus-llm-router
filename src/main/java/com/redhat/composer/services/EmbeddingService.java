package com.redhat.composer.services;

import com.redhat.composer.components.embeddingmodel.EmbeddingModelFactory;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 *  This service is mostly for testing embeddings models and their outputs.
 */
@ApplicationScoped
public class EmbeddingService {

  @Inject
  EmbeddingModelFactory embeddingTemplateFactory;

  /**
   * Embeds the given text using the specified embedding model.
   * @param text the text to embed
   * @param embeddingType the type of embedding model to use
   * @return the embedded text
   */
  public Embedding embedding(String text, String embeddingType) {
    EmbeddingModel embedding = embeddingTemplateFactory.getEmbeddingModel(embeddingType);
    Response<Embedding> response = embedding.embed(text);
    return response.content();
  }

}