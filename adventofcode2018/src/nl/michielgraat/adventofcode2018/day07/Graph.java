package nl.michielgraat.adventofcode2018.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Simple implementation of a graph.
 */
public class Graph {
    private final Map<Node, List<Node>> nodes = new HashMap<>();

    public void addNode(final char label) {
        nodes.putIfAbsent(new Node(label), new ArrayList<>());
    }

    public void removeNode(final char label) {
        final Node v = new Node(label);
        nodes.values().stream().forEach(e -> e.remove(v));
        nodes.remove(v);
    }

    public List<Node> getConnectedNodes(final char label) {
        return new ArrayList<>(nodes.get(new Node(label)));
    }

    public void addEdge(final char fromLabel, final char toLabel) {
        final Node from = new Node(fromLabel);
        final Node to = new Node(toLabel);
        addNode(fromLabel);
        addNode(toLabel);
        nodes.get(from).add(to);
        Collections.sort(nodes.get(from));
    }

    public void removeEdge(final char fromLabel, final char toLabel) {
        final Node from = new Node(fromLabel);
        final Node to = new Node(toLabel);
        nodes.get(from).remove(to);
    }

    public boolean hasIncomingEdges(final char label) {
        for (final Entry<Node, List<Node>> e : nodes.entrySet()) {
            for (final Node n : e.getValue()) {
                if (n.getLabel() == label) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Character> getStart() {
        final List<Character> startNodes = new ArrayList<>();
        for (final Node v : nodes.keySet()) {
            final char label = v.getLabel();
            if (!hasIncomingEdges(label)) {
                startNodes.add(label);
            }
        }
        Collections.sort(startNodes);
        return startNodes;
    }

    public List<Node> getStartNodes() {
        final List<Node> startNodes = new ArrayList<>();
        for (final Node v : nodes.keySet()) {
            if (!hasIncomingEdges(v.getLabel())) {
                startNodes.add(v);
            }
        }
        Collections.sort(startNodes);
        return startNodes;
    }

    public int getNodeCount() {
        return nodes.keySet().size();
    }
}
