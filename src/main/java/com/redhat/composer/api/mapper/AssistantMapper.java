package com.redhat.composer.api.mapper;

import com.redhat.composer.api.model.AssistantChatMessage;
import com.redhat.composer.model.request.AssistantChatRequest;
import com.redhat.composer.util.mappers.QuarkusMapperConfig;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper to facilitate decoupling of REST interface from internal processing models
 * for Assistant APIs.
 */
@Mapper(config = QuarkusMapperConfig.class)
public interface AssistantMapper {

  /**
   * Map a chat request.
   *
   * @param request REST representation of a chat message request
   * @return internal representation of a chat message
   */
  AssistantChatRequest fromRest(AssistantChatMessage request);

}
