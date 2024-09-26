package com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils;

import io.restassured.response.Response;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Assert;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class ResponseUtils {

    public static void verifyStatusCode(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    public static void verifyMessage(Response response, String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        assertEquals(expectedMessage, actualMessage);
    }

    public static void verifyMessage(Response response, String expectedId, String actualId) {
        assertEquals(expectedId, actualId);
    }

    public static void validateResponseAgainstSchema(String jsonResponse, String schemaFileName) throws JSONException {
        // Cargar el archivo JSON Schema
        InputStream schemaStream = ResponseUtils.class.getClassLoader().getResourceAsStream(schemaFileName);
        if (schemaStream == null) {
            throw new IllegalArgumentException("No se pudo encontrar el archivo de esquema: " + schemaFileName);
        }
        JSONObject schemaObject = new JSONObject(new JSONTokener(schemaStream.toString()));

        // Cargar el esquema
        Schema schema = SchemaLoader.load(schemaObject);

        // Convertir la respuesta en JSONObject
        JSONObject jsonResponseObject = new JSONObject(jsonResponse);

        // Validar la respuesta contra el esquema
        try {
            schema.validate(jsonResponseObject); // Si no hay excepciones, es válido
            System.out.println("La respuesta es válida según el esquema.");
        } catch (Exception e) {
            Assert.fail("La respuesta no es válida según el esquema: " + e.getMessage());
        }
    }
}
