package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import com.github.javafaker.Faker;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.UserManager;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.DataManager;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.ResponseUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginSteps {

    private Response response;
    private UserManager userManager = UserManager.getInstance();

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        System.out.println("Navigating to login page");
    }

    @Given("I register a new user")
    public void iRegisterANewUser() {
        userManager.registerNewUser();  // Uso de UserManager para registrar
    }

    @When("I login with the registered user's email and password")
    public void iLoginWithRegisteredUserCredentials() {
        RestAssured.baseURI = "http://192.168.199.138:3000";

        String payload = String.format("{ \"email\": \"%s\", \"password\": \"%s\" }",
                userManager.getEmail(), userManager.getPassword());

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/login");

        String authToken = response.jsonPath().getString("token");
        DataManager.getInstance().setAuthToken("currentUser", authToken);
        System.out.println("Auth Token: " + authToken);
    }

    @When("I try to login without providing the email and with the registered user's password")
    public void iTryToLoginWithoutProvidingTheEmail() {
        RestAssured.baseURI = "http://192.168.199.138:3000";

        String payload = String.format("{ \"password\": \"%s\" }", userManager.getPassword());

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/login");
    }

    @When("I try to login with the registered user's email and without providing the password")
    public void iTryToLoginWithoutProvidingThePassword() {
        RestAssured.baseURI = "http://192.168.201.200:3000";

        String payload = String.format("{ \"email\": \"%s\" }", userManager.getEmail());

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/login");
    }

    @When("I try to login with an invalid email or password")
    public void iTryToLoginWithInvalidCredentials() {
        RestAssured.baseURI = "http://192.168.199.138:3000";
        Faker faker = new Faker();

        String invalidEmail = faker.internet().emailAddress();
        String invalidPassword = faker.internet().password();

        String payload = String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", invalidEmail, invalidPassword);

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/login");
    }

    @Then("I should see the dashboard")
    public void iShouldSeeTheDashboard() {
        ResponseUtils.verifyStatusCode(response, 200);
        System.out.println("Navigating to the dashboard");
    }

    @Then("I should receive a {int} {string} login response")
    public void iShouldReceiveAResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
    }

    @Then("I should see a login message {string}")
    public void iShouldSeeAMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }
}
