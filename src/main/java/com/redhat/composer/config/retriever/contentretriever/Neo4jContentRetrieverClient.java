package com.redhat.composer.config.retriever.contentretriever;

import com.redhat.composer.config.llm.models.synchronous.SynchronousBaseModel;
import com.redhat.composer.config.llm.models.synchronous.SynchronousModelFactory;
import com.redhat.composer.model.request.LLMRequest;
import com.redhat.composer.model.request.RetrieverRequest;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.neo4j.Neo4jContentRetriever;
import dev.langchain4j.store.graph.neo4j.Neo4jGraph;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.logging.Level;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.neo4j.driver.*;

/**
 * Neo4j Content Retriever Client.
 */
@Singleton
public class Neo4jContentRetrieverClient extends BaseContentRetrieverClient {

  @ConfigProperty(name = "neo4j.default.url")
  private String neo4jUrl;
  @ConfigProperty( name = "neo4j.default.username")
  private String neo4jUser;
  @ConfigProperty( name = "neo4j.default.password")
  private String neo4jPassword;

  Logger log = Logger.getLogger(Neo4jContentRetrieverClient.class);

  static ContentRetriever RETRIEVER;

  @Inject SynchronousModelFactory synchronousModelFactory;

  // Task: Generate Accurate Cypher Statement to Query a Neo4J Graph
  // Database About a Patients.
  final PromptTemplate PROMPT_TEMPLATE =
      PromptTemplate.from(
          """
You are Data scientist with deep understanding of Neo4J and the Cypher query language. You have great experience in healthcare. You return high quality Cypher queries based on our Neo4J nodes, relations and properties listed below.

## Rules:
1. Only use valid Cypher language, don't mix this with SQL or HTML.
2. Only return the Cypher query without formatting, explanation and markdown. We need it to inject to Neo4J.
3. Always do lowercase when matching with text.
4. If you cannot answer the question return empty string.
5. Only use the nodes, relationships and properties listed below.
6. When using relationships, make sure to use the correct direction.
7. Do not use BETWEEN to compare dates.
8. For date conversions use: datetime(ISODATE).epochMillis
9. Expect dates in future years like 2122

Schema:
{{schema}}

***Example usage***
Find all patients:
MATCH (p:Patient)
RETURN p

The user question is:
{{question}}
\s""");

  /**
   * Get the Content Retriever.
   * @param request the RetrieverRequest
   * @return the Content Retriever
   */
  public ContentRetriever getContentRetriever(RetrieverRequest request) {
    if (RETRIEVER == null) {
      //      Driver driver =
      //          GraphDatabase.driver(
      //              neo4jUrl,
      //              AuthTokens.basic(neo4jUser, neo4jPassword),
      //              Config.builder().withLogging(Logging.console(Level.FINE)).build());
      Driver driver = GraphDatabase.driver(neo4jUrl, AuthTokens.basic(neo4jUser, neo4jPassword));

      Neo4jGraph graph = Neo4jGraph.builder().driver(driver).build();

      graph.refreshSchema();
      log.info(graph.getSchema());

      // TODO: This should be pulled from the request ... I think (need to think about this more)
      SynchronousBaseModel baseModel =
          synchronousModelFactory.getModel(SynchronousModelFactory.DEFAULT_MODEL);
      ChatLanguageModel chatLanguageModel = baseModel.getChatModel(new LLMRequest());

      RETRIEVER =
          Neo4jContentRetriever.builder()
              .graph(graph)
              .chatLanguageModel(chatLanguageModel)
              .promptTemplate(PROMPT_TEMPLATE)
              .build();
    }

    return RETRIEVER;
  }
}
