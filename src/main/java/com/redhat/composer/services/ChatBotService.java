package com.redhat.composer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.composer.config.llm.aiservices.AiServicesFactory;
import com.redhat.composer.config.llm.aiservices.BaseAiService;
import com.redhat.composer.config.llm.models.streaming.StreamingModelFactory;
import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import com.redhat.composer.model.request.AssistantChatRequest;
import com.redhat.composer.model.request.ChatBotRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.response.ContentResponse;
import com.redhat.composer.repositories.AssistantRepository;
import com.redhat.composer.util.mappers.MapperUtil;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.router.DefaultQueryRouter;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.opentelemetry.api.trace.Span;
import io.quarkus.logging.Log;
import io.quarkus.runtime.util.StringUtil;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ChatBotService class.
 */
@ApplicationScoped
public class ChatBotService {

  @ConfigProperty(name = "prompt.default.system.message")
  String defaultSystemMessage;

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

  /**
   * Chat with an assistant.
   * @param request the AssistantChatRequest
   * @return stream of chat response
   */
  public Multi<String> chat(AssistantChatRequest request) {
    return chat(request, Collections.emptyList());
  }

  /**
   * Chat with an assistant.
   *
   * @param request   the AssistantChatRequest
   * @param documents uploaded documents to include
   * @return stream of chat response
   */
  public Multi<String> chat(AssistantChatRequest request, Collection<Document> documents) {

    AssistantEntity assistant;
    if (!StringUtil.isNullOrEmpty(request.getAssistantName())) {
      assistant = assistantRepository.findByName(request.getAssistantName());
    } else if (!StringUtil.isNullOrEmpty(request.getAssistantId())) {
      assistant = AssistantEntity.findById(request.getAssistantId());
    } else {
      throw new RuntimeException("Assistant Name or ID Required");
    }

    LlmConnectionEntity llmConnection = LlmConnectionEntity.findById(assistant.getLlmConnectionId());

    RetrieverConnectionEntity retrieverConnection = RetrieverConnectionEntity
                                      .findById(assistant.getRetrieverConnectionId());

    ChatBotRequest chatBotRequest = new ChatBotRequest();
    chatBotRequest.setMessage(request.getMessage());
    chatBotRequest.setContext(request.getContext());
    chatBotRequest.setRetrieverRequest(mapperUtil.toRequest(retrieverConnection));
    chatBotRequest.setModelRequest(mapperUtil.toRequest(llmConnection));
    chatBotRequest.setSystemMessage(assistant.getUserPrompt());

    return chat(chatBotRequest, documents);
  }

  /**
   * Chat with a model.
   * @param request the ChatBotRequest
   * @return stream of chat response
   */
  public Multi<String> chat(ChatBotRequest request) {
    return chat(request, Collections.emptyList());
  }

  /**
   * Chat with a model.
   * @param request the ChatBotRequest
   * @param documents documents to include
   * @return stream of chat response
   */
  public Multi<String> chat(ChatBotRequest request, Collection<Document> documents) {

    String traceId = Span.current().getSpanContext().getTraceId();
    Log.info("ChatBotService.chat for message: " + request.getMessage() + " traceId: " + traceId);
    validateRequest(request);

    StreamingChatLanguageModel llm = modelTemplateFactory.getModel(request.getModelRequest().getModelType())
                                                                    .getChatModel(request.getModelRequest());

    // TODO: Make this configurable
    Class<? extends BaseAiService> aiServiceClass = aiServicesFactory
                        .getAiService(AiServicesFactory.MISTRAL7B_AI_SERVICE);

    BaseAiService aiService = prepareAiService(
        aiServiceClass,
        llm,
        Collections.singleton(request.getRetrieverRequest()),
        documents
    );

    try {
      String systemMessage = request.getSystemMessage() == null ? defaultSystemMessage : request.getSystemMessage();
      Multi<String> multi = Multi.createFrom().emitter(em -> {
        aiService.chatToken(request.getContext(), request.getMessage(), systemMessage)
        .onNext(em::emit)
        .onRetrieved(sources -> {
          try {
            em.emit("START_SOURCES_STRING\n");
            em.emit(objectMapper.writeValueAsString(new ContentResponse(sources)));
            em.emit("\nEND_SOURCES_STRING\n");
          } catch (JsonProcessingException e) {
            Log.error("Sources not processable: %e", e);
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
      Log.error("Error in ChatBotService.chat", e);
      return Multi.createFrom().failure(e);
    }
  }

  // TODO: Support non-streaming chat?

  /**
   * Prepare an AI service with zero or more content retrievers. Content retrievers may be from a
   * {@link RetrieverRequest} or may be generated from provided {@link Document} that will be split, embedded,
   * and stored in an {@link EmbeddingStore} for retrieval by the AI
   *
   * @param <T> the type of AI service to build
   * @param aiServiceClass the class of AI service to build
   * @param llm the language model to use in the AI service
   * @param retrieverRequests an optional collection of  {@link RetrieverRequest} to provide additional knowledge
   *     to the AI
   * @param documents an optional collection of {@link Document} in which the AI may infer knowledge
   * @return the built AI service
   */
  private <T extends BaseAiService> T prepareAiService(
      Class<T> aiServiceClass,
      StreamingChatLanguageModel llm,
      Collection<RetrieverRequest> retrieverRequests,
      Collection<Document> documents
  ) {
    AiServices<T> builder = AiServices.builder(aiServiceClass)
        .streamingChatLanguageModel(llm);

    List<ContentRetriever> retrievers = new ArrayList<>();

    if (retrieverRequests != null) {
      retrieverRequests.stream()
          .filter(Objects::nonNull)
          .map(ragService::getContentRetriever)
          .forEach(retrievers::add);
    }

    if (documents != null && !documents.isEmpty()) {
      // TODO: Make these configurable in the Assistant or LLM descriptions; for now, they're null to use defaults that
      //  are configured in application.properties (or simialr config sources, such as environment variables)
      Integer maxResults = null;
      Double minScore = null;

      retrievers.add(ragService.contentRetrieverForDocuments(
          documents,
          maxResults,
          minScore
      ));
    }

    if (!retrievers.isEmpty()) {
      builder.retrievalAugmentor(DefaultRetrievalAugmentor.builder()
          .queryRouter(new DefaultQueryRouter(retrievers))
          .build());
    }

    return builder.build();
  }

  private void validateRequest(ChatBotRequest request) {
    if (request.getMessage() == null) {
      throw new RuntimeException("Request Message Required");
    }
  }

}
