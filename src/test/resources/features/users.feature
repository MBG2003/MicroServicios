Feature: Get all users feature

  Scenario: Successfully get all users
    Given I am authenticated
    When I request to get all users
    Then I should receive a 200 OK response with a list of users

  Scenario: No users found
    Given I am authenticated
    When I request to get all users
    Then I should receive a 404 Not Found response with a message "No se encontraron usuarios"

  Scenario: Server error when getting users
    Given I am authenticated
    When I request to get all users
    Then I should receive a 500 Internal Server Error response with a message "Error del servidor al obtener usuarios"
