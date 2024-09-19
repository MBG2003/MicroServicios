Feature: Password Recovery Feature

  Scenario: Successful password update
    Given I am on the password recovery page
    When I submit a valid email "mateo.baezg@example.com" and a new password "newpass123"
    Then I should receive a 200 OK response
    And the password should be successfully updated

  Scenario: Password update fails when email is missing
    Given I am on the password recovery page
    When I submit no email and a new password "newpass123"
    Then I should receive a 400 Bad Request response
    And I should see a message "Email and new password are required"

  Scenario: Password update fails when new password is missing
    Given I am on the password recovery page
    When I submit an email "mateo.baezg@example.com" and no new password
    Then I should receive a 400 Bad Request response
    And I should see a message "Email and new password are required"

  Scenario: Server error when updating password
    Given I am on the password recovery page
    When I submit an email "mateo.baezg@example.com" and a new password "newpass123"
    And the server fails to process the request
    Then I should receive a 500 Internal Server Error response
    And I should see a message "Server error"
