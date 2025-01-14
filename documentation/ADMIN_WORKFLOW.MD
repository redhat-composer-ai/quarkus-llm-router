# Admin Workflow

Backend includes the ability to dynamically create Assistants, Rag Connections, and LLM Connections

## Content Retriever(RAG) Connection

### Weaviate Example

{
  "name": "test",
  "description": "test",
  "baseRetrieverRequest": {
    "contentRetrieverType": "elastic",
    "host": "test",
    "index": "test",
    "apiKey": "test",
    "username": "test",
    "password": "test",
    "scheme": "test",
    "textKey": "test"
  },
  "embeddingType": "nomic"
}

```json
{
  "baseRetrieverRequest": {
    "contentRetrieverType": "weaviate",
    "textKey": "source",
    "metadata": ["source","page_number", "title"], 
    "index": "my_custom_index",
    "scheme": "http",
    "host": "my-weaviate-instance.svc:8080",
    "apiKey" : "ABCDE12345"
  },
  "embeddingType": "nomic",
  "name": "Example Rag Connection",
  "description": "Example of a rag connection"
}
```

> [!NOTE]
> This document is a work in progress.
