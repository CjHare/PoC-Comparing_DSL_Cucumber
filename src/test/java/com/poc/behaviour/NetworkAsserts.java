package com.poc.behaviour;


import static org.junit.jupiter.api.Assertions.assertTrue;

import com.poc.behaviour.Network;
import com.poc.behaviour.Node;
import java.util.ArrayList;
import java.util.List;

public class NetworkAsserts {

  public static void assertProposerTakesTurnProposing(final Node expectedProposer,
      final Network network) {
    final List<Node> proposers = new ArrayList<>(network.size());

    for (int i = 0; i < network.size(); i++) {
      proposers.add(network.mineBlock());
    }

    assertTrue(proposers.contains(expectedProposer), String
        .format("Did not find the validator being a proposer within %d blocks", network.size()));
  }
}
