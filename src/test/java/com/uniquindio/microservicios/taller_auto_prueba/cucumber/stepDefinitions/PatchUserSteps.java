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

public class PatchUserSteps {

    private Response response;
    private String authToken;
    private String authenticatedUserId;

    @Given("I am authenticated with a valid token for patch")
    public void iAmAuthenticatedWithValidTokenForPatch() {
        // Registrar un nuevo usuario si es necesario y obtener su ID
        UserManager userManager = UserManager.getInstance();
        userManager.registerNewUser();

        System.out.println("[PatchSteps] Original name:" +userManager.getNombre());

        // Realiza el login para obtener el token de autenticación
        authToken = userManager.loginAndGetToken();
        authenticatedUserId = DataManager.getInstance().getUserId("currentUser");

        DataManager.getInstance().setAuthToken("currentToken", authToken);
        System.out.println("[PatchSteps] Authenticated user with ID: " + authenticatedUserId + " and token: " + authToken);
    }

    @When("I request to patch my user with valid data")
    public void iRequestToPartiallyUpdateMyUserWithValidData() {
        RestAssured.baseURI = "http://192.168.199.138:3000";

        Faker faker = new Faker();
        String payload = String.format("{ \"nombre\": \"%s\" }", faker.name().firstName());

        System.out.println("[PatchSteps] Patch Name: " + payload);

        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .patch("/api/users/" + authenticatedUserId);

        System.out.println("[PatchSteps] Response: " + response.getBody().asString());
    }

    @When("I request to patch my user with an empty request body")
    public void iRequestToPartiallyUpdateMyUserWithAnEmptyRequestBody() {
        RestAssured.baseURI = "http://192.168.199.138:3000";
        String payload = "{}";

        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .patch("/api/users/" + authenticatedUserId);

        System.out.println("[PatchSteps] Response: " + response.getBody().asString());
    }

    @When("I request to patch another user with valid data")
    public void iRequestToPartiallyUpdateAnotherUserWithValidData() {
        RestAssured.baseURI = "http://192.168.199.138:3000";
        Faker faker = new Faker();
        String payload = String.format("{ \"nombre\": \"%s\" }", faker.name().firstName());

        //generamos un nuevo usuario y obtenemos su id
        UserManager userManager = UserManager.getInstance();
        userManager.registerNewUser();
        String anotherUserId = DataManager.getInstance().getUserId("currentUser");

        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .patch("/api/users/" + anotherUserId);

        System.out.println("[PatchSteps] Response: " + response.getBody().asString());
    }

    //    When I request to patch my user but my user is not found
    @When("I request to patch my user but my user is not found")
    public void iRequestToPartiallyUpdateMyUserButMyUserIsNotFound() {
        RestAssured.baseURI = "http://192.168.199.138:3000";
        Faker faker = new Faker();
        String payload = String.format("{ \"nombre\": \"%s\" }", faker.name().firstName());

        //eliminamos el usuario para que no se encuentre en bd
        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .delete("/api/users/" + authenticatedUserId);

        //patch al usuario eliminado
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .patch("/api/users/" + authenticatedUserId);

        System.out.println("[PatchSteps] Response: " + response.getBody().asString());
    }

    @When("I request to patch my user with an invalid ID format")
    public void iRequestToPartiallyUpdateMyUserWithAnInvalidIDFormat() {
        RestAssured.baseURI = "http://192.168.199.138:3000";

        String invalidUserId = "12345";

        String payload = "{ \"nombre\": \"InvalidIDPartialUpdate\" }";

        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .patch("/api/users/" + invalidUserId);

        System.out.println("[PatchSteps] Response: " + response.getBody().asString());
    }

    @Then("I should receive a {int} {string} response with the patch user details")
    public void iShouldReceiveResponseWithUpdatedUserDetails(int statusCode, String statusMessage) {
        assertEquals(statusCode, response.getStatusCode());
        System.out.println("Received " + statusMessage + " response: " + statusCode);
        String updatedName = response.jsonPath().getString("nombre");
        System.out.println("[PatchSteps] Partially updated. ");
    }

    @Then("I should receive a patch type {int} {string} response")
    public void iShouldReceivePatchTypeResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
        System.out.println("[PatchSteps]  Received " + statusMessage + " response: " + statusCode);
    }

    @Then("I should see a patch type message {string}")
    public void iShouldSeePatchTypeMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }
}
