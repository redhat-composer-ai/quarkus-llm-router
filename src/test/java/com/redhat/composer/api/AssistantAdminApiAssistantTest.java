package com.redhat.composer.api;

import com.redhat.composer.api.model.CreateAssistantRequest;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // TODO: Remove test ordering requirements
class AssistantAdminApiAssistantTest {

  // TODO: Don't keep state between tests; each test method should manage preconditions and validate postconditions to be self-contained
  private static CreateAssistantRequest createAssistantRequest;

  @BeforeAll
  public static void beforeAll() {
    createAssistantRequest = new CreateAssistantRequest();
    createAssistantRequest.setName("assistant");
    createAssistantRequest.setDescription("Assistant X");
  }

  @Test
  @Order(10)
  public void createAssistantShouldSucceed() {

    String llmConnectionId = given()
        .contentType(ContentType.JSON)
        .and()
        .body("""
            {
              "name":"conn1",
              "description": "Llm Connection"
            }
            """)
        .when()
        .post("/llm")
        .then()
        .extract()
        .path("id");


    createAssistantRequest.setLlmConnectionId(llmConnectionId);

    String assistantHexStringId = given()
        .contentType(ContentType.JSON)
        .and()
        .body(createAssistantRequest)
        .when()
        .post()
        .then()
        .assertThat()
        .body("id", notNullValue())
        .statusCode(
            Response.Status.OK.getStatusCode())
        .extract()
        .path("id");

    createAssistantRequest.setId(assistantHexStringId);
  }


  @Test
  @Order(20)
  public void retrieveAsssitants() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .get()
        .then()
        .assertThat()
        .body("$", Matchers.is(not(emptyArray())))
        .and()
        .body("", hasItem(hasEntry("id", createAssistantRequest.getId())))
        .statusCode(
            Response.Status.OK.getStatusCode());
  }


  @Test
  @Order(30)
  public void retrieveSpecificAsssitant() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("assistantId", createAssistantRequest.getId())
        .get("/{assistantId}")
        .then()
        .assertThat()
        .body("", hasEntry("id", createAssistantRequest.getId()))
        .statusCode(
            Response.Status.OK.getStatusCode());
  }


  @Test
  @Order(35)
  public void noyExistingAsssitant() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("assistantId", new ObjectId().toHexString())
        .get("/{assistantId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NOT_FOUND.getStatusCode());
  }

  @Test
  @Order(40)
  public void deleteAsssitant() {
    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("assistantId", createAssistantRequest.getId())
        .delete("/{assistantId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NO_CONTENT.getStatusCode());

    given()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .pathParams("assistantId", createAssistantRequest.getId())
        .get("/{assistantId}")
        .then()
        .assertThat()
        .statusCode(
            Response.Status.NOT_FOUND.getStatusCode());
  }


}