package com.poc.dsl;

import static com.poc.dsl.NetworkAsserts.assertProposerTakesTurnProposing;

import com.poc.behaviour.Network;
import com.poc.behaviour.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorReconnection {

  private Network network;
  private Node bouncingValidator;

  @BeforeEach
  public void setUp() {
    bouncingValidator = new Node();
    network = new Network(bouncingValidator, new Node(), new Node());
  }

  @Test
  public void mustProposeBlockInTurn_afterSyncing() {
    network.disconnect(bouncingValidator);

    network.mineBlock();

    network.reconnect(bouncingValidator);

    network.sync();

    assertProposerTakesTurnProposing(bouncingValidator, network);
  }
}


