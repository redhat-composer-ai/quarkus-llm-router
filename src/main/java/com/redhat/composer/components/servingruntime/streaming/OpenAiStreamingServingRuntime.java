package com.redhat.composer.components.servingruntime.streaming;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.redhat.composer.model.request.LLMRequest;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel.OpenAiStreamingChatModelBuilder;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * OpenAI Streaming Model.
 */
@ApplicationScoped
public class OpenAiStreamingServingRuntime extends StreamingServingRuntime {

  Logger log = Logger.getLogger(OpenAiStreamingServingRuntime.class);

  @ConfigProperty(name = "openai.default.url")
  private String openAiDefaultUrl;

  @ConfigProperty(name = "openai.default.apiKey")
  private String openAiDefaultApiKey;

  @ConfigProperty(name = "openai.default.modelName")
  private String openAiDefaultModelName;

  @ConfigProperty(name = "openai.default.temp")
  private double openaiDefaultTemp;

  @ConfigProperty(name = "openai.default.maxTokens")
  private Integer openaiDefaultMaxTokens;

  /**
   * Get the Chat Model.
   * @param request the LLMRequest
   * @return the StreamingChatLanguageModel
   */
  public StreamingChatLanguageModel getChatModel(LLMRequest request) {

    log.info("Attempting to create OpenAI Streaming Chat Model at: " + request.getUrl()
                                      + " with model name: " + request.getModelName());

    OpenAiStreamingChatModelBuilder builder = OpenAiStreamingChatModel.builder();
    builder.baseUrl(request.getUrl() == null ? openAiDefaultUrl : request.getUrl());
    builder.apiKey(request.getApiKey() == null ? openAiDefaultApiKey : request.getApiKey());

    builder.modelName(request.getModelName() == null ? openAiDefaultModelName : request.getModelName());
    builder.temperature(request.getTempature() == null ? openaiDefaultTemp : request.getTempature());

    builder.maxTokens(openaiDefaultMaxTokens);

    return builder.build();
  }

}
