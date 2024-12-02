package com.redhat.composer.beans;

import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

/**
 * WebClientProducer class.
 */
@ApplicationScoped
public class WebClientProducer {

  /**
   * Create a WebClient.
   * @param vertx the Vertx
   * @return the WebClient
   */
  @Produces
  @Singleton
  public WebClient createWebClient(Vertx vertx) {
    return WebClient.create(vertx);
  }
}
