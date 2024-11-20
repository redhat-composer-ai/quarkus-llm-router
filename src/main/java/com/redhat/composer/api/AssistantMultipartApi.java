package com.redhat.composer.api;

import com.redhat.composer.api.model.AssistantChatRequestMultipart;
import io.smallrye.mutiny.Multi;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

/**
 * A manual interface in jaxrs-spec style to be used until openapi-generator
 * properly generates API interfaces for endpoints that support multipart requests.
 */
@Path("/assistant/chat")
public interface AssistantMultipartApi {

  /**
   * Chat with an assistant.
   *
   * @param multipartRequest the multipart request, including a required chat message and optional files
   * @return streamed responses from the assistant
   */
  @POST
  @Path("streaming")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  Multi<String> assistantChatStreamingMp(
      @Valid @BeanParam AssistantChatRequestMultipart multipartRequest
  );

}
