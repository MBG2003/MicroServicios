Feature: Delete user by ID feature

  Scenario: Successfully delete user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to delete user by ID "66e03b7314a4734111e8306c"
    Then I should receive a 200 OK response

  Scenario: Unauthorized to delete user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to delete user by ID "60a7842f58d6c90d58f4e2b7"
    Then I should receive a 403 Forbidden response

  Scenario: User not found by ID for deletion
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to delete user by ID "60a7842f58d6c90d58f4e2b8"
    Then I should receive a 404 Not Found response

  Scenario: Invalid ID format for deletion
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to delete user by ID "invalid-id"
    Then I should receive a 400 Bad Request response


  Scenario: Server error when deleting user
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to delete user by ID "66e03b7314a4734111e8306c"
    Then I should receive a 500 Internal Server Error response
