package com.redhat.composer.api;

import com.redhat.composer.model.mongo.LlmConnectionEntity;
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
@TestHTTPEndpoint(LlmConnectionAdminApi.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssistantAdminApiLlmConnectionTest {

  // TODO: Don't keep state between tests; each test method should be fully self-contained
  // TODO: Use REST model for interacting with REST API, not database entity model
  private static LlmConnectionEntity llmConnectionEntity;

  @BeforeAll
  public static void beforeAll() {
    llmConnectionEntity = new LlmConnectionEntity();
    llmConnectionEntity.setName("llm_name");
    llmConnectionEntity.setDescription("LLM Connection X");
  }

  @Test
  @Order(10)
  public void createLlmConnectionShouldSucceed() {
    String hexStringId = given()
        .contentType(ContentType.JSON)
        .and()
        .body(llmConnectionEntity)
        .when()
        .post()
        .then()
        .assertThat()
        .body("id", notNullValue())
        .statusCode(
            Response.Status.OK.getStatusCode())
        .extract()
        .path("id");

    llmConnectionEntity.id = new ObjectId(hexStringId);
  }


  @Test
  @Order(20)
  public void retrieveLlmConnections() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .get()
        .then()
        .assertThat()
        .body("$", Matchers.is(not(emptyArray())))
        .and()
        .body("", hasItem(hasEntry("id", llmConnectionEntity.id.toHexString())))
        .statusCode(
            Response.Status.OK.getStatusCode());
  }


  @Test
  @Order(30)
  public void retrieveSpecificLlmConnection() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("llmConnectionId", llmConnectionEntity.id.toHexString())
        .get("/{llmConnectionId}")
        .then()
        .assertThat()
        .body("", hasEntry("id", llmConnectionEntity.id.toHexString()))
        .statusCode(
            Response.Status.OK.getStatusCode());
  }


  @Test
  @Order(35)
  public void noyExistingLlmConnection() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("llmConnectionId", new ObjectId().toHexString())
        .get("/{llmConnectionId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NOT_FOUND.getStatusCode());
  }

  @Test
  @Order(40)
  public void deleteLlmConnection() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("llmConnectionId", llmConnectionEntity.id.toHexString())
        .delete("/{llmConnectionId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NO_CONTENT.getStatusCode());

    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("llmConnectionId", llmConnectionEntity.id.toHexString())
        .get("/{llmConnectionId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NOT_FOUND.getStatusCode());
  }


}