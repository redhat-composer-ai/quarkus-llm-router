package com.redhat.composer.model.mongo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.jackson.ObjectIdDeserializer;
import org.bson.types.ObjectId;

/**
 * BaseEntity.
 */
public class BaseEntity extends PanacheMongoEntity {

  
  @JsonDeserialize(using = ObjectIdDeserializer.class)
  public ObjectId id;
  
  
}
