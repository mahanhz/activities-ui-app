Feature: Retrieve Participants

  Scenario: Participants are retrieved
    Given search by country "se"
    When retrieving participants
    Then participants are retrieved

  Scenario: Participants are not retrieved
    Given search by invalid country "abc"
    When attempting to retrieve participants
    Then participants are not retrieved