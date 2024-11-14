package com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils.ResponseUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ProfileSteps {

    private static final String BASE_URL = "http://192.168.206.49:8082";
    private Response response;
    private UserProfile createdProfile;
    private Faker faker = new Faker();

    // General user data for all scenarios
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String password;

    // Estructura UserProfile para manejar datos
    public static class UserProfile {
        public String user_id;
        public String personal_url;
        public String nickname;
        public boolean contact_public;
        public String address;
        public String biography;
        public String organization;
        public String country;
        public List<String> social_links;
    }

    @Given("I have a new user profile")
    public void iHaveANewUserProfileWithTheFollowingDetails() {

        // Generate random data
        nombre = faker.name().firstName();
        apellido = faker.name().lastName();
        username = faker.name().username();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
    }

    @When("I send a request to create the user profile")
    public void iSendARequestToCreateTheUserProfile() {

        String payload = String.format(
                "{ \"nombre\": \"%s\", \"apellido\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\" }",
                nombre, apellido, username, email, password
        );

        response = RestAssured
                .given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post(BASE_URL + "/auth-api/api/auth/");

        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("the profile should be created successfully")
    public void theProfileShouldBeCreatedSuccessfully() {
        assertEquals(201, response.getStatusCode());
    }

    @Then("I should receive a confirmation with UserID {string}")
    public void iShouldReceiveAConfirmationWithUserID(String expectedUserID) {
        String actualMessage = response.jsonPath().getString("message");
        System.out.println("Received message: " + actualMessage);
        assertEquals(expectedUserID, actualMessage);
    }

    @Given("a user profile exists with UserID {string}")
    public void aUserProfileExistsWithUserID(String userID) {
        createdProfile = new UserProfile();
        createdProfile.user_id = userID;
        response = RestAssured
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3MzYwZDQ3YzhhNTA1Mjc4YTNiNTgyNSIsImlhdCI6MTczMTU5NTY2OSwiZXhwIjoxNzMxNTk5MjY5fQ.LuLJEw4iTDCXM4LzV7cAkmOQqZBbSwHp40QOL-vJhEo")
                .get(BASE_URL + "/profile-api/profile/" + userID);
    }

    @When("I request the user profile with UserID {string}")
    public void iRequestTheUserProfileWithUserID(String userID) {
        response = RestAssured
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3MzYwZDQ3YzhhNTA1Mjc4YTNiNTgyNSIsImlhdCI6MTczMTU5NTY2OSwiZXhwIjoxNzMxNTk5MjY5fQ.LuLJEw4iTDCXM4LzV7cAkmOQqZBbSwHp40QOL-vJhEo")
                .accept("application/json")
                .when()
                .get(BASE_URL + "/profile-api/profile/" + userID);
        System.out.println("Response body: " + response.getBody().asString());

        //createdProfile = response.as(UserProfile.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserProfile deserializedProfile = mapper.readValue(response.getBody().asString(), UserProfile.class);
            System.out.println("User ID: " + deserializedProfile.personal_url);  // Verificar que se haya deserializado correctamente
            createdProfile.user_id = deserializedProfile.user_id;
            createdProfile.personal_url = deserializedProfile.personal_url;
            createdProfile.nickname = deserializedProfile.nickname;
            createdProfile.contact_public = deserializedProfile.contact_public;
            createdProfile.address = deserializedProfile.address;
            createdProfile.biography = deserializedProfile.biography;
            createdProfile.organization = deserializedProfile.organization;
            createdProfile.country = deserializedProfile.country;
            createdProfile.social_links = deserializedProfile.social_links;

        } catch (Exception e) {
            e.printStackTrace();  // Manejar cualquier excepción de deserialización
        }
    }

    @Then("I should receive the user profile with the following details:")
    public void iShouldReceiveTheUserProfileWithTheFollowingDetails(DataTable dataTable) {

        // Convierte la
        // DataTable en un mapa
        Map<String, String> expectedProfileData = dataTable.asMaps().get(0);


        assertEquals(expectedProfileData.get("personal_url"), createdProfile.personal_url);
        assertEquals(expectedProfileData.get("Nickname"), createdProfile.nickname);
        assertEquals(Boolean.parseBoolean(expectedProfileData.get("ContactPublic")), createdProfile.contact_public);
        assertEquals(expectedProfileData.get("Address"), createdProfile.address);
        assertEquals(expectedProfileData.get("Biography"), createdProfile.biography);
        assertEquals(expectedProfileData.get("Organization"), createdProfile.organization);
        assertEquals(expectedProfileData.get("Country"), createdProfile.country);
        assertEquals(List.of(expectedProfileData.get("SocialLinks").split(",")), createdProfile.social_links);
    }

    @When("I update the biography of the user profile with UserID {string} to {string}")
    public void iUpdateTheBiographyOfTheUserProfileWithUserIDTo(String userID, String newBiography) {
        createdProfile.biography = newBiography;
        response = RestAssured
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3MzYwZDQ3YzhhNTA1Mjc4YTNiNTgyNSIsImlhdCI6MTczMTU5NTY2OSwiZXhwIjoxNzMxNTk5MjY5fQ.LuLJEw4iTDCXM4LzV7cAkmOQqZBbSwHp40QOL-vJhEo")
                .contentType("application/json")
                .body(Map.of("biography", newBiography))
                .when()
                .put(BASE_URL + "/profile-api/profile/" + userID);
    }

    @Then("the profile should be updated successfully")
    public void theProfileShouldBeUpdatedSuccessfully() {
        assertEquals(200, response.getStatusCode());  // Asegurarse de que el código de estado es 200 para éxito
    }

    @Then("when I request the user profile with UserID {string}")
    public void whenIRequestTheUserProfileWithUserID(String userID) {
        response = RestAssured
                .given()
                .when()
                .get(BASE_URL + "/profile-api/profile/" + userID);
        //createdProfile = response.as(UserProfile.class);  // Asigna el perfil actualizado para su verificación
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserProfile deserializedProfile = mapper.readValue(response.getBody().asString(), UserProfile.class);
            System.out.println("User ID: " + deserializedProfile.personal_url);  // Verificar que se haya deserializado correctamente
            createdProfile.user_id = deserializedProfile.user_id;
            createdProfile.personal_url = deserializedProfile.personal_url;
            createdProfile.nickname = deserializedProfile.nickname;
            createdProfile.contact_public = deserializedProfile.contact_public;
            createdProfile.address = deserializedProfile.address;
            createdProfile.biography = deserializedProfile.biography;
            createdProfile.organization = deserializedProfile.organization;
            createdProfile.country = deserializedProfile.country;
            createdProfile.social_links = deserializedProfile.social_links;

        } catch (Exception e) {
            e.printStackTrace();  // Manejar cualquier excepción de deserialización
        }
    }

    @Then("the biography should be {string}")
    public void theBiographyShouldBe(String expectedBiography) {
        assertEquals(expectedBiography, createdProfile.biography);
    }
}