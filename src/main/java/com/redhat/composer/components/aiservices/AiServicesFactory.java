package com.redhat.composer.components.aiservices;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Factory for AI Services.
 */
@ApplicationScoped
public class AiServicesFactory {

  public static final String MISTRAL_AI_SERVICE = "mistral";

  public static final String GRANITE_AI_SERVICE = "granite";

  @ConfigProperty(name = "model.default.type")
  private String defaultModel;

  /**
   * Get the AI service class.
   * @param aiServiceType the AI service type
   * @return the AI service class
   */
  public Class<? extends BaseAiService> getAiService(String aiServiceType) {

    aiServiceType = aiServiceType == null ? defaultModel : aiServiceType;

    switch (aiServiceType) {
      case MISTRAL_AI_SERVICE:
        return MistralAiService.class;
      case GRANITE_AI_SERVICE:
        return GraniteAiService.class;
      default:
        throw new RuntimeException("AI service type not found: " + aiServiceType);
    }
  }

}
