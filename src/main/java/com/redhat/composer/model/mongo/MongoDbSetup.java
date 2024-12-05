package com.redhat.composer.model.mongo;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.bson.Document;

/**
 * Adds unique constraints to fields in the admin collections: llm, retriever and assistants.
 */
@ApplicationScoped
public class MongoDbSetup {

  @Inject
  MongoClient mongoClient;

  public void onStart(@Observes StartupEvent event) {
    MongoDatabase database = mongoClient.getDatabase("composer");
    MongoCollection<Document> llmConnectionCollection = database.getCollection("llm_connection");
    llmConnectionCollection.createIndex(new Document("name", 1), new IndexOptions().unique(true));

    MongoCollection<Document> assistantCollection = database.getCollection("assistant");
    assistantCollection.createIndex(new Document("name", 1), new IndexOptions().unique(true));

    MongoCollection<Document> retrieverConnectionCollection = database.getCollection("retriever_connection");
    retrieverConnectionCollection.createIndex(new Document("name", 1), new IndexOptions().unique(true));

  }
}
