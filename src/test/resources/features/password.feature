Feature: Password Recovery Feature

  Scenario: Successful password update
    Given I am on the password recovery page
    And I have registered a new user
    When I submit the registered user's email and a new password
    Then I should receive a password type 200 "OK" response
    And I should see a password type message "Contraseña actualizada exitosamente"

  Scenario: Password update fails when email is missing
    Given I am on the password recovery page
    When I submit no email and a new password "newpass123"
    Then I should receive a password type 400 "Bad Request" response
    And I should see a password type message "Email y nueva contraseña son requeridos"

  Scenario: Password update fails when new password is missing
    Given I am on the password recovery page
    When I submit the registered user's email and no new password
    Then I should receive a password type 400 "Bad Request" response
    And I should see a password type message "Email y nueva contraseña son requeridos"

  Scenario: Password update fails when the user is not found
    Given I am on the password recovery page
    When I submit an unregistered email and a new password
    Then I should receive a password type 404 "Not Found" response
    And I should see a password type message "Usuario no encontrado"
