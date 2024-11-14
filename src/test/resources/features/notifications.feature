# notifications.feature

Feature: Microservicio de notificaciones

  Scenario: Recibir y registrar una notificación
    Given que existe una alerta generada
    When envío una notificación
    Then la respuesta debe ser "200 OK"

  Scenario: Consultar todas las notificaciones registradas
    Given que el microservicio de notificaciones está disponible en "localhost:5000"
    When realizo una solicitud GET a "/notifications"
    Then la respuesta debe ser "200 OK"
    And el cuerpo de la respuesta debe contener una lista de notificaciones

  Scenario: Consultar una notificación específica
    Given que el microservicio de notificaciones está disponible en "localhost:5000"
    When realizo una solicitud GET a "/notifications/1"
    Then la respuesta debe ser "200 OK"
    And el cuerpo de la respuesta debe contener la notificación con ID "1"

  Scenario: Consultar métricas
    Given que el microservicio de notificaciones está disponible en "localhost:9090"
    When realizo una solicitud GET a prometheus en "/targets"
    Then la respuesta debe ser "200 OK"