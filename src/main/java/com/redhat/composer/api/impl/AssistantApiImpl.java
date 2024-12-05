package com.redhat.composer.api.impl;

import com.redhat.composer.api.AssistantApi;
import com.redhat.composer.api.mapper.AssistantMapper;
import com.redhat.composer.api.model.AssistantChatMessage;
import com.redhat.composer.services.ChatBotService;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;

/**
 * Assistant API for Chatting using assistants.
 */
@Authenticated
public class AssistantApiImpl implements AssistantApi {

  @Inject
  AssistantMapper restMapper;

  @Inject
  ChatBotService chatBotService;

  @Override
  public Multi<String> assistantChatStreaming(AssistantChatMessage assistantChatMessage) {
    return chatBotService.chat(restMapper.fromRest(assistantChatMessage));
  }
}
