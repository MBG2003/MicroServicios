Feature: Get user by ID feature

  Scenario: Successfully get user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to get user by ID "66e03b7314a4734111e8306c"
    Then I should receive a 200 OK response with the user details

  Scenario: Unauthorized to get user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to get user by ID "66e03b7314a4734111e8306c"
    Then I should receive a 403 Forbidden response with a message "No autorizado para acceder a esta información"

  Scenario: User not found by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to get user by ID "66e03b7314a4734111e8306c"
    Then I should receive a 404 Not Found response with a message "Usuario no encontrado"

  Scenario: Invalid ID format
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to get user by ID "invalid-id"
    Then I should receive a 400 Bad Request response with a message "Formato de ID inválido"
