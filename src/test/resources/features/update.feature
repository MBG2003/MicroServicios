Feature: Update user by ID feature

  Scenario: Successfully update user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to update user by ID "66e03b7314a4734111e8306c" with valid data
    Then I should receive a 200 OK response with the updated user details

  Scenario: Unauthorized to update user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to update user by ID "60e03b7314a4734111e8306c" with valid data
    Then I should receive a 403 Forbidden response with a message "No autorizado para actualizar esta información"

  Scenario: Update user by ID with empty request body
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to update user by ID "66e03b7314a4734111e8306c" with an empty request body
    Then I should receive a 400 Bad Request response with a message "El cuerpo de la solicitud no puede estar vacío"

  Scenario: User not found by ID for update
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to update user by ID "60a7842f58d6c90d58f4e2b8" with valid data
    Then I should receive a 404 Not Found response with a message "Usuario no encontrado"

  Scenario: Invalid ID format for update
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to update user by ID "invalid-id" with valid data
    Then I should receive a 400 Bad Request response with a message "Formato de ID inválido"

  Scenario: Server error when updating user
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to update user by ID "66e03b7314a4734111e8306c" with valid data
    Then I should receive a 500 Internal Server Error response with a message "Error del servidor al actualizar usuario"