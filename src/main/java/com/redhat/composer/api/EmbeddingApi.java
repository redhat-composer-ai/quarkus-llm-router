package com.redhat.composer.api;

import com.redhat.composer.services.EmbeddingService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Api For Testing Embedding.
 */
@Path("/embedding")
public class EmbeddingApi {

  @Inject
  EmbeddingService embeddingService;

  /**
   * Embedd a string.
   * 
   * @param text          the text to embedd
   * @param embeddingType the type of embedding to use
   * @return the embedded string
   */
  @POST
  @Path("{embeddingType}")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.TEXT_PLAIN)
  public String embeddString(String text, @PathParam("embeddingType") String embeddingType) {
    return embeddingService.embedding(text, embeddingType).toString();
  }
}
