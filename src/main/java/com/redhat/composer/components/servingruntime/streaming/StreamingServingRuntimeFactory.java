package com.redhat.composer.components.servingruntime.streaming;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Streaming Model Factory.
 */
@ApplicationScoped
public class StreamingServingRuntimeFactory {

  @Inject
  OpenAiStreamingServingRuntime openAiRuntime;
  public static final String OPENAI_RUNTIME = "openai";

  @ConfigProperty(name = "serving.runtime.default.type")
  private String defaultServingRuntime;

  /**
   * Get the model.
   * @param servingRuntimeType the model type
   * @return the model
   */
  public StreamingServingRuntime getServingRuntime(String servingRuntimeType) {
    if (servingRuntimeType == null) {
      servingRuntimeType = defaultServingRuntime;
    }

    switch (servingRuntimeType) {
      case OPENAI_RUNTIME:
        return openAiRuntime;
      default:
        throw new RuntimeException("Model type not found: " + servingRuntimeType);
    }
  }

}
