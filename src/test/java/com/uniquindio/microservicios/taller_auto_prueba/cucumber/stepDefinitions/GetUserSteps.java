package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.DataManager;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.UserManager;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.ResponseUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.given;

public class GetUserSteps {

    private final String apiUrl = "192.168.204.207";
    private Response response;
    private String authToken;
    private String authenticatedUserId;

    @Given("I am authenticated with a valid token for user")
    public void iAmAuthenticatedWithValidToken() {
        // Registrar nuevo usuario si es necesario
        UserManager userManager = UserManager.getInstance();
        userManager.registerNewUser();

        // Realiza el login para obtener el token de autenticación
        authToken = userManager.loginAndGetToken();
        authenticatedUserId = DataManager.getInstance().getUserId("currentUser");

        DataManager.getInstance().setAuthToken("currentToken", authToken);
        System.out.println("[UserStep] Authenticated user with ID: " + authenticatedUserId + " and token: " + authToken);
    }

    @When("I request to get my user by ID")
    public void iRequestToGetMyUserById() {
        RestAssured.baseURI = "http://" + apiUrl + ":3000";

        // Realiza la petición GET para obtener el usuario por ID
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .get("/api/users/" + authenticatedUserId);

        System.out.println("[UserStep] Response: " + response.getBody().asString());
    }

    @When("I request to get another user's ID")
    public void iRequestToGetAnotherUserId() {
        String anotherUserId = "invalidUserId12345";
        RestAssured.baseURI = "http://" + apiUrl + ":3000";

        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .get("/api/users/" + anotherUserId);

        System.out.println("[UserStep] Response: " + response.getBody().asString());
    }

    @When("I request to get user by a non-existing ID")
    public void iRequestToGetNonExistingUserById() {
        RestAssured.baseURI = "http://" + apiUrl + ":3000";

        String nonExistingUserId = authenticatedUserId;

        //eliminamos el user para que no se encuentre en la base de datos
       response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .delete("/api/users/" + nonExistingUserId);

        // Realiza la petición GET para obtener el usuario por ID
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .get("/api/users/" + nonExistingUserId);

        System.out.println("[UserStep] Response: " + response.getBody().asString());
    }

    @Then("I should receive a user type {int} {string} response")
    public void iShouldReceiveUserResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
        System.out.println("Received " + statusMessage + " response: " + statusCode);
    }

    @Then("I should see a user type message {string}")
    public void iShouldSeeUserMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }

    @Then("I should receive a {int} {string} response with the user details")
    public void iShouldReceiveResponseWithUserDetails(int expectedStatusCode, String expectedStatusMessage) {
        ResponseUtils.verifyStatusCode(response, expectedStatusCode);
        System.out.println("Received " + expectedStatusMessage + " response: " + expectedStatusCode);
        String userId = response.jsonPath().getString("_id");
        ResponseUtils.verifyMessage(response, authenticatedUserId, userId);
    }

}
