package com.redhat.composer.api;

import com.redhat.composer.model.mongo.LlmConnectionEntity;
import com.redhat.composer.model.mongo.RetrieverConnectionEntity;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(AssistantAdminApi.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistantAdminApiRetrieverConnectionTest {

  private static RetrieverConnectionEntity retrieverConnectionEntity;

  @BeforeAll
  public static void beforeAll() {
    retrieverConnectionEntity = new RetrieverConnectionEntity();
    retrieverConnectionEntity.setName("retriever_connection");
    retrieverConnectionEntity.setDescription("Retriever Connection X");
  }

  @Test
  @Order(10)
  public void createRetrieverConnectionShouldSucceed() {
    String hexStringId = given()
        .contentType(ContentType.JSON)
        .and()
        .body(retrieverConnectionEntity)
        .when()
        .post("/retrieverConnection")
        .then()
        .assertThat()
        .body("id", notNullValue())
        .statusCode(
            Response.Status.OK.getStatusCode())
        .extract()
        .path("id");

    retrieverConnectionEntity.id = new ObjectId(hexStringId);
  }


  @Test
  @Order(20)
  public void retrieveRetrieverConnections() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .get("/retrieverConnection")
        .then()
        .assertThat()
        .body("$", Matchers.is(not(emptyArray())))
        .and()
        .body("", hasItem(hasEntry("id", retrieverConnectionEntity.id.toHexString())))
        .statusCode(
            Response.Status.OK.getStatusCode());
  }


  @Test
  @Order(30)
  public void retrieveSpecificRetrieverConnection() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("retrieverConnectionId", retrieverConnectionEntity.id.toHexString())
        .get("/retrieverConnection/{retrieverConnectionId}")
        .then()
        .assertThat()
        .body("", hasEntry("id", retrieverConnectionEntity.id.toHexString()))
        .statusCode(
            Response.Status.OK.getStatusCode());
  }


  @Test
  @Order(35)
  public void noyExistingRetrieverConnection() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("retrieverConnectionId", new ObjectId().toHexString())
        .get("/retrieverConnection/{retrieverConnectionId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NOT_FOUND.getStatusCode());
  }

  @Test
  @Order(40)
  public void deleteRetrieverConnection() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("retrieverConnectionId", retrieverConnectionEntity.id.toHexString())
        .delete("/retrieverConnection/{retrieverConnectionId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NO_CONTENT.getStatusCode());

    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("retrieverConnectionId", retrieverConnectionEntity.id.toHexString())
        .get("/retrieverConnection/{retrieverConnectionId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NOT_FOUND.getStatusCode());
  }


}