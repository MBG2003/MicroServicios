package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class GetAllUsersSteps {

    private Response response;

    @Given("I am authenticated")
    public void iAmAuthenticated() {
        // Simulación de la autenticación
        System.out.println("Authenticated user");
    }

    @When("I request to get all users")
    public void iRequestToGetAllUsers() {
        RestAssured.baseURI = "http://localhost:3000";

        // Realiza la petición GET al endpoint para obtener todos los usuarios
        response = given()
                .header("Content-Type", "application/json")
                .get("/api/users");

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a 200 OK response with a list of users")
    public void iShouldReceive200OkWithListOfUsers() {
        assertEquals(200, response.getStatusCode());

        // Opcional: Verificar que la respuesta contiene una lista de usuarios
        assert response.jsonPath().getList("$").size() > 0;
    }

    @Then("I should receive a 404 Not Found response with a message {string}")
    public void iShouldReceive404NotFoundWithMessage(String message) {
        assertEquals(404, response.getStatusCode());

        String responseMessage = response.jsonPath().getString("message");
        assertEquals(message, responseMessage);
    }

    @Then("I should receive a 500 Internal Server Error response with a message {string}")
    public void iShouldReceive500InternalServerErrorWithMessage(String message) {
        assertEquals(500, response.getStatusCode());

        String responseMessage = response.jsonPath().getString("message");
        assertEquals(message, responseMessage);
    }
}
