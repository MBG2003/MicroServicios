Feature: Login feature

  Scenario: Successful login
    Given I am on the login page
    And I register a new user
    When I login with the registered user's email and password
    Then I should receive a 200 "OK" login response
    And I should see the dashboard

  Scenario: Login fails when email is missing
    Given I am on the login page
    And I register a new user
    When I try to login without providing the email and with the registered user's password
    Then I should receive a 400 "Bad Request" login response
    And I should see a login message "Email y contraseña son requeridos"

  Scenario: Login fails when password is missing
    Given I am on the login page
    And I register a new user
    When I try to login with the registered user's email and without providing the password
    Then I should receive a 400 "Bad Request" login response
    And I should see a login message "Email y contraseña son requeridos"

  Scenario: Login fails with invalid credentials
    Given I am on the login page
    And I register a new user
    When I try to login with an invalid email or password
    Then I should receive a 400 "Bad Request" login response
    And I should see a login message "Credenciales inválidas"
