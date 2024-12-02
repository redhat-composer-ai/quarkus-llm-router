package com.redhat.composer.api.validation;

import java.time.Duration;
import java.util.Map;
import java.util.Random;

import org.jboss.logging.Logger;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

/**
 * Assistant API for Chatting using assistants.
 */
@Path("/streaming/test")
@Authenticated
public class StreamingExample {

  Logger log = Logger.getLogger(StreamingExample.class);



  /**
   * Test
   */
  @GET
  @Path("basic")
  public Multi<String> streamBasic() {
    return Multi.createFrom().ticks().every(java.time.Duration.ofSeconds(1))
            .onItem().transform(n -> getRandomLetter());
  }

  @POST
  @Path("map")
  @Consumes(MediaType.APPLICATION_JSON)
  public Multi<String> steamMap(Map<String, Object> map) {
    return Multi.createFrom().items(map.entrySet().stream()
        .map(entry -> entry.getKey() + ": " + entry.getValue())
        ).onItem().transformToUniAndConcatenate(item -> Uni.createFrom().item(item)
            .onItem().delayIt().by(Duration.ofSeconds(1)));
  }

  private String getRandomLetter() {
    Random r = new Random();
    return String.valueOf((char) (r.nextInt(26) + 'a'));
  }
}
