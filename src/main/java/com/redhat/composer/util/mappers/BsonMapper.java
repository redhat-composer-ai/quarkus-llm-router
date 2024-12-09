package com.redhat.composer.util.mappers;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

/**
 * Mapper to support conversions related to BSON, such as the ObjectId class.
 */
@Mapper(config = QuarkusMapperConfig.class)
public interface BsonMapper {

  /**
   * Map a BSON object ID.
   *
   * @param id the hex string representation of a BSON object ID
   * @return the BSON object ID
   */
  default ObjectId toBsonObjectId(String id) {
    if (id == null) {
      return null;
    }

    return new ObjectId(id);
  }

  /**
   * Map a BSON object ID.
   *
   * @param id the BSON object ID
   * @return a hex string representation of the BSON object ID
   */
  default String fromBsonObjectId(ObjectId id) {
    if (id == null) {
      return null;
    }

    return id.toHexString();
  }

}
