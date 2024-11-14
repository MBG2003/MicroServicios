Feature: Logs feature

  Scenario: Successful logs verification
    Given I register a new user for logs
    When I request to get all logs
    Then I should receive a 200 "OK" response with all logs details