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

    Scenario: Validar la respuesta con json schema
      Given el endpoint "hhtp://localhost:3000/api/users/:id"
      When peticion GET
      Then la respuesta debe cumplir con el esquema "schema.json"
