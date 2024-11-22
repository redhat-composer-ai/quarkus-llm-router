package com.redhat.composer.api;

import org.jboss.logging.Logger;

import com.redhat.composer.model.request.AssistantChatRequest;
import com.redhat.composer.services.ChatBotService;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

/**
 * Assistant API for Chatting using assistants.
 */
@Path("/assistant/chat")
public class AssistantApi {

  Logger log = Logger.getLogger(AssistantApi.class);

  @Inject
  ChatBotService chatBotService;


  /**
   * Chat with an assistant.
   * @param input the AssistantChatRequest
   * @return the Multi of String
   */
  @POST
  @Path("streaming")
  @Consumes(MediaType.APPLICATION_JSON)
  public Multi<String> chat(AssistantChatRequest input) {
    return chatBotService.chat(input);
  }

}
