package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
public class DeleteSteps {
    private Response response;
    private String authToken;

    @Given("I am authenticated with user ID {string}")
    public void iAmAuthenticatedWithUserId(String userId) {
        RestAssured.baseURI = "http://localhost:3000";

        // Simulación de autenticación
        // Aquí debes implementar la lógica para obtener un token de autenticación o cualquier otro mecanismo de autenticación
        // En este ejemplo, se asume que el token se asigna directamente
        authToken = "Bearer dummy-token"; // Reemplaza esto con el token real si es necesario
        System.out.println("Authenticated with user ID: " + userId);
    }

    @When("I request to delete user by ID {string}")
    public void iRequestToDeleteUserById(String userId) {
        RestAssured.baseURI = "http://localhost:3000";

        // Realiza la solicitud DELETE al endpoint con el ID del usuario
        response = given()
                .header("Authorization", authToken)
                .when()
                .delete("/api/users/" + userId);

        // Imprime la respuesta en consola para fines de depuración
        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a {int} OK response")
    public void iShouldReceiveA200OKResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 200 (OK)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica si la respuesta contiene el mensaje esperado
        String successMessage = response.jsonPath().getString("message");
        System.out.println("Success message: " + successMessage);
    }

    @Then("I should receive a {int} Forbidden response")
    public void iShouldReceiveA403ForbiddenResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 403 (Forbidden)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica el mensaje de error en la respuesta
        String errorMessage = response.jsonPath().getString("message");
        System.out.println("Error message: " + errorMessage);
    }

    @Then("I should receive a {int} Not Found response")
    public void iShouldReceiveA404NotFoundResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 404 (Not Found)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica el mensaje de error en la respuesta
        String errorMessage = response.jsonPath().getString("message");
        System.out.println("Error message: " + errorMessage);
    }

    @Then("I should receive a {int} Bad Request response")
    public void iShouldReceiveABadRequestResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 400 (Bad Request)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica el mensaje de error en la respuesta
        String errorMessage = response.jsonPath().getString("message");
        System.out.println("Error message: " + errorMessage);
    }

    @Then("I should receive a {int} Internal Server Error response")
    public void iShouldReceiveA500InternalServerErrorResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 500 (Internal Server Error)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica el mensaje de error en la respuesta
        String errorMessage = response.jsonPath().getString("message");
        System.out.println("Error message: " + errorMessage);
    }

}
