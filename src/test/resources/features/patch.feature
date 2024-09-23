Feature: Partial update user by ID

  Scenario: Successfully patch user by ID
    Given I am authenticated with a valid token for patch
    When I request to patch my user with valid data
    Then I should receive a 200 "OK" response with the patch user details

  Scenario: Unauthorized to patch another user by ID
    Given I am authenticated with a valid token for patch
    When I request to patch another user with valid data
    Then I should receive a patch type 403 "Forbidden" response
    And I should see a patch type message "No autorizado para actualizar esta información"

  Scenario: Patch user by ID with an empty request body
    Given I am authenticated with a valid token for patch
    When I request to patch my user with an empty request body
    Then I should receive a patch type 400 "Bad Request" response
    And I should see a patch type message "El cuerpo de la solicitud no puede estar vacío"

  Scenario: User ID not found during patch
    Given I am authenticated with a valid token for patch
    When I request to patch my user but my user is not found
    Then I should receive a patch type 404 "Not Found" response
    And I should see a patch type message "Usuario no encontrado"
