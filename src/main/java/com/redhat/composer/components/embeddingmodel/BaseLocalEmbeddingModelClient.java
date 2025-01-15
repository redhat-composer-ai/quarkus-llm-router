package com.redhat.composer.components.embeddingmodel;

import java.util.concurrent.Executor;

import dev.langchain4j.model.embedding.onnx.AbstractInProcessEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.OnnxBertBiEncoder;

/**
 * Base Local Embedding Model Client.
 */
public class BaseLocalEmbeddingModelClient extends AbstractInProcessEmbeddingModel {

  /**
   * Constructor.
   * @param executor the Executor
   */
  public BaseLocalEmbeddingModelClient(Executor executor) {
    super(executor);
  }

  // Can be overridden by subclasses
  static String MODEL_NAME = "model.onnx";
  static String TOKENIZER_NAME = "tokenizer.json";



  @Override
  protected OnnxBertBiEncoder model() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'model'");
  }



}