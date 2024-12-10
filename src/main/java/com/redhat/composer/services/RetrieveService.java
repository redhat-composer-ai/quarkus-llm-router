package com.redhat.composer.services;

import com.redhat.composer.config.application.ContentRetrieverConfig;
import com.redhat.composer.config.retriever.contentretriever.BaseContentRetrieverClient;
import com.redhat.composer.config.retriever.contentretriever.ContentRetrieverClientFactory;
import com.redhat.composer.config.retriever.embeddingmodel.EmbeddingModelFactory;
import com.redhat.composer.model.enums.ContentRetrieverType;
import com.redhat.composer.model.request.RetrieverRequest;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.DocumentTransformer;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Service for retrieving content.
 */
@ApplicationScoped
public class RetrieveService {

  @Inject
  ContentRetrieverConfig retrieverConfig;

  @Inject
  ContentRetrieverClientFactory contentRetrieverClientFactory;

  @Inject
  EmbeddingModelFactory embeddingModelFactory;
  @Inject
  ContentRetrieverConfig contentRetrieverConfig;

  /**
   * Retrieves content from a given message.
   * @param request the request to retrieve content
   * @return the retrieved content
   */
  public ContentRetriever getContentRetriever(RetrieverRequest request) {
    ContentRetrieverType contentRetrieverType = ContentRetrieverType.fromString(
                          request.getBaseRetrieverRequest().getContentRetrieverType());
    BaseContentRetrieverClient client = contentRetrieverClientFactory
                          .getContentRetrieverClient(contentRetrieverType);
    return client.getContentRetriever(request);

  }

  /**
   * Retrieves content from a given message.
   * @param request the request to retrieve content
   * @param message the message to retrieve content from
   * @return the retrieved content
   */
  public List<Content> retrieveContent(RetrieverRequest request, String message) {
    ContentRetriever contentRetriever = getContentRetriever(request);
    Query query = Query.from(message);
    return contentRetriever.retrieve(query);
  }

