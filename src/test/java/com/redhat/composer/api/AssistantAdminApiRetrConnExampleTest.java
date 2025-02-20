package com.redhat.composer.api;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestHTTPEndpoint(ContentRetrieverConnectionAdminApi.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistantAdminApiRetrConnExampleTest {

  static final String EXAMPLE_JSON_REQUEST_ELASTIC = """
  {
  "baseRetrieverRequest": {
    "maxResults" : 100,
    "minScore" : "0.50",

    "contentRetrieverType": "elasticsearch",
    "index": "new-index",
    "user": "test_user",
    "host": "my-elastic-instance.svc:8080",
    "password" : "ABCDE12345"
  },
  "embeddingType": "nomic",
  "name": "Example Elastic Rag Connection",
  "description": "Example Elastic of a rag connection"
}    
""";

static final String EXAMPLE_JSON_REQUEST_WEAVIATE = """
{
  "baseRetrieverRequest": {
    "contentRetrieverType": "weaviate",
    "textKey": "source",
    "metadata": ["source","page_number", "title"], 
    "index": "my_custom_index",
    "scheme": "http",
    "host": "my-weaviate-instance.svc:8080",
    "apiKey" : "ABCDE12345",

    "maxResults" : 200,
    "minScore"   : "0.6"
  },
  "embeddingType": "nomic",
  "name": "Example Weaviate Rag Connection",
  "description": "Example of a Weaviate rag connection"
}
""";


  @Test
  @Order(10)
  public void createRetrieverConnectionExampleElastic() {

    given()
      .body( EXAMPLE_JSON_REQUEST_ELASTIC )
      .contentType( ContentType.JSON )
    .when()
      .post()
    .then()
      .statusCode( 200 )
      .body( "name", is( "Example Elastic Rag Connection" ) )
      .body( "connectionEntity.contentRetrieverType",  is( "elasticsearch") )
      .body( "connectionEntity.user",  is( "test_user" ) )
      .body( "connectionEntity.host",  is( "my-elastic-instance.svc:8080" ) )
      .body( "connectionEntity.index", is( "new-index" ) )
      .body( "connectionEntity.maxResults",  is( 100 ) )
      .body( "connectionEntity.minScore"  ,  is( Float.parseFloat( "0.5" ) ) );
      // Note that JSON will always convert back to a float value so the actual 
      // value here is <0.5F>, so force the comparison to use float from string.
      

  }


  @Test
  @Order(20)
  public void createRetrieverConnectionExampleWeaviate() {

    given()
      .body( EXAMPLE_JSON_REQUEST_WEAVIATE )
      .contentType( ContentType.JSON )
    .when()
      .post()
    .then()
      .statusCode( 200 )
      .body( "name", is( "Example Weaviate Rag Connection" ) )
      .body( "connectionEntity.textKey",  is( "source" ) )
      .body( "connectionEntity.host",  is( "my-weaviate-instance.svc:8080" ) )
      .body( "connectionEntity.index", is( "my_custom_index" ) )
      .body( "connectionEntity.maxResults",  is( 200 ) )
      .body( "connectionEntity.minScore"  ,  is( Float.parseFloat( "0.6" ) ) );
      // Note that JSON will always convert back to a float value so the actual 
      // value here is <0.5F>, so force the comparison to use float from string.
      

  }
  


}