package com.poc.junit5;

import static com.poc.behaviour.NetworkAsserts.assertProposerTakesTurnProposing;

import com.poc.behaviour.Network;
import com.poc.behaviour.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class ValidatorReconnection {

  private Network network;
  private Node bouncingValidator;

  @BeforeEach
  public void setUp() {
    bouncingValidator = new Node();
    network = new Network(bouncingValidator, new Node(), new Node());
  }

  @Nested
  class ProposeBlockInTurn_afterSyncing {
    @Test
    @Order(1)
    @DisplayName("validator disconnects")
    public void disconnects() {
      network.disconnect(bouncingValidator);
    }


    @Test
    @Order(2)
    @DisplayName("chain height increase after disconnection")
    public void chain_height_increase_after_disconnection() {
      network.mineBlock();
    }

    @Test
    @Order(3)
    @DisplayName("validator reconnects")
    public void validator_reconnects() {
      network.reconnect(bouncingValidator);
    }

    @Test
    @Order(4)
    @DisplayName("validator syncs to latest chain height")
    public void validator_syncs_to_latest_chain_height() {
      network.sync();
    }

    @Test
    @Order(5)
    @DisplayName("validator takes turn proposing")
    public void validator_must_take_turn_proposing() {
      assertProposerTakesTurnProposing(bouncingValidator, network);
    }
  }
}
