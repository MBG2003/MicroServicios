Feature: User Registration Feature

  Scenario: Successful user registration
    Given I am on the registration page
    When I submit valid data with "nombre" as "Mateo", "apellido" as "Baez", "username" as "mateobaez", "email" as "mateo.baezg@example.com" and "password" as "contra123"
    Then I should receive a 201 Created response
    And I should see a message "User registered successfully"

  Scenario: User registration fails when required fields are missing
    Given I am on the registration page
    When I submit a form with missing "nombre" and "apellido" and valid "username", "email" and "password"
    Then I should receive a 400 Bad Request response
    And I should see a message "El campo nombre es requerido, El campo apellido es requerido, El campo username es requerido, El campo email es requerido, El campo password es requerido, "

  Scenario: User registration fails when username or email already exists
    Given I am on the registration page
    When I submit a form with an existing "email" as "mateo.baezg@example.com" and valid "nombre", "apellido", "username" and "password"
    Then I should receive a 409 Conflict response
    And I should see a message "Email or username already exists"

  Scenario: User registration fails when all fields are missing
    Given I am on the registration page
    When I submit an empty form
    Then I should receive a 400 Bad Request response
    And I should see a message "El campo nombre es requerido, El campo apellido es requerido, El campo username es requerido, El campo email es requerido, El campo password es requerido, "

  Scenario: Server error during user registration
    Given I am on the registration page
    When I submit valid data and the server fails to process the request
    Then I should receive a 500 Internal Server Error response
    And I should see a message "Server error"
