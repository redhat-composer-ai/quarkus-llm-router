package com.redhat.composer.config.retriever.embeddingmodel;

import dev.langchain4j.model.embedding.onnx.OnnxBertBiEncoder;
import dev.langchain4j.model.embedding.onnx.PoolingMode;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.inject.Singleton;

/**
 * Nomic Local Embedding Model Client.
 */
@Singleton
public class NomicLocalEmbeddingModelClient extends BaseLocalEmbeddingModelClient {

  private static final String MODEL_FOLDER = "embeddings/nomic/";

  private final OnnxBertBiEncoder model;

  /**
   * Constructor.
   */
  public NomicLocalEmbeddingModelClient() {
    super(Infrastructure.getDefaultExecutor());
    model = loadFromJar(
        MODEL_FOLDER + MODEL_NAME,
        MODEL_FOLDER + TOKENIZER_NAME,
        PoolingMode.CLS
    );
  }


  /**
   * Get the Model Folder.
   * @return the Model Folder
   */
  @Override
  protected OnnxBertBiEncoder model() {
    return model;
  }

}
