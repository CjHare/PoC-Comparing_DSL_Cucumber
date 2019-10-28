package com.poc.dsl;

import static com.poc.dsl.NetworkAsserts.assertProposesWithinProposalRound;

import com.poc.behaviour.Network;
import com.poc.behaviour.Node;
import org.junit.Before;
import org.junit.Test;

public class ValidatorReconnection {

  private Network network;
  private Node bouncingValidator;

  @Before
  public void setUp() {
    bouncingValidator = new Node();
    network = new Network(bouncingValidator, new Node(), new Node());
  }

  @Test
  public void mustSyncThenProposeBlock_afterDisconnection_whenHasProgressed() {
    network.disconnect(bouncingValidator);

    network.mineBlock();

    network.reconnect(bouncingValidator);

    network.sync();

    assertProposesWithinProposalRound(bouncingValidator, network);
  }
}


