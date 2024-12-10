package com.redhat.composer.config.retriever.embeddingmodel;

import com.redhat.composer.config.application.ContentRetrieverConfig;
import dev.langchain4j.model.embedding.EmbeddingModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

/**
 * Embedding Model Factory.
 */
@ApplicationScoped
public class EmbeddingModelFactory {

  public static final String NOMIC_EMBEDDING = "nomic";

  @Inject
  ContentRetrieverConfig retrieverConfig;

  @Inject
  NomicLocalEmbeddingModelClient nomicEmbeddingClient;

  /**
   * Get the Embedding Model.
   * @param embeddingType the String
   * @return the Embedding Model
   */
  public EmbeddingModel getEmbeddingModel(String embeddingType) {
    embeddingType = Objects.requireNonNullElseGet(embeddingType, retrieverConfig::defaultEmbeddingModel);

    return switch (embeddingType) {
      case NOMIC_EMBEDDING -> nomicEmbeddingClient;
      default -> throw new RuntimeException("Embedding type not found: " + embeddingType);
    };
  }

}
