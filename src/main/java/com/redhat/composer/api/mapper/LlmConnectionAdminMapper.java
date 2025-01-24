package com.redhat.composer.api.mapper;

import com.redhat.composer.api.model.CreateLlmConnectionRequest;
import com.redhat.composer.api.model.LLMConnection;
import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.util.mappers.BsonMapper;
import com.redhat.composer.util.mappers.QuarkusMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper to facilitate decoupling of REST interface from internal processing models
 * for Assistant Admin APIs.
 */
@Mapper(
    config = QuarkusMapperConfig.class,
    uses = {
        BsonMapper.class
    }
)
public interface LlmConnectionAdminMapper {

  /**
   * Map LLM creation requests.
   *
   * @param request the REST representation of a request
   * @return the internal representation of a request
   */
  LlmConnectionEntity fromRest(CreateLlmConnectionRequest request);

  /**
   * Map LLM connection.
   *
   * @param llmConnectionEntity the internal entity
   * @return a REST representation of the entity
   */
  // TODO: Scrubbing sensitive information should be done in the service layer probably
  @Mapping(target = "apiKey", ignore = true)
  LLMConnection toRest(LlmConnectionEntity llmConnectionEntity);

}
