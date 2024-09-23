Feature: Update user by ID

  Scenario: Successfully update user by ID
    Given I am authenticated with a valid token for update
    When I request to update my user with valid data
    Then I should receive a 200 "OK" response with the updated user details

  Scenario: Unauthorized to update another user by ID
    Given I am authenticated with a valid token for update
    When I request to update another user with valid data
    Then I should receive an update type 403 "Forbidden" response
    And I should see an update type message "No autorizado para actualizar esta información"

  Scenario: Update user by ID with an empty request body
    Given I am authenticated with a valid token for update
    When I request to update my user with an empty request body
    Then I should receive an update type 400 "Bad Request" response
    And I should see an update type message "El cuerpo de la solicitud no puede estar vacío"

  Scenario: User ID not found during update
    Given I am authenticated with a valid token for update
    When I request to update my user with valid data but my user is not found
    Then I should receive an update type 404 "Not Found" response
    And I should see an update type message "Usuario no encontrado"