  /**
   * A method to build a {@link ContentRetriever} from a collection of {@link Document}s. This uses
   * a temporary {@link InMemoryEmbeddingStore} for storage of the documents; however, a better implementation may
   * be to use a more permanent {@link EmbeddingStore}, and tag the ingested metadata (via use of
   * {@link EmbeddingStoreIngestor.Builder#documentTransformer(DocumentTransformer)}) to include metadata about the
   * specific user or specific chat session.
   *
   * <p>This overload uses defaults for document splitting, max results, and minimum score.
   *
   * @param documents      collection of input documents
   * @return an {@link EmbeddingStoreContentRetriever} that will retrieve across
   *     {@link dev.langchain4j.data.embedding.Embedding}s created from the input documents
   * @see #contentRetrieverForDocuments(Collection, String, Integer, Integer, Integer, Integer, Integer, Double)
   */
  ContentRetriever contentRetrieverForDocuments(
      Collection<Document> documents
  ) {
    return contentRetrieverForDocuments(
        documents,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
  }

  /**
   * A method to build a {@link ContentRetriever} from a collection of {@link Document}s. This uses
   * a temporary {@link InMemoryEmbeddingStore} for storage of the documents; however, a better implementation may
   * be to use a more permanent {@link EmbeddingStore}, and tag the ingested metadata (via use of
   * {@link EmbeddingStoreIngestor.Builder#documentTransformer(DocumentTransformer)}) to include metadata about the
   * specific user or specific chat session.
   *
   * <p>This overload uses defaults for document splitting configuration.
   *
   * @param documents      collection of input documents
   * @param maxResults     the value for
   *     {@link EmbeddingStoreContentRetriever.EmbeddingStoreContentRetrieverBuilder#maxResults(Integer)}
   * @param minScore       the value for
   *     {@link EmbeddingStoreContentRetriever.EmbeddingStoreContentRetrieverBuilder#minScore(Double)}
   * @return an {@link EmbeddingStoreContentRetriever} that will retrieve across
   *     {@link dev.langchain4j.data.embedding.Embedding}s created from the input documents
   * @see #contentRetrieverForDocuments(Collection, String, Integer, Integer, Integer, Integer, Integer, Double)
   */
  ContentRetriever contentRetrieverForDocuments(
      Collection<Document> documents,
      Integer maxResults,
      Double minScore
  ) {
    return contentRetrieverForDocuments(
        documents,
        null,
        null,
        null,
        null,
        null,
        maxResults,
        minScore
    );
  }

  /**
   * A method to build a {@link ContentRetriever} from a collection of {@link Document}s. This uses
   * a temporary {@link InMemoryEmbeddingStore} for storage of the documents; however, a better implementation may
   * be to use a more permanent {@link EmbeddingStore}, and tag the ingested metadata (via use of
   * {@link EmbeddingStoreIngestor.Builder#documentTransformer(DocumentTransformer)}) to include metadata about the
   * specific user or specific chat session.
   *
   * <p>If both maxSegmentTokens and overlapTokens are specified, these will take priority; otherwise, maxSegmentChars
   * and overlapChars will be used. If maxSegment
   *
   * @param documents           collection of input documents
   * @param embeddingModelName  the embedding model to use when ingesting documents
   * @param maxSegmentChars     optional maximum number of characters in a document segment
   * @param overlapChars        optional number of overlap characters across document segments
   * @param maxSegmentTokens    optional maximum number of tokens in a documnet segment
   * @param overlapTokens       optoinal number of overlap tokens across document segment
   * @param maxResults          the value for
   *     {@link EmbeddingStoreContentRetriever.EmbeddingStoreContentRetrieverBuilder#maxResults(Integer)}
   * @param minScore            the value for
   *     {@link EmbeddingStoreContentRetriever.EmbeddingStoreContentRetrieverBuilder#minScore(Double)}
   * @return an {@link EmbeddingStoreContentRetriever} that will retrieve across
   *     {@link dev.langchain4j.data.embedding.Embedding}s created from the input documents
   */
  ContentRetriever contentRetrieverForDocuments(
      Collection<Document> documents,
      String embeddingModelName,
      Integer maxSegmentChars,
      Integer overlapChars,
      Integer maxSegmentTokens,
      Integer overlapTokens,
      Integer maxResults,
      Double minScore
  ) {
    InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    EmbeddingModel embeddingModel = embeddingModelFactory.getEmbeddingModel(
        Objects.requireNonNullElseGet(
            embeddingModelName,
            retrieverConfig::defaultEmbeddingModel)
    );

    DocumentSplitter splitter = splitterForSettings(
        maxSegmentChars,
        overlapChars,
        maxSegmentTokens,
        overlapTokens,
        // TODO: Understand how to build a Tokenizer to match the one used by embeddingModel
        null
    );

    EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
        .documentSplitter(splitter)
        .embeddingModel(embeddingModel)
        .embeddingStore(embeddingStore)
        .build();

    ingestor.ingest(documents.toArray(new Document[0]));

    return EmbeddingStoreContentRetriever.builder()
        .displayName("Uploaded Documents")
        .embeddingModel(embeddingModel)
        .embeddingStore(embeddingStore)
        .maxResults(Objects.requireNonNullElseGet(
            maxResults,
            contentRetrieverConfig.document()::defaultMaxResults))
        .minScore(Objects.requireNonNullElseGet(
            minScore,
            contentRetrieverConfig.document()::defaultMinScore))
        .build();
  }

  private DocumentSplitter splitterForSettings(
      Integer maxSegmentSizeInChars,
      Integer overlapSizeInChars,
      Integer maxSegmentSizeInTokens,
      Integer overlapSizeInTokens,
      Tokenizer tokenizer
  ) {
    boolean useTokens;

    if (tokenizer == null) {
      // If there is no tokenizer, segments have to be split by character
      useTokens = false;
    } else if (maxSegmentSizeInTokens != null && overlapSizeInTokens != null) {
      // If there's a tokenizer and token configuration, use tokens to split
      useTokens = true;
    } else {
      // If there's a tokenizer but no token configuration, use character configuration if it's explicitly provided
      // in the method call
      useTokens = maxSegmentSizeInChars == null
          && overlapSizeInChars == null
          // Otherwise, use tokens only if defaults are configured. Final fallback will be default character configs
          && contentRetrieverConfig.document().split().defaultSegmentSizeInTokens().isPresent()
          && contentRetrieverConfig.document().split().defaultSegmentOverlapInTokens().isPresent();
    }

    if (useTokens) {
      return DocumentSplitters.recursive(
          Objects.requireNonNullElseGet(
              maxSegmentSizeInTokens,
              contentRetrieverConfig.document().split().defaultSegmentSizeInTokens()::get),
          Objects.requireNonNullElseGet(
              overlapSizeInTokens,
              contentRetrieverConfig.document().split().defaultSegmentOverlapInTokens()::get),
          tokenizer
      );
    } else {
      return DocumentSplitters.recursive(
          Objects.requireNonNullElseGet(
              maxSegmentSizeInChars,
              contentRetrieverConfig.document().split()::defaultSegmentSizeInCharacters),
          Objects.requireNonNullElseGet(
              overlapSizeInChars,
              contentRetrieverConfig.document().split()::defaultSegmentOverlapInCharacters)
      );
    }
  }
}
