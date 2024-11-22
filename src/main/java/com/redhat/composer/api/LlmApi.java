package com.redhat.composer.api;

import org.jboss.logging.Logger;

import com.redhat.composer.config.llm.models.streaming.StreamingBaseModel;
import com.redhat.composer.config.llm.models.streaming.StreamingModelFactory;
import com.redhat.composer.config.llm.models.synchronous.SynchronousBaseModel;
import com.redhat.composer.config.llm.models.synchronous.SynchronousModelFactory;
import com.redhat.composer.model.request.LLMRequest;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.AiServices;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

/**
 * Api For Testing the LLM.
 */
@Path("/llm")
@Authenticated
public class LlmApi {

  Logger log = Logger.getLogger(LlmApi.class);

  @Inject
  SynchronousModelFactory synchronousModelFactory;

  @Inject
  StreamingModelFactory streamingModelFactory;

  /**
   * Generate a response for a message.
   * @param request the LLMRequest
   * @param message message from LLM
   * @return the response
   */
  @POST
  @Path("/generate")
  public String syncRequest(LLMRequest request, @QueryParam("message") String message) {
    log.info("Generating response for message: " + message);
    if (request == null) {
      request = new LLMRequest();
    }
    SynchronousBaseModel model = synchronousModelFactory.getModel(request.getModelType());
    ChatLanguageModel llm = model.getChatModel(request);
    return llm.generate(message);
  }

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
    StreamingBaseModel model = streamingModelFactory.getModel(request.getModelType());

    StreamingChatLanguageModel llm = model.getChatModel(request);
    Assistant assistant = AiServices.create(Assistant.class, llm);

    Multi<String> response = assistant.chat(message);
    return response;
  }

  interface Assistant {
    Multi<String> chat(String message);
  }

}
