Feature: Actuator
  Scenario: Should be healthy
    When I ask for its health
    Then its status should be UP
