package com.redhat.composer.api.mapper;

import com.redhat.composer.api.model.Assistant;
import com.redhat.composer.api.model.CreateAssistantRequest;
import com.redhat.composer.model.mongo.AssistantEntity;
import com.redhat.composer.model.request.AssistantCreationRequest;
import com.redhat.composer.model.response.AssistantResponse;
import com.redhat.composer.util.mappers.BsonMapper;
import com.redhat.composer.util.mappers.QuarkusMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

/**
 * MapStruct mapper to facilitate decoupling of REST interface from internal processing models
 * for Assistant Admin APIs.
 */
@Mapper(
    config = QuarkusMapperConfig.class,
    uses = {
        BsonMapper.class,
        ContentRetrieverConnectionAdminMapper.class,
        LlmConnectionAdminMapper.class
    }
)
public interface AssistantAdminMapper {

  /**
   * Map assistant creation requests.
   *
   * @param request the REST representation of a request
   * @return the internal representation of a request
   */
  AssistantCreationRequest fromRest(CreateAssistantRequest request);

  /**
   * Map assistant.
   *
   * @param assistant the internal entity
   * @return a REST representation of the entity
   */
  @SubclassMapping(target = Assistant.class, source = AssistantResponse.class)
  Assistant toRest(AssistantEntity assistant);

}
