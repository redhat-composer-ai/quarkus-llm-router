package com.redhat.composer.config.retriever.contentretriever;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

//import com.redhat.composer.config.retriever.contentretriever.custom.WeaviateEmbeddingStoreCustom;
import com.redhat.composer.model.request.RetrieverRequest;
//import com.redhat.composer.model.request.retriever.WeaviateRequest;
import com.redhat.composer.model.request.retriever.ElasticsearchRequest;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import jakarta.inject.Singleton;

/**
 * Elasticsearch Content Retriever Client.
 */
@Singleton
public class ElasticsearchContentRetrieverClient extends BaseContentRetrieverClient {

  Logger log = Logger.getLogger(ElasticsearchContentRetrieverClient.class);

  @ConfigProperty(name = "elasticsearch.default.host")
  private String elasticHost;

  @ConfigProperty(name = "elasticsearch.default.user")
  private String elasticUser;

  @ConfigProperty(name = "elasticsearch.default.password")
  private String elasticPassword;

  @ConfigProperty(name = "elasticsearch.default.index")
  private String elasticIndex;

  /**
   * Get the Content Retriever.
   * @param request the RetrieverRequest
   * @return the Content Retriever
   */
  public ContentRetriever getContentRetriever(RetrieverRequest request) {
    ElasticsearchRequest elasticsearchRequest = (ElasticsearchRequest) request.getBaseRetrieverRequest();
    if (elasticsearchRequest == null) {
      elasticsearchRequest = new ElasticsearchRequest();
    }
    String host = elasticsearchRequest.getHost() != null ? elasticsearchRequest.getHost() : elasticHost;
    String user = elasticsearchRequest.getUser() != null ? elasticsearchRequest.getUser() : elasticUser;
    String pass = elasticsearchRequest.getPassword() != null ? elasticsearchRequest.getPassword() : elasticPassword;
    String index = elasticsearchRequest.getIndex() != null ? elasticsearchRequest.getIndex() : elasticIndex;

    // TODO: Make this configurable for different authentication types
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pass));

    RestClient restClient = RestClient
        .builder(HttpHost.create(host))
        .setHttpClientConfigCallback(httpClientBuilder -> {
          httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
          return httpClientBuilder;
        })
        .build();
  
    log.debug("Attempting to connect to Elasticsearch at " + host + " with index " + index);

    EmbeddingStore<TextSegment> store = ElasticsearchEmbeddingStore.builder()
                    .indexName(index)
                    .restClient(restClient)
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
