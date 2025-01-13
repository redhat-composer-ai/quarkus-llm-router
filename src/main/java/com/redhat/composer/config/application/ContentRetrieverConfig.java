package com.redhat.composer.config.application;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

/**
 * Configuration options related to Content Retrievers in general.
 */
@ConfigMapping(prefix = "conductor.retriever")
public interface ContentRetrieverConfig {

  /**
   * Default embedding model name.
   *
   * @return the model name
   */
  @WithDefault("nomic")
  @NotBlank
  String defaultEmbeddingModel();

  /**
   * Configuration specific to retrieval from uploaded documents.
   *
   * @return object for document-specific config
   */
  DocumentRetrieverConfig document();

  /**
   * Configuration options related to Content Retrievers set up around uploaded documents.
   */
  interface DocumentRetrieverConfig {

    /**
     * The maximum number of results when content retrieving across an uploaded document.
     *
     * @return a max number of results
     */
    @WithDefault("5")
    @Min(1)
    Integer defaultMaxResults();

    /**
     * The minimum score for results when content retrieving across an uploaded document.
     *
     * @return a minimum result score
     */
    @WithDefault("0.75")
    @DecimalMin("0.01")
    Double defaultMinScore();

    /**
     * Configuration specific to how uploaded documents are split during ingestion.
     *
     * @return object for document-splitting configuration
     */
    DocumentSplitConfig split();

    /**
     * Configuration options related to splitting of uploaded documents during ingestion.
     */
    interface DocumentSplitConfig {

      /**
       * The maximum segment size when splitting uploaded documents during ingestion, specified in characters.
       *
       * <p>As an example, this may be set in application.properties as
       * {@code conductor.retriever.document.split.default-segment-size-in-characters=4000}.
       *
       * <p>Similarly, it may be set as an environment variable as
       * {@code CONDUCTOR_RETRIEVER_DOCUMENT_SPLIT_DEFAULT_SEGMENT_SIZE_IN_CHARACTERS=4000}.
       *
       * @return max segment size
       */
      @WithDefault("4000")
      @Min(50)
      Integer defaultSegmentSizeInCharacters();

      /**
       * The segment overlap size when splitting uploaded documents during ingestion, specified in characters.
       *
       * <p>As an example, this may be set in application.properties as
       * {@code conductor.retriever.document.split.default-segment-overlap-in-characters=200}.
       *
       * <p>Similarly, it may be set as an environment variable as
       * {@code CONDUCTOR_RETRIEVER_DOCUMENT_SPLIT_DEFAULT_SEGMENT_OVERLAP_IN_CHARACTERS=200}.
       *
       * @return segment overlap size
       */
      @WithDefault("200")
      @Min(0)
      Integer defaultSegmentOverlapInCharacters();

      /**
       * The maximum segment size when splitting uploaded documents during ingestion, specified in tokens.
       *
       * <p>As an example, this may be set in application.properties as
       * {@code conductor.retriever.document.split.default-segment-size-in-tokens=4000}.
       *
       * <p>Similarly, it may be set as an environment variable as
       * {@code CONDUCTOR_RETRIEVER_DOCUMENT_SPLIT_DEFAULT_SEGMENT_SIZE_IN_TOKENS=4000}.
       *
       * @return max segment size
       */
      // TODO: Add @Min(50) back after ensuring Optional<Integer> stays validated in prod builds
      Optional<Integer> defaultSegmentSizeInTokens();

      /**
       * The segment overlap size when splitting uploaded documents during ingestion, specified in tokens.
       *
       * <p>As an example, this may be set in application.properties as
       * {@code conductor.retriever.document.split.default-segment-overlap-in-tokens=200}.
       *
       * <p>Similarly, it may be set as an environment variable as
       * {@code CONDUCTOR_RETRIEVER_DOCUMENT_SPLIT_DEFAULT_SEGMENT_OVERLAP_IN_TOKENS=200}.
       *
       * @return segment overlap size
       */
      // TODO: Add @Min(0) back after ensuring Optional<Integer> stays validated in prod builds
      Optional<Integer> defaultSegmentOverlapInTokens();

    }

  }

}
