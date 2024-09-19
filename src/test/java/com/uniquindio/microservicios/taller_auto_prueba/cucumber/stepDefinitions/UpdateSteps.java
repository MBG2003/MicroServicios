package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class UpdateSteps {

    private Response response;
    private String userId;

    @Given("I am authenticated with user ID {string}")
    public void iAmAuthenticatedWithUserId(String id) {
        // Configurar la autenticación y guardar el user ID
        this.userId = id;
        System.out.println("Authenticated as user with ID: " + userId);
    }

    @When("I request to update user by ID {string} with valid data")
    public void iRequestToUpdateUserByIdWithValidData(String id) {
        // Configuración de la URL base para RestAssured
        RestAssured.baseURI = "http://localhost:3000";

        // Crear el payload con datos de ejemplo para la actualización
        String payload = "{ \"name\": \"Updated Name\", \"email\": \"updated.email@example.com\" }";

        // Realizar la petición PUT al endpoint de actualización de usuario con el payload
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/users/" + id);

        // Imprimir la respuesta en consola para fines de depuración
        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I request to update user by ID {string} with an empty request body")
    public void iRequestToUpdateUserByIdWithAnEmptyRequestBody(String id) {
        RestAssured.baseURI = "http://localhost:3000";

        // Crear un payload vacío
        String payload = "{}";

        // Realizar la petición PUT con un cuerpo vacío
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/users/" + id);

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a {int} response with the updated user details")
    public void iShouldReceiveAResponseWithTheUpdatedUserDetails(int statusCode) {
        // Verificar que la respuesta tenga el código de estado esperado
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verificar los detalles del usuario actualizado
        System.out.println("Updated user details: " + response.getBody().asString());
    }

    @Then("I should receive a {int} response with a message {string}")
    public void iShouldReceiveAResponseWithMessage(int statusCode, String message) {
        // Verificar que la respuesta tenga el código de estado esperado
        assertEquals(statusCode, response.getStatusCode());

        // Verificar el mensaje de la respuesta
        String responseMessage = response.jsonPath().getString("message");
        assertEquals(message, responseMessage);

        System.out.println("Error message: " + responseMessage);
    }
}
