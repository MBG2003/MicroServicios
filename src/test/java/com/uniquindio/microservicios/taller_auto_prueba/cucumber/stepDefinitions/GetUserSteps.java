package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class GetUserSteps {

    private Response response;
    private String authenticatedUserId;

    @Given("I am authenticated with user ID {string}")
    public void iAmAuthenticatedWithUserId(String userId) {
        // Simulación de la autenticación
        authenticatedUserId = userId;
        System.out.println("Authenticated user with ID: " + userId);
    }

    @When("I request to get user by ID {string}")
    public void iRequestToGetUserById(String userId) {
        RestAssured.baseURI = "http://localhost:3000";

        // Realiza la petición GET al endpoint para obtener el usuario por ID
        response = given()
                .header("Content-Type", "application/json")
                .get("/api/users/" + userId);

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a 200 OK response with the user details")
    public void iShouldReceive200OkWithUserDetails() {
        assertEquals(200, response.getStatusCode());

        // Opcional: Verificar que la respuesta contiene detalles del usuario
        String userId = response.jsonPath().getString("id");
        assertEquals(authenticatedUserId, userId);
    }

    @Then("I should receive a 403 Forbidden response with a message {string}")
    public void iShouldReceive403ForbiddenWithMessage(String message) {
        assertEquals(403, response.getStatusCode());

        String responseMessage = response.jsonPath().getString("message");
        assertEquals(message, responseMessage);
    }

    @Then("I should receive a 404 Not Found response with a message {string}")
    public void iShouldReceive404NotFoundWithMessage(String message) {
        assertEquals(404, response.getStatusCode());

        String responseMessage = response.jsonPath().getString("message");
        assertEquals(message, responseMessage);
    }

    @Then("I should receive a 400 Bad Request response with a message {string}")
    public void iShouldReceive400BadRequestWithMessage(String message) {
        assertEquals(400, response.getStatusCode());

        String responseMessage = response.jsonPath().getString("message");
        assertEquals(message, responseMessage);
    }
}
