Feature: User Registration Feature

  Scenario: Successful user registration
    Given I am on the registration page
    When I submit valid random user data
    Then I should receive a 201 "Created" register response
    And I should see a register message "Usuario registrado exitosamente"

  Scenario: User registration fails when required fields are missing
    Given I am on the registration page
    When I submit a form with missing "nombre" and "apellido" and valid random data for "username", "email" and "password"
    Then I should receive a 400 "Bad Request" register response
    And I should see a register message "El campo nombre es requerido, El campo apellido es requerido"

  Scenario: User registration fails when username or email already exists
    Given I am on the registration page
    When I submit a form with an existing email and valid random data for "nombre", "apellido", "username" and "password"
    Then I should receive a 409 "Conflict" register response
    And I should see a register message "El email o username ya existen"

  Scenario: User registration fails when all fields are missing
    Given I am on the registration page
    When I submit an empty form
    Then I should receive a 400 "Bad Request" register response
    And I should see a register message "El campo nombre es requerido, El campo apellido es requerido, El campo username es requerido, El campo email es requerido, El campo password es requerido"
