package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import com.github.javafaker.Faker;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.DataManager;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.ResponseUtils;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class DeleteSteps {
    private Faker faker = new Faker();

    private Response response;
    private String authToken;
    private String authenticatedUserId;

    //    Given I am authenticated with a valid token for delete
    @Given("I am authenticated with a valid token for delete")
    public void iAmAuthenticatedWithValidTokenForUpdate() {
        // Registrar un nuevo usuario si es necesario y obtener su ID
        UserManager userManager = UserManager.getInstance();
        userManager.registerNewUser();

        // Realiza el login para obtener el token de autenticación
        authToken = userManager.loginAndGetToken();
        authenticatedUserId = DataManager.getInstance().getUserId("currentUser");

        DataManager.getInstance().setAuthToken("currentToken", authToken);
        System.out.println("[UpdateSteps] Authenticated user with ID: " + authenticatedUserId + " and token: " + authToken);
    }

    @When("I request to delete my user")
    public void iRequestToUpdateTheUserWithValidData() {
        RestAssured.baseURI = "http://localhost:3000";

        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .delete("/api/users/" + authenticatedUserId);

        System.out.println("[DeleteSteps] Response: " + response.getBody().asString());
    }

    // WhenI request to delete another user
    @When("I request to delete another user")
    public void iRequestToUpdateAnotherUserWithValidData() {
        RestAssured.baseURI = "http://localhost:3000";
        UserManager userManager = UserManager.getInstance();
        userManager.registerNewUser();
        String anotherUserId = DataManager.getInstance().getUserId("currentUser");

        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .delete("/api/users/" + anotherUserId);

        System.out.println("[DeleteSteps] Response: " + response.getBody().asString());
    }

    @Then("I should receive an delete type {int} {string} response")
    public void iShouldReceiveUserResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
        System.out.println("Received " + statusMessage + " response: " + statusCode);
    }

    @Then("I should see an delete type message {string}")
    public void iShouldSeeUserMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }

}
