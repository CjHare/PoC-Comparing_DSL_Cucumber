Feature: ValidationReconnection
  Verifying the reconnection behaviour for validators

  Scenario: After disconnecting and becoming out dated, Validator can reconnect and after syncing propose a block
    Given validator disconnects
    And chain height increase after disconnection
    When validator reconnects
    And validator syncs to latest chain height
    Then validator must be the proposer of a block within five blocks