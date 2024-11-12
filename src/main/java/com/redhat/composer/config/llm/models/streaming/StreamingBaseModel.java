package com.redhat.composer.config.llm.models.streaming;

import org.testcontainers.shaded.org.apache.commons.lang3.NotImplementedException;

import com.redhat.composer.model.request.LLMRequest;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;

/**
 * Streaming Base Model.
 */
public class StreamingBaseModel {
  
  /**
   * Get Chat Model.
   * @param request the LLMRequest
   * @return the StreamingChatLanguageModel
   */
  public StreamingChatLanguageModel getChatModel(LLMRequest request) {  
    throw new NotImplementedException("Not implemented");
  }
}
