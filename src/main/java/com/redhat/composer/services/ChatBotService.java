package com.redhat.composer.services;

import java.util.Map;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.composer.config.llm.aiservices.AiServicesFactory;
import com.redhat.composer.config.llm.aiservices.BaseAiService;
import com.redhat.composer.config.llm.models.streaming.StreamingModelFactory;
import com.redhat.composer.model.mongo.AIGatewayConnectionEntity;
import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.request.AssistantChatRequest;
import com.redhat.composer.model.request.ChatBotRequest;
import com.redhat.composer.model.response.ContentResponse;
import com.redhat.composer.repositories.AssistantRepository;
import com.redhat.composer.util.mappers.MapperUtil;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.AiServices;
import io.opentelemetry.api.trace.Span;
import io.quarkus.runtime.util.StringUtil;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.core.streams.WriteStream;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.ext.web.codec.BodyCodec;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * ChatBotService class.
 */
@ApplicationScoped
public class ChatBotService {

  Logger log = Logger.getLogger(ChatBotService.class);

  @Inject
  StreamingModelFactory modelTemplateFactory;

  @Inject
  AiServicesFactory aiServicesFactory;

  @Inject
  RetrieveService ragService;

  @Inject
  AssistantRepository assistantRepository;

  @Inject
  MapperUtil mapperUtil;

  @Inject
  ObjectMapper objectMapper;

  @Inject
  WebClient webClient;

  /**
   * Chat with an assistant.
   * @param request the AssistantChatRequest
   * @return stream of chat response
   */
  public Multi<String> chat(AssistantChatRequest request) {

    AssistantEntity assistant;
    if (!StringUtil.isNullOrEmpty(request.getAssistantName())) {
      assistant = assistantRepository.findByName(request.getAssistantName());
    } else if (!StringUtil.isNullOrEmpty(request.getAssistantId())) {
      assistant = AssistantEntity.findById(request.getAssistantId());
    } else {
      throw new RuntimeException("Assistant Name or ID Required");
    }

    AIGatewayConnectionEntity aiGateway = AIGatewayConnectionEntity.findById(assistant.getAiGatewayConnectionId());
    if (aiGateway != null) {
      String url = aiGateway.getHost() + (aiGateway.getPath().startsWith("/") ? aiGateway.getPath() : "/" + aiGateway.getPath());
      Map<String, Object> forwardRequest = aiGateway.getMessageProperties();
      // TODO: Make Message configurable in some way
      forwardRequest.put("message", request.getMessage()); 
      
      // webClient.post(url)
      //       .as(BodyCodec.pipe(writeStream))
      //       .sendJson(forwardRequest);
      // writeStream.
      return Multi.createFrom().emitter(emitter -> {
        webClient.post(url)
            .sendJson(forwardRequest)
            .subscribe().with(response -> {
                emitter.emit(response.bodyAsString());
                emitter.complete();
            }, emitter::fail);
    });
    }

    LlmConnectionEntity llmConnection = LlmConnectionEntity.findById(assistant.getLlmConnectionId());

    RetrieverConnectionEntity retrieverConnection = RetrieverConnectionEntity.findById(assistant.getRetrieverConnectionId());

    ChatBotRequest chatBotRequest = new ChatBotRequest();
    chatBotRequest.setMessage(request.getMessage());
    chatBotRequest.setContext(request.getContext());
    chatBotRequest.setRetrieverRequest(mapperUtil.toRequest(retrieverConnection));
    chatBotRequest.setModelRequest(mapperUtil.toRequest(llmConnection));

    return chat(chatBotRequest);
  }

  /**
   * Chat with a model.
   * @param request the ChatBotRequest
   * @return stream of chat response
   */
  public Multi<String> chat(ChatBotRequest request) {

    String traceId = Span.current().getSpanContext().getTraceId();
    log.info("ChatBotService.chat for message: " + request.getMessage() + " traceId: " + traceId);
    validateRequest(request);

    StreamingChatLanguageModel llm = modelTemplateFactory.getModel(request.getModelRequest().getModelType())
                                                                    .getChatModel(request.getModelRequest());
    
    // TODO: Make this configurable
    Class<? extends BaseAiService> aiServiceClass = aiServicesFactory
                        .getAiService(AiServicesFactory.MISTRAL7B_AI_SERVICE);
    
    AiServices<? extends BaseAiService> builder = AiServices.builder(aiServiceClass)
        .streamingChatLanguageModel(llm);

    if (request.getRetrieverRequest() != null) {
      builder.contentRetriever(ragService.getContentRetriever(request.getRetrieverRequest()));
    }

    BaseAiService aiService = builder.build();

    try {
      Multi<String> multi = Multi.createFrom().emitter(em -> {
        aiService.chatToken(request.getContext(), request.getMessage())
        .onNext(em::emit)
        .onRetrieved(sources -> {
          try {
            em.emit("START_SOURCES_STRING\n");
            em.emit(objectMapper.writeValueAsString(new ContentResponse(sources)));
            em.emit("\nEND_SOURCES_STRING\n");
          } catch (JsonProcessingException e) {
            log.error("Sources not processable: %e", e);
          }
        })
        .onError(em::fail)
        .onComplete(response -> {
          em.complete();
        })
            .start();
      });
      return multi;
    } catch (Exception e) {
      log.error("Error in ChatBotService.chat", e);
      return Multi.createFrom().failure(e);
    }
  }

  // TODO: Support non-streaming chat?

  private void validateRequest(ChatBotRequest request) {
    if (request.getMessage() == null) {
      throw new RuntimeException("Request Message Required");
    }
  }
  

  
  
}
