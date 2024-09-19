Feature: The Login API provide to user the function for Login and Logout of application
  Scenario: Yo como usuario registrado quiero poder autenticarme en el sistema para poder hacer uso de las funcionalidades
    Given Soy un usuario registrado del sistema usando credenciales validas
    When invoco el servicio de autenticación
    Then obtengo un status code 201
    And un token de autenticación