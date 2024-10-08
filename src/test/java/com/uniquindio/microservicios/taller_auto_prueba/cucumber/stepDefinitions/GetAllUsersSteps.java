package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.DataManager;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.ResponseUtils;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetAllUsersSteps {

    private Response response;
    private String authToken;

    @Given("I am authenticated with a valid token for users")
    public void iAmAuthenticatedWithValidToken() {
        // Simulación de la autenticación y obtención del token usando UserManager
        UserManager userManager = UserManager.getInstance();
        userManager.registerNewUser(); // Registra un nuevo usuario

        // Inicia sesión y obtiene el token
        authToken = userManager.loginAndGetToken();
        DataManager.getInstance().setAuthToken("currentToken", authToken); // Guarda el token
        System.out.println("Authenticated user with token: " + authToken);
    }

    @When("I request to get all users")
    public void iRequestToGetAllUsers() {
        RestAssured.baseURI = "http://192.168.199.138:3000";

        // Realiza la petición GET al endpoint para obtener todos los usuarios
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken) // Incluye el token en el encabezado
                .get("/api/users");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I request to get all users and there are no users")
    public void iRequestToGetAllUsersAndThereAreNoUsers() {
        RestAssured.baseURI = "http://192.168.199.138:3000";

        // Eliminamos todos los usuarios
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .delete("/api/users");

        System.out.println("Delete Response: " + response.getBody().asString());

        // Realiza la petición GET al endpoint para obtener todos los usuarios
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken) // Incluye el token en el encabezado
                .get("/api/users");

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a {int} {string} response with all users details")
    public void iShouldReceiveResponseWithAllUsersDetails(int expectedStatusCode, String expectedStatusMessage) {
        ResponseUtils.verifyStatusCode(response, expectedStatusCode);
        System.out.println("Received " + expectedStatusMessage + " response: " + expectedStatusCode);

    }

    @Then("I should see a users type message {string}")
    public void iShouldSeeUserMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }

    @Then("I should receive a users type {int} {string} response")
    public void iShouldReceiveUserResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
        System.out.println("Received " + statusMessage + " response: " + statusCode);
    }
}
