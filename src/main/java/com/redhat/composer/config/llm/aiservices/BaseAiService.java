package com.redhat.composer.config.llm.aiservices;

import dev.langchain4j.service.TokenStream;
import io.smallrye.mutiny.Multi;

/**
 * Base AI Service Interface.
 */
public interface BaseAiService {

  /**
   * Returns TokenStream given input.
   * @param context Context information such as chat history and source information
   * @param input User Message
   * @return the TokenStream
   */
  TokenStream chatToken(String context, String input, String systemMessage);

  /**
   * Returns a Multi of String given input.
   * @param context Context information such as chat history and source information
   * @param input User Message
   * @return the Multi of String
   */
  Multi<String> chatStream(String context, String input, String systemMessage);
  
} 