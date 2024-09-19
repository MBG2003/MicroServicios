package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class LoginSteps {

    private Response response;

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        // Simulación de la navegación a la página de login
        System.out.println("Navigating to login page");
    }

    @When("I enter valid credentials with email {string} and password {string}")
    public void iEnterValidCredentials(String email, String password) {
        // Configuración de la URL base para RestAssured
        RestAssured.baseURI = "http://localhost:3000";

        // Crea el payload con el email y password recibidos desde el archivo .feature
        String payload = String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password);

        // Realiza la petición POST al endpoint de login con el payload
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/login");

        // Imprime la respuesta en consola para fines de depuración
        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I try to login without providing the email and with password {string}")
    public void iTryToLoginWithoutProvidingTheEmail(String password) {
        RestAssured.baseURI = "http://localhost:3000";

        // Crea el payload sin el email
        String payload = String.format("{ \"password\": \"%s\" }", password);

        // Realiza la petición POST
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/login");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I try to login with email {string} and without providing the password")
    public void iTryToLoginWithoutProvidingThePassword(String email) {
        RestAssured.baseURI = "http://localhost:3000";

        // Crea el payload sin el password
        String payload = String.format("{ \"email\": \"%s\" }", email);

        // Realiza la petición POST
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/login");

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("I should see the dashboard")
    public void iShouldSeeTheDashboard() {
        // Verifica que la respuesta tenga el código 200 (OK)
        assertEquals(200, response.getStatusCode());

        // Opcional: Verifica el contenido de la respuesta (por ejemplo, un token de autenticación)
        String token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);

        // Simulación de la redirección al dashboard
        System.out.println("Navigating to the dashboard");
    }

    @Then("I should receive a {int} Bad Request response")
    public void iShouldReceiveBadRequest(int statusCode) {
        // Verifica que la respuesta sea un 400 Bad Request
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica si hay algún mensaje de error en la respuesta
        String errorMessage = response.jsonPath().getString("message");
        System.out.println("Error message: " + errorMessage);
    }
}