Feature: Get all users feature

  Scenario: Successfully get all users
    Given I am authenticated with a valid token for users
    When I request to get all users
    Then I should receive a 200 "OK" response with all users details

  Scenario: No users found
    Given I am authenticated with a valid token for users
    When I request to get all users and there are no users
    Then I should receive a users type 404 "Not Found" response
    And I should see a users type message "No se encontraron usuarios"