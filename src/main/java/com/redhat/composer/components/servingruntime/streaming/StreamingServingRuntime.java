package com.redhat.composer.components.servingruntime.streaming;

import org.apache.commons.lang3.NotImplementedException;

import com.redhat.composer.model.request.LLMRequest;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;

/**
 * Streaming Base Model.
 */
public class StreamingServingRuntime {

  /**
   * Get Chat Model.
   * @param request the LLMRequest
   * @return the StreamingChatLanguageModel
   */
  public StreamingChatLanguageModel getChatModel(LLMRequest request) {
    throw new NotImplementedException("Not implemented");
  }
}
