package com.redhat.composer.model.enums;

/**
 * Content Retriever Type.
 */
public enum ContentRetrieverType {

  WEAVIATE("weaviate"),
  NEO4J("neo4j"),
  ELASTICSEARCH("elasticsearch");

  private final String type;

  ContentRetrieverType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  /**
   * Get the Content Retriever Type from String.
   * @param type string representation of the type
   * @return the ContentRetrieverType
   */
  public static ContentRetrieverType fromString(String type) {
    for (ContentRetrieverType retrieverType : ContentRetrieverType.values()) {
      if (retrieverType.getType().equalsIgnoreCase(type)) {
        return retrieverType;
      }
    }
    throw new IllegalArgumentException("No constant with type " + type + " found");
  }
  
}
