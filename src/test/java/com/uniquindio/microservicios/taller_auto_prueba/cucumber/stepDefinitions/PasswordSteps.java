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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import static io.restassured.RestAssured.given;

public class PasswordSteps {

    private final String apiUrl = "192.168.1.126";
    private Response response;
    private Faker faker = new Faker();
    private String email;
    private String newPassword;

    @Given("I am on the password recovery page")
    public void iAmOnThePasswordRecoveryPage() {
        System.out.println("Navigating to the password recovery page");
    }

    @Given("I have registered a new user")
    public void iHaveRegisteredANewUser() {

        // Reutilizando la información del usuario registrado
        email = UserManager.getInstance().getEmail();
        if (email == null) {
            // Registrando un nuevo usuario
            UserManager.getInstance().registerNewUser();
            email = UserManager.getInstance().getEmail();
            System.out.println("New email is: " + email);
        }else{
            System.out.println("Old email is: " + email);
        }
    }

    @When("I submit the registered user's email and a new password")
    public void iSubmitRegisteredUserEmailAndNewPassword() {
        RestAssured.baseURI = "http://" + apiUrl + ":3000";
        newPassword = faker.internet().password();

        String payload = String.format("{ \"email\": \"%s\", \"newPassword\": \"%s\" }", email, newPassword);

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/auth/password");
    }

    @When("I submit no email and a new password {string}")
    public void iSubmitNoEmailAndNewPassword(String newPassword) {
        RestAssured.baseURI = "http://" + apiUrl + ":3000";
        String payload = String.format("{ \"newPassword\": \"%s\" }", newPassword);

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/auth/password");
    }

    @When("I submit the registered user's email and no new password")
    public void iSubmitEmailAndNoNewPassword() {
        RestAssured.baseURI = "http://" + apiUrl + ":3000";
        String payload = String.format("{ \"email\": \"%s\" }", email);

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/auth/password");
    }

    @When("I submit an unregistered email and a new password")
    public void iSubmitUnregisteredEmailAndNewPassword() {
        RestAssured.baseURI = "http://" + apiUrl + ":3000";
        String unregisteredEmail = faker.internet().emailAddress();
        String newPassword = faker.internet().password();

        String payload = String.format("{ \"email\": \"%s\", \"newPassword\": \"%s\" }", unregisteredEmail, newPassword);

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/auth/password");
    }

    @Then("I should receive a password type {int} {string} response")
    public void iShouldReceivePasswordResponse(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
        System.out.println("Received " + statusMessage + " response: " + statusCode);
    }

    @Then("I should see a password type message {string}")
    public void iShouldSeePasswordMessage(String expectedMessage) {
        ResponseUtils.verifyMessage(response, expectedMessage);
    }
}
