# Admin Workflow

Backend includes the ability to dynamically create Assistants, Rag Connections, and LLM Connections

## Content Retriever(RAG) Connection

### Retriever Default Values and How to Override
> [!NOTE]
> **maxResults** and **minScore** values have default values that are set in _application.properties_ as below.  They can be overridden in various ways as shown in the  following example of setting **maxResults** to 100:

> Explicitly in a retriever request in JSON:
>```json
>{
>    // ...
>    "maxResults" : 100,
>    // ...
>}
>```
> setting system values:
>```java 
>  mvn quarkus:dev -Dconductor.retriever.document.max-results=100 
>```
>setting an environment variable:
>```bash 
> export CONDUCTOR_RETRIEVER_MAX_RESULTS=100
>```
>setting the **default** value in _application.properties_ :
>```java
> conductor.retriever.document.max-results = ${CONDUCTOR_RETRIEVER_MAX_RESULTS:100} 
>```
>and unsetting any environment vars and not passing a system value. 




### Elastic Example

```json
{
  "baseRetrieverRequest": {
    "contentRetrieverType": "elasticsearch",
    "index": "my_custom_index",
    "user": "test_user",
    "host": "my-elastic-instance.svc:8080",
    "password" : "ABCDE12345",
    "maxResults" : 100,
    "minScore" : 0.5

  },
  "embeddingType": "nomic",
  "name": "Example Elastic Rag Connection",
  "description": "Example of an ElasticSearch RAG connection"
}
```

### Weaviate Example

```json
{
  "baseRetrieverRequest": {
    "contentRetrieverType": "weaviate",
    "textKey": "source",
    "metadata": ["source","page_number", "title"], 
    "index": "my_custom_index",
    "scheme": "http",
    "host": "my-weaviate-instance.svc:8080",
    "apiKey" : "ABCDE12345",
    "maxResults" : 100,
    "minScore" : 0.5

  },
  "embeddingType": "nomic",
  "name": "Example Rag Connection",
  "description": "Example of a Weaviate RAG connection"
}
```

> [!NOTE]
> This document is a work in progress.
