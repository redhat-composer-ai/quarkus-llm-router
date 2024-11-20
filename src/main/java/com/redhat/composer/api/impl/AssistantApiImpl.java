package com.redhat.composer.api.impl;

import com.redhat.composer.api.AssistantApi;
import com.redhat.composer.api.AssistantMultipartApi;
import com.redhat.composer.api.mapper.AssistantMapper;
import com.redhat.composer.api.model.AssistantChatMessage;
import com.redhat.composer.api.model.AssistantChatRequestMultipart;
import com.redhat.composer.services.ChatBotService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;

/**
 * Assistant API for Chatting using assistants.
 */
@Authenticated
public class AssistantApiImpl implements AssistantApi, AssistantMultipartApi {

  @Inject
  AssistantMapper restMapper;

  @Inject
  ChatBotService chatBotService;

  @Override
  public Multi<String> assistantChatStreaming(AssistantChatMessage assistantChatMessage) {
    return chatBotService.chat(restMapper.fromRest(assistantChatMessage));
  }

  /**
   * Chat with an assistant.
   *
   * @param multipartRequest the multipart request which includes a AssistantChatMessage
   * @return the Multi of String
   */
  @Override
  public Multi<String> assistantChatStreamingMp(AssistantChatRequestMultipart multipartRequest) {
    return chatBotService.chat(
        restMapper.fromRest(multipartRequest.getJsonRequest())
    );
  }
}
