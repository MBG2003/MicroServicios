package com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserManager {
    private static UserManager instance = null;
    private Faker faker = new Faker();

    // Variables de usuario (ahora se inicializan y actualizan en el método registerNewUser)
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String password;

    private Response response;

    // Constructor privado para el patrón Singleton
    private UserManager() {}

    // Método para obtener la instancia única
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Métodos para obtener la información del usuario
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

    // Método para registrar un nuevo usuario
    public void registerNewUser() {
        // Actualiza los datos del usuario con cada llamada
        nombre = faker.name().firstName();
        apellido = faker.name().lastName();
        username = faker.name().username();
        email = faker.internet().emailAddress();
        password = faker.internet().password();

        // Define la base URI de la API
        RestAssured.baseURI = "http://localhost:3000";

        // Cuerpo del payload para registrar al usuario
        String payload = String.format(
                "{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                nombre, apellido, username, email, password
        );

        // Envía la solicitud POST para registrar al usuario
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/auth");

        // Muestra la respuesta
        System.out.println("[UserManager] Response: " + response.getBody().asString());

        // Almacena el userId si la respuesta es exitosa
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

        // Manejo de la respuesta del login
        if (loginResponse.getStatusCode() == 200) {
            String token = loginResponse.jsonPath().getString("token");
            DataManager.getInstance().setAuthToken("currentToken", token); // Guardar token
            System.out.println("[UserManager] Login successful, token: " + token);
            return token; // Devolver el token
        } else {
            System.out.println("[UserManager] Login failed: " + loginResponse.getStatusCode());
            return null; // Manejar error si es necesario
        }
    }
}
