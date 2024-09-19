Feature: Partial update user by ID feature

  Scenario: Successfully partially update user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to partially update user by ID "66e03b7314a4734111e8306c" with valid data
    Then I should receive a 200 OK response

  Scenario: Unauthorized to partially update user by ID
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to partially update user by ID "60a7842f58d6c90d58f4e2b7" with valid data
    Then I should receive a 403 Forbidden response

  Scenario: Partial update user by ID with empty request body
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to partially update user by ID "66e03b7314a4734111e8306c" with an empty request body
    Then I should receive a 400 Bad Request response

  Scenario: User not found by ID for partial update
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to partially update user by ID "60a7842f58d6c90d58f4e2b8" with valid data
    Then I should receive a 404 Not Found response

  Scenario: Invalid ID format for partial update
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to partially update user by ID "invalid-id" with valid data
    Then I should receive a 400 Bad Request response

  Scenario: Server error when partially updating user
    Given I am authenticated with user ID "66e03b7314a4734111e8306c"
    When I request to partially update user by ID "66e03b7314a4734111e8306c" with valid data
    Then I should receive a 500 Internal Server Error response
