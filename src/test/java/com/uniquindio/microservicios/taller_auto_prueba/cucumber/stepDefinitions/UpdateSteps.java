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

public class UpdateSteps {
    private Faker faker = new Faker();

    //datos a actualizar de ejemplo
    private String name = faker.name().firstName();
    private String apellido = faker.name().lastName();
    private String username = faker.name().username();
    private String email = faker.internet().emailAddress();
    private String password = faker.internet().password();

    private Response response;
    private String authToken;
    private String authenticatedUserId;

    @Given("I am authenticated with a valid token for update")
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

    @When("I request to update my user with valid data")
    public void iRequestToUpdateTheUserWithValidData() {
        RestAssured.baseURI = "http://localhost:3000";
        String payload = String.format("{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                name, apellido, username, email, password);

        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/users/" + authenticatedUserId);

        System.out.println("[UpdateSteps] Response: " + response.getBody().asString());
    }

    // When I request to update another user with valid data
    @When("I request to update another user with valid data")
    public void iRequestToUpdateAnotherUserWithValidData() {
        RestAssured.baseURI = "http://localhost:3000";
        String payload = String.format("{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                name, apellido, username, email, password);

        //generamos un nuevo usuario y obtenemos su id
        UserManager userManager = UserManager.getInstance();
        userManager.registerNewUser();
        String anotherUserId = DataManager.getInstance().getUserId("currentUser");

        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/users/" + anotherUserId);

        System.out.println("[UpdateSteps] Response: " + response.getBody().asString());
    }

    // When I request to update my user with an empty request body
    @When("I request to update my user with an empty request body")
    public void iRequestToUpdateMyUserWithAnEmptyRequestBody() {
        RestAssured.baseURI = "http://localhost:3000";
        String payload = "{}";

        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/users/" + authenticatedUserId);

        System.out.println("[UpdateSteps] Response: " + response.getBody().asString());
    }

    //    When I request to update my user with valid data but my user is not found
    @When("I request to update my user with valid data but my user is not found")
    public void iRequestToUpdateMyUserWithValidDataButMyUserIsNotFound() {
        RestAssured.baseURI = "http://192.168.199.138:3000";
        String payload = String.format("{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                name, apellido, username, email, password);

        //eliminamos el user para que no se encuentre en la base de datos
        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .delete("/api/users/" + authenticatedUserId);

        // Realiza la petición PUT para actualizar el usuario que fue eliminado
        response = given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/users/" + authenticatedUserId);

        System.out.println("[UpdateSteps] Response: " + response.getBody().asString());
    }

    @Then("I should receive an update type {int} {string} response")
    public void iShouldReceiveUserResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
        System.out.println("Received " + statusMessage + " response: " + statusCode);
    }

    @Then("I should see an update type message {string}")
    public void iShouldSeeUserMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }

    @Then("I should receive a {int} {string} response with the updated user details")
    public void iShouldReceiveResponseWithUserDetails(int expectedStatusCode, String expectedStatusMessage) {
        ResponseUtils.verifyStatusCode(response, expectedStatusCode);
        System.out.println("Received " + expectedStatusMessage + " response: " + expectedStatusCode);
        String userId = response.jsonPath().getString("_id");
        ResponseUtils.verifyMessage(response, authenticatedUserId, userId);
    }

    @Then("^la respuesta debe cumplir con el esquema \"([^\"]*)\"$")
    public void validateApiResponse(String schemaFileName) {
        try {
            ResponseUtils.validateResponseAgainstSchema(response.getBody().asString(), schemaFileName);
        } catch (Exception e) {
            System.out.println("Error al validar la respuesta contra el esquema: " + e.getMessage());
        }
    }
}
