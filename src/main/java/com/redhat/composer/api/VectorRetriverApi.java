package com.redhat.composer.api;

import java.util.List;

import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.response.SourceResponse;
import com.redhat.composer.services.RetrieveService;
import com.redhat.composer.util.mappers.MapperUtil;

import dev.langchain4j.rag.content.Content;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

/**
 * Api For Testing Store Retrievers.
 */
@Path("/retriver")
public class VectorRetriverApi {

  @Inject
  RetrieveService retrieveService;

  @Inject
  MapperUtil mapperUtil;

  /**
   * Retrieve sources.
   * @param request the RetrieverRequest
   * @param message the message
   * @return the list of SourceResponse
   */
  @POST
  @Path("/sources")
  public List<SourceResponse> retrieveSources(RetrieverRequest request, 
                                              @QueryParam("message") String message) {
    return retrieveService.retrieveContent(request, message).stream()
                          .map(VectorRetriverApi::toSourceResponse).toList();
  }

  /**
   * Retrieve sources.
   * @param content the Content returned from the retriever
   * @return source info
   */
  public static SourceResponse toSourceResponse(Content content) {
    SourceResponse response = new SourceResponse();
    response.setContent(content.textSegment().text());
    response.setMetadata(content.textSegment().metadata().toMap());
    return response;
  }

}
