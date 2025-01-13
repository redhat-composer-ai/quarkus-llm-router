package com.redhat.composer.components.aiservices;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.smallrye.mutiny.Multi;


/**
 * Mistral7BAiService.
 */
@SuppressWarnings("LineLengthCheck")
public interface MistralAiService extends BaseAiService {

  static final String userMessage = """
    {{systemMessage}}
    <<<
    Context: {context}
    User Message: {input}
    >>>
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