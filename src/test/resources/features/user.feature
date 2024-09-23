Feature: Get user by ID feature

  Scenario: Successfully get user by ID
    Given I am authenticated with a valid token for user
    When I request to get my user by ID
    Then I should receive a 200 "OK" response with the user details

  Scenario: Unauthorized to get user by ID
    Given I am authenticated with a valid token for user
    When I request to get another user's ID
    Then I should receive a user type 403 "Forbidden" response
    And I should see a user type message "No autorizado para acceder a esta información"

  Scenario: User not found
    Given I am authenticated with a valid token for user
    When I request to get user by a non-existing ID
    Then I should receive a user type 404 "Not Found" response
    And I should see a user type message "Usuario no encontrado"
