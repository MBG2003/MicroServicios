package com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserManager {
    private static UserManager instance = null;
    private Faker faker = new Faker();

    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String password;

    private Response response;

    private UserManager() {}

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void registerNewUser() {
        // Actualiza los datos del usuario con cada llamada
        nombre = faker.name().firstName();
        apellido = faker.name().lastName();
        username = faker.name().username();
        email = faker.internet().emailAddress();
        password = faker.internet().password();

        // Define la base URI de la API
        RestAssured.baseURI = "http://192.168.199.138:3000";

        String payload = String.format(
                "{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                nombre, apellido, username, email, password
        );

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth");

        System.out.println("[UserManager] Response: " + response.getBody().asString());

        if (response.getStatusCode() == 201) {
            String userId = response.jsonPath().getString("userId");
            DataManager.getInstance().setUserId("currentUser", userId);
            System.out.println("[UserManager] Successfully Registered userId: " + userId);
        } else {
            System.out.println("[UserManager] Error al registrar usuario: " + response.getStatusCode());
        }
    }

    // Método para iniciar sesión y obtener el token
    public String loginAndGetToken() {
        String email = getEmail();
        String password = getPassword();

        Response loginResponse = given()
                .header("Content-Type", "application/json")
                .body(String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password))
                .post("/api/auth/login");

        if (loginResponse.getStatusCode() == 200) {
            String token = loginResponse.jsonPath().getString("token");
            DataManager.getInstance().setAuthToken("currentToken", token);
            System.out.println("[UserManager] Login successful, token: " + token);
            return token;
        } else {
            System.out.println("[UserManager] Login failed: " + loginResponse.getStatusCode());
            return null;
        }
    }
}
