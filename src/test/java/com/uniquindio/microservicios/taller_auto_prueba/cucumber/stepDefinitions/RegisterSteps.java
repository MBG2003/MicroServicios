package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import com.github.javafaker.Faker;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.ResponseUtils;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class RegisterSteps {

    private Response response;
    private Faker faker = new Faker();

    // General user data for all scenarios
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String password;

    @Given("I am on the registration page")
    public void iAmOnTheRegistrationPage() {
        System.out.println("Navigating to registration page");
        RestAssured.baseURI = "http://192.168.206.249:3000";

        // Generate random data
        nombre = faker.name().firstName();
        apellido = faker.name().lastName();
        username = faker.name().username();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
    }

    @When("I submit valid random user data")
    public void iSubmitValidRandomUserData() {
        String payload = String.format(
                "{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                nombre, apellido, username, email, password
        );

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit a form with missing {string} and {string} and valid random data for {string}, {string} and {string}")
    public void iSubmitFormWithMissingFields(String missingField1, String missingField2, String validField1, String validField2, String validField3) {
        // Only set valid fields
        String payload = String.format(
                "{ \"%s\": \"%s\", \"%s\": \"%s\", \"%s\": \"%s\" }",
                validField1, username, validField2, email, validField3, password
        );

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit a form with an existing email and valid random data for {string}, {string}, {string} and {string}")
    public void iSubmitFormWithExistingEmail(String field1, String field2, String field3, String field4) {
        // Register a new user to get an existing email
        UserManager.getInstance().registerNewUser();

        //get the email from the registered user
        String existingEmail = UserManager.getInstance().getEmail();

        String payload = String.format(
                "{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                nombre, apellido, username, existingEmail, password
        );

        System.out.println("existing email Payload: " + payload);

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit an empty form")
    public void iSubmitAnEmptyForm() {
        String payload = "{}";

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth");

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should receive a {int} {string} register response")
    public void iShouldReceiveAResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
    }

    @Then("I should see a register message {string}")
    public void iShouldSeeAMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }
}
