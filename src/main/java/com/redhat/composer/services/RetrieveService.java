package com.redhat.composer.services;

import java.util.Collection;
import java.util.List;

import com.redhat.composer.config.retriever.contentretriever.BaseContentRetrieverClient;
import com.redhat.composer.config.retriever.contentretriever.ContentRetrieverClientFactory;
import com.redhat.composer.config.retriever.embeddingmodel.EmbeddingModelFactory;
import com.redhat.composer.model.enums.ContentRetrieverType;
import com.redhat.composer.model.request.RetrieverRequest;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentTransformer;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
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

/**
 * Service for retrieving content.
 */
@ApplicationScoped
public class RetrieveService {

  // Should these be configurable by assistant? Should it change to tokens?
  private static final int MAX_SEGMENT_SIZE_IN_CHARS = 4000;
  private static final int MAX_OVERLAP_SIZE_IN_CHARS = 200;

  @Inject
  ContentRetrieverClientFactory contentRetrieverClientFactory;

  @Inject
  EmbeddingModelFactory embeddingModelFactory;

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
   * A quick-and-dirty method to build a {@link ContentRetriever} from a collection of {@link Document}s. This uses
   * a temporary {@link InMemoryEmbeddingStore} for storage of the documents; however, a better implementation may
   * be to use a more permanent {@link EmbeddingStore}, and tag the ingested metadata (via use of
   * {@link EmbeddingStoreIngestor.Builder#documentTransformer(DocumentTransformer)}) to include metadata about the
   * specific user or specific chat session.
   *
   * @param documents      collection of input documents
   * @param maxResults     the value for
   *     {@link EmbeddingStoreContentRetriever.EmbeddingStoreContentRetrieverBuilder#maxResults(Integer)}
   * @param minScore       the value for
   *     {@link EmbeddingStoreContentRetriever.EmbeddingStoreContentRetrieverBuilder#minScore(Double)}
   * @return an {@link EmbeddingStoreContentRetriever} that will retrieve across
   *     {@link dev.langchain4j.data.embedding.Embedding}s created from the input documents
   */
  ContentRetriever contentRetrieverForDocuments(
      Collection<Document> documents,
      int maxResults,
      double minScore
  ) {
    InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    // TODO: Make this configurable?
    String embeddingModelName = EmbeddingModelFactory.DEFAULT_EMBEDDING;
    EmbeddingModel embeddingModel = embeddingModelFactory.getEmbeddingModel(embeddingModelName);

    EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
        .documentSplitter(DocumentSplitters.recursive(MAX_SEGMENT_SIZE_IN_CHARS, MAX_OVERLAP_SIZE_IN_CHARS))
        .embeddingModel(embeddingModel)
        .embeddingStore(embeddingStore)
        .build();

    ingestor.ingest(documents.toArray(new Document[0]));

    return EmbeddingStoreContentRetriever.builder()
        .displayName("Uploaded Documents")
        .embeddingModel(embeddingModel)
        .embeddingStore(embeddingStore)
        .maxResults(maxResults)
        .minScore(minScore)
        .build();
  }
}
