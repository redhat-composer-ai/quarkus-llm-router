package com.redhat.composer.config.llm.aiservices;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.smallrye.mutiny.Multi;


/**
 * Mistral7BAiService.
 */
@SuppressWarnings("LineLengthCheck")
public interface GraniteAiService extends BaseAiService {

  // Note: Handling history is a little more complex than just passing it as a string
  // See: https://www.ibm.com/granite/docs/models/granite/#using-the-multi-round-chat-example-finance 
  static final String userMessage = """
    <|start_of_role|>system<|end_of_role|>{{systemMessage}}<|end_of_text|>
    {context}
    <|start_of_role|>user<|end_of_role|>{{input}}<|end_of_text|>
    <|start_of_role|>assistant<|end_of_role|>
      """;

  /**
   * Returns TokenStream given input.
   * @param context Context information such as chat history and source information
   * @param input User Message
   * @return the TokenStream
   */
  @SystemMessage("{{systemMessage}}")
  @UserMessage(userMessage)
  TokenStream chatToken(@V("context") String context, @V("input") String input, @V("systemMessage") String systemMessage);


  /**
   * Returns a Multi of String given input.
   * @param context Context information such as chat history and source information
   * @param input User Message
   * @return the Multi of String
   */
  @SystemMessage("{{systemMessage}}")
  @UserMessage(userMessage)
  Multi<String> chatStream(@V("context") String context, @V("input") String input, @V("systemMessage") String systemMessage);
  
} 