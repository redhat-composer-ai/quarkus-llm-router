package com.redhat.composer.api.nonspec;

import com.redhat.composer.components.servingRuntime.streaming.StreamingServingRuntime;
import com.redhat.composer.components.servingRuntime.streaming.StreamingServingRuntimeFactory;
import com.redhat.composer.model.request.LLMRequest;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.AiServices;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.jboss.logging.Logger;

/**
 * Api For Testing the LLM.
 */
@Path("/llm")
public class LlmApi {

  Logger log = Logger.getLogger(LlmApi.class);

  @Inject
  StreamingServingRuntimeFactory streamingModelFactory;

  /**
   * Generate a response for a message.
   * @param request the LLMRequest
   * @param message streaming message from LLM
   * @return the response
   */
  @POST
  @Path("/generate/streaming")
  public Multi<String> streamingRequest(LLMRequest request, @QueryParam("message") String message) {
    log.info("Generating response for message: " + message);
    if (request == null) {
      request = new LLMRequest();
    }
    StreamingServingRuntime model = streamingModelFactory.getServingRuntime(request.getModelType());

    StreamingChatLanguageModel llm = model.getChatModel(request);
    Assistant assistant = AiServices.create(Assistant.class, llm);

    return assistant.chat(message);
  }

  interface Assistant {
    Multi<String> chat(String message);
  }

}
