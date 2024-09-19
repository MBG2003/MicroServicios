package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class PasswordSteps {

    private Response response;

    @Given("I am on the password recovery page")
    public void iAmOnThePasswordRecoveryPage() {
        // Simulación de la navegación a la página de recuperación de contraseñas
        System.out.println("Navigating to the password recovery page");
    }

    @When("I submit a valid email {string} and a new password {string}")
    public void iSubmitValidEmailAndNewPassword(String email, String newPassword) {
        RestAssured.baseURI = "http://localhost:3000";

        // Crea el payload con el email y la nueva contraseña
        String payload = String.format("{ \"email\": \"%s\", \"newPassword\": \"%s\" }", email, newPassword);

        // Realiza la petición POST al endpoint de recuperación de contraseña
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/recover-password");

        // Imprime la respuesta en consola para fines de depuración
        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit no email and a new password {string}")
    public void iSubmitNoEmailAndNewPassword(String newPassword) {
        RestAssured.baseURI = "http://localhost:3000";

        // Crea el payload sin el email
        String payload = String.format("{ \"newPassword\": \"%s\" }", newPassword);

        // Realiza la petición POST
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/recover-password");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("I submit an email {string} and no new password")
    public void iSubmitEmailAndNoNewPassword(String email) {
        RestAssured.baseURI = "http://localhost:3000";

        // Crea el payload sin la nueva contraseña
        String payload = String.format("{ \"email\": \"%s\" }", email);

        // Realiza la petición POST
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth/recover-password");

        System.out.println("Response: " + response.getBody().asString());
    }

    @When("the server fails to process the request")
    public void theServerFailsToProcessTheRequest() {
        // Este método debe simular el fallo del servidor en un escenario de prueba.
        // Generalmente esto se configura en el entorno de prueba o mediante un mock.
        System.out.println("Simulating server failure");
    }

    @Then("I should receive a {int} OK response")
    public void iShouldReceiveA200OKResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 200 (OK)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica si la respuesta contiene un mensaje de éxito
        String successMessage = response.jsonPath().getString("message");
        System.out.println("Success message: " + successMessage);
    }

    @Then("I should receive a {int} Bad Request response")
    public void iShouldReceiveABadRequestResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 400 (Bad Request)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica el mensaje de error en la respuesta
        String errorMessage = response.jsonPath().getString("message");
        System.out.println("Error message: " + errorMessage);
    }

    @Then("I should receive a {int} Internal Server Error response")
    public void iShouldReceiveAnInternalServerErrorResponse(int statusCode) {
        // Verifica que la respuesta tenga el código 500 (Internal Server Error)
        assertEquals(statusCode, response.getStatusCode());

        // Opcional: Verifica el mensaje de error en la respuesta
        String errorMessage = response.jsonPath().getString("message");
        System.out.println("Error message: " + errorMessage);
    }

    @Then("the password should be successfully updated")
    public void thePasswordShouldBeSuccessfullyUpdated() {
        // Aquí se puede agregar una lógica para verificar que la contraseña se haya actualizado.
        // Esto puede incluir consultas adicionales o validaciones basadas en el contexto de la aplicación.
        System.out.println("Password should be successfully updated");
    }
}


