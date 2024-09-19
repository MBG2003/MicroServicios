package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class RegisterSteps {


    private Response response;

    @Given("I am on the registration page")
    public void iAmOnTheRegistrationPage() {
        // Simulación de la navegación a la página de registro
        System.out.println("Navigating to registration page");
    }

    @When("I submit valid data with {string} as {string}, {string} as {string}, {string} as {string}, {string} as {string} and {string} as {string}")
    public void iSubmitValidData(String field1, String nombre, String field2, String apellido, String field3, String username, String field4, String email, String field5, String password) {
        RestAssured.baseURI = "http://localhost:3000";
        String payload = String.format(
                "{ \"%s\": \"%s\", \"%s\": \"%s\", \"%s\": \"%s\", \"%s\": \"%s\", \"%s\": \"%s\" }",
                field1, nombre, field2, apellido, field3, username, field4, email, field5, password
        );

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/register");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit a form with missing {string} and {string} and valid {string}, {string} and {string}")
    public void iSubmitFormWithMissingFields(String field1, String field2, String field3, String field4, String field5) {
        RestAssured.baseURI = "http://localhost:3000";
        String payload = String.format(
                "{ \"%s\": \"validUser\", \"%s\": \"validEmail@example.com\", \"%s\": \"validPassword\" }",
                field3, field4, field5
        );

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/register");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit a form with an existing {string} as {string} and valid {string}, {string}, {string} and {string}")
    public void iSubmitFormWithExistingEmail(String field1, String email, String field2, String nombre, String field3, String apellido, String field4, String username, String field5, String password) {
        RestAssured.baseURI = "http://localhost:3000";
        String payload = String.format(
                "{ \"%s\": \"%s\", \"%s\": \"%s\", \"%s\": \"%s\", \"%s\": \"%s\", \"%s\": \"%s\" }",
                field1, email, field2, nombre, field3, apellido, field4, username, field5, password
        );

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/register");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit an empty form")
    public void iSubmitAnEmptyForm() {
        RestAssured.baseURI = "http://localhost:3000";

        response = given()
                .header("Content-Type", "application/json")
                .body("{}")
                .post("/api/auth/register");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit valid data and the server fails to process the request")
    public void iSubmitValidDataAndServerFails() {
        RestAssured.baseURI = "http://localhost:3000";
        String payload = "{ \"nombre\": \"Mateo\", \"apellido\": \"Baez\", \"username\": \"mateobaez\", \"email\": \"mateo.baezg@example.com\", \"password\": \"contra123\" }";

        // Simulando un fallo en el servidor
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/fail-register");

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a {int} {string} response")
    public void iShouldReceiveResponse(int statusCode, String statusText) {
        assertEquals(statusCode, response.getStatusCode());
        System.out.println("Received " + statusText + " response: " + statusCode);
    }

    @Then("I should see a message {string}")
    public void iShouldSeeMessage(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        assertEquals(expectedMessage, actualMessage);
        System.out.println("Expected message: " + expectedMessage);
    }
}