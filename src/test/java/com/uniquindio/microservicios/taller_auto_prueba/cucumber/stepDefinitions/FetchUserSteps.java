package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class FetchUserSteps {

    private Response response;

    @Given("I am authenticated")
    public void iAmAuthenticated() {
        // Simulación de autenticación
        System.out.println("User authenticated");
    }

    @When("I request to partially update user by ID {string} with valid data")
    public void iRequestToPartiallyUpdateUserByIdWithValidData(String id) {
        // Configuración de la URL base para RestAssured
        RestAssured.baseURI = "http://localhost:3000";

        // Crea el payload con los datos válidos de actualización
        String payload = "{ \"username\": \"updatedUser\" }";

        // Realizar la petición PATCH al endpoint de actualización parcial de usuario
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .patch("/api/users/" + id);

        // Imprimir la respuesta en consola para fines de depuración
        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I request to partially update user by ID {string} with an empty request body")
    public void iRequestToPartiallyUpdateUserByIdWithAnEmptyRequestBody(String id) {
        // Configuración de la URL base para RestAssured
        RestAssured.baseURI = "http://localhost:3000";

        // Realizar la petición PATCH con un cuerpo vacío
        String payload = "{}";
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .patch("/api/users/" + id);

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a {int} OK response with the updated user details")
    public void iShouldReceiveAnOkResponseWithTheUpdatedUserDetails(int statusCode) {
        // Verificar que la respuesta tenga el código de estado 200 (OK)
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

        System.out.println("Response message: " + responseMessage);
    }
}
