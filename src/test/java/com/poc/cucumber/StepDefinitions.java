package com.poc.cucumber;

import static com.poc.behaviour.NetworkAsserts.assertProposerTakesTurnProposing;
import static junit.framework.TestCase.assertTrue;

import com.poc.behaviour.Network;
import com.poc.behaviour.Node;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {

  private Network network;
  private Node bouncingValidator;

  @Before
  public void setUp() {
    bouncingValidator = new Node();
    network = new Network(bouncingValidator, new Node(), new Node());
  }

  @Given("validator disconnects")
  public void validator_disconnects() {
    network.disconnect(bouncingValidator);
  }

  @Given("chain height increase after disconnection")
  public void chain_height_increase_after_disconnection() {
    network.mineBlock();
  }

  @When("validator reconnects")
  public void validator_reconnects() {
    network.reconnect(bouncingValidator);
  }

  @When("validator syncs to latest chain height")
  public void validator_syncs_to_latest_chain_height() {
    network.sync();
  }

  @Then("validator takes turn proposing")
  public void validator_must_take_turn_proposing() {
    assertProposerTakesTurnProposing(bouncingValidator, network);
  }
}
