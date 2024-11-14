package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;
// NotificationsSteps.java

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class NotificationsSteps {

    private final String apiUrl = "192.168.206.49";
    private Response response;

    @Given("que el microservicio de notificaciones está disponible en {string}")
    public void setMicroserviceUrl(String url) {
        // Método opcional: puede ser usado para configurar la URL del microservicio
        RestAssured.baseURI = "http://" + url;
        response = given()
                .header("Content-Type", "application/json")
                .get();
    }

    @Given("que el microservicio de monitoreo está disponible en {string}")
    public void setMonitoreoUrl(String url) {
        // Método opcional: puede ser usado para configurar la URL del microservicio
        RestAssured.baseURI = "http://" + url;
        response = given()
                .header("Content-Type", "application/json")
                .get();
    }

    @Given("que existe una alerta generada")
    public void alertIsGenerated() {
        // Genera datos de alerta simulados que se enviarán en el test

    }

    @When("envío una notificación")
    public void sendNotification() {
        RestAssured.baseURI = "http://" + apiUrl + ":5000";
        String jsonBody = "{ \"alerts\": [{ \"annotations\": { \"summary\": \"Test summary\", \"description\": \"Test description\" } }] }";
        response = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .post("/alert");
    }

    @Then("la respuesta debe ser {string}")
    public void verifyResponseStatus(String status) {
        Assert.assertEquals(Integer.parseInt(status.split(" ")[0]), response.getStatusCode());
    }

    @When("realizo una solicitud GET a {string}")
    public void sendGetRequest(String endpoint) {
        RestAssured.baseURI = "http://" + apiUrl + ":5000";
        response = RestAssured.given().when().get(endpoint);
    }

    @When("realizo una solicitud GET a prometheus en {string}")
    public void sendPrometheusGetRequest(String endpoint) {
        RestAssured.baseURI = "http://" + apiUrl + ":9090";
        response = RestAssured.given().when().get(endpoint);
    }

    @Then("el cuerpo de la respuesta debe contener una lista de notificaciones")
    public void verifyResponseBodyContainsNotificationsList() {
        Assert.assertTrue(response.jsonPath().getList("notifications").size() > 0);
    }

    @Then("el cuerpo de la respuesta debe contener la notificación con ID {string}")
    public void verifyResponseContainsNotificationById(String id) {
        Assert.assertEquals(Integer.parseInt(id), response.jsonPath().getInt("id"));
    }
}