package com.poc.behaviour;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The external system behaviour that each testing framework validates against.
 *
 * A dummy class that is taking the place of the various abstraction layers present when testing a
 * real system.
 *
 * Every mine event has a proposer, decided using a round robin allocation. Whenever the network
 * membership changes the proposer order resets.
 */
public class Network {

  private final Set<Node> nodes;
  private int chainHeight;
  private Iterator<Node> proposer;

  public Network(final Node... nodes) {
    this.nodes = Stream.of(nodes).collect(Collectors.toSet());
    this.proposer = this.nodes.iterator();
    this.chainHeight = 0;

    sync();
  }

  public void disconnect(final Node node) {
    nodes.remove(node);
    proposer = nodes.iterator();
  }

  public void reconnect(final Node node) {
    nodes.add(node);
    proposer = nodes.iterator();
  }

  public void sync() {
    for (final Node node : nodes) {
      node.sync(chainHeight);
    }
  }

  public Node mineBlock() {
    chainHeight++;

    if (!proposer.hasNext()) {
      proposer = nodes.iterator();
    }

    return proposer.next();
  }

  public int size(){return nodes.size();}
}
