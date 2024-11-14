package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.DataManager;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.ResponseUtils;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class LogsSteps {

    private final String apiUrl = "192.168.206.49";
    private Response response;
    private UserManager userManager = UserManager.getInstance();

    @Given("I register a new user for logs")
    public void iRegisterANewUserForLogs() {
        userManager.registerNewUser();  // Uso de UserManager para registrar
    }

    @When("I request to get all logs")
    public void iRequestToGetAllLogs() {
        RestAssured.baseURI = "http://" + apiUrl + ":3001";

        response = given()
                .header("Content-Type", "application/json")
                .param("query", Map.of("job", "docker-logs", "filename", "/var/lib/docker/containers/85ef1fc7f62b294cf82718abd8f7f497c2a87bf1ea8143e6a08d143a37ad4e17/85ef1fc7f62b294cf82718abd8f7f497c2a87bf1ea8143e6a08d143a37ad4e17-json.log"))
                .get("/loki/api/v1/query_range");
    }

    @Then("I should receive a {int} {string} response with all logs details")
    public void iShouldReceiveResponseWithAllLogs(int statusCode, String statusMessage) {
        ResponseUtils.verifyStatusCode(response, statusCode);
    }
}
