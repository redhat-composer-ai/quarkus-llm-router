package com.redhat.composer.config.retriever.contentretriever;

import java.util.List;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;

public class CustomContentRetriever implements ContentRetriever {

    @Override
    public List<Content> retrieve(Query query) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'retrieve'");
    } 
}
