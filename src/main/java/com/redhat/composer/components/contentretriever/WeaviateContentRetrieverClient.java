package com.redhat.composer.components.contentretriever;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.redhat.composer.components.contentretriever.custom.WeaviateEmbeddingStoreCustom;
import com.redhat.composer.model.request.RetrieverRequest;
import com.redhat.composer.model.request.retriever.WeaviateRequest;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import jakarta.inject.Singleton;

/**
 * Weaviate Content Retriever Client.
 */
@Singleton
public class WeaviateContentRetrieverClient extends BaseContentRetrieverClient {

  Logger log = Logger.getLogger(WeaviateContentRetrieverClient.class);

  @ConfigProperty(name = "weaviate.default.scheme")
  private String weaviateScheme;

  @ConfigProperty(name = "weaviate.default.host")
  private String weaviateHost;

  @ConfigProperty(name = "weaviate.default.apiKey")
  private String weaviateApiKey;

  @ConfigProperty(name = "weaviate.default.index")
  private String weaviateIndex;

  @ConfigProperty(name = "weaviate.default.textKey")
  private String weaviateTextKey;

  /**
   * Get the Content Retriever.
   * @param request the RetrieverRequest
   * @return the Content Retriever
   */
  public ContentRetriever getContentRetriever(RetrieverRequest request) {
    WeaviateRequest weaviateRequest = (WeaviateRequest) request.getBaseRetrieverRequest();
    if (weaviateRequest == null) {
      weaviateRequest = new WeaviateRequest();
    }
    String scheme = weaviateRequest.getScheme() != null ? weaviateRequest.getScheme() : weaviateScheme;
    String host = weaviateRequest.getHost() != null ? weaviateRequest.getHost() : weaviateHost;
    String apiKey = weaviateRequest.getApiKey() != null ? weaviateRequest.getApiKey() : weaviateApiKey;
    String index = weaviateRequest.getIndex() != null ? weaviateRequest.getIndex() : weaviateIndex;
    String textKey = weaviateRequest.getTextKey() != null ? weaviateRequest.getTextKey() : weaviateTextKey;


    log.info("Attempting to connect to Weaviate at " + scheme + "://" + host + " with index " + index);

    WeaviateEmbeddingStoreCustom store = WeaviateEmbeddingStoreCustom.builder()
          .scheme(scheme)
          .host(host)
          .apiKey(apiKey)
          .metadataFieldName("")
          // .metadataFieldName(null)
          .metadataKeys(weaviateRequest.getMetadataFields())
          .objectClass(index)
          .avoidDups(true)
          .textFieldName(textKey)
        .build();


    // Retrieve the embedding model
    EmbeddingModel embeddingModel = getEmbeddingModel(request.getEmbeddingType());



    // Create the content retriever
    ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
          .embeddingStore(store)
          .embeddingModel(embeddingModel)
        .build();

    return contentRetriever;
  }

}
