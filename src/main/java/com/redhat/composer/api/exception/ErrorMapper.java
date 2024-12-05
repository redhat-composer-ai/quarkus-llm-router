package com.redhat.composer.api.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

/**
 * Maps exceptions to error responses.
 */
@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

  Logger log = Logger.getLogger(ErrorMapper.class);

  @Inject
  ObjectMapper objectMapper;

  @Override
  public Response toResponse(Exception exception) {
    log.error("Failed to handle request", exception);

    int code = 500;
    if (exception instanceof WebApplicationException) {
      code = ((WebApplicationException) exception).getResponse().getStatus();
    } else if (exception instanceof IllegalArgumentException) {
      code = 400; //bad-request
    }

    ObjectNode exceptionJson = objectMapper.createObjectNode();
    exceptionJson.put("code", code);

    if (exception.getMessage() != null) {
      exceptionJson.put("error", exception.getMessage());
    }

    return Response.status(code)
        .entity(exceptionJson)
        .build();
  }

}