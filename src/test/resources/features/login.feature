Feature: Login feature

  Scenario: Successful login
    Given I am on the login page
    When I enter valid credentials with email "mateo.baezg@example.com" and password "contra123"
    Then I should see the dashboard

  Scenario: Login fails when email is missing
    Given I am on the login page
    When I try to login without providing the email and with password "contra123"
    Then I should receive a 400 Bad Request response

  Scenario: Login fails when password is missing
    Given I am on the login page
    When I try to login with email "mateo.baezg@example.com" and without providing the password
    Then I should receive a 400 Bad Request response

  Scenario: Login fails with invalid credentials
    Given I am on the login page
    When I enter valid credentials with email "mateo.baezg@example.com" and password "contra123"
    Then I should see the dashboard

