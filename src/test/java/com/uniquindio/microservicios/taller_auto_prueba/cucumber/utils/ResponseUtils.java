package com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils;

import io.restassured.response.Response;
import static org.junit.Assert.assertEquals;

public class ResponseUtils {

    // Método para verificar el código de estado y el mensaje
    public static void verifyStatusCode(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // Método para verificar el mensaje en la respuesta
    public static void verifyMessage(Response response, String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        assertEquals(expectedMessage, actualMessage);
    }

    public static void verifyMessage(Response response, String expectedId, String actualId) {
        assertEquals(expectedId, actualId);
    }
}
