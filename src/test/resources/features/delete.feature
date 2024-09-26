Feature: Delete user by ID feature

  Scenario: Successfully delete user by ID
    Given I am authenticated with a valid token for delete
    When I request to delete my user
    Then I should receive an delete type 200 "OK" response
    And I should see an delete type message "Usuario eliminado exitosamente"

  Scenario: Unauthorized to delete user by ID
    Given I am authenticated with a valid token for delete
    When I request to delete another user
    Then I should receive an delete type 403 "Forbidden" response
    And I should see an delete type message "No autorizado para eliminar este usuario"
