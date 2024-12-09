package com.redhat.composer.api.nonspec;

import org.jboss.logging.Logger;

import com.redhat.composer.model.request.ChatBotRequest;
import com.redhat.composer.services.ChatBotService;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

/**
 * ChatBotAPI for Chatting using ChatBots.
 */
@Path("/chatbot/chat")
public class ChatBotApi {

  Logger log = Logger.getLogger(ChatBotApi.class);

  @Inject
  ChatBotService chatBotService;

  /**
   * Chat with a ChatBot.
   * @param input ChatBotRequest infromation
   * @return Streamed response
   */
  @POST
  @Path("streaming")
  @Consumes(MediaType.APPLICATION_JSON)
  public Multi<String> chat(ChatBotRequest input) {
    return chatBotService.chat(input);
  }

}
