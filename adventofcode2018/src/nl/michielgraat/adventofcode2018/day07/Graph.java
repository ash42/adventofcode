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
    private Map<Node, List<Node>> nodes = new HashMap<>();

    public void addNode(char label) {
        nodes.putIfAbsent(new Node(label), new ArrayList<>());
    }

    public void removeNode(char label) {
        Node v = new Node(label);
        nodes.values().stream().forEach(e -> e.remove(v));
        nodes.remove(v);
    }

    public List<Node> getConnectedNodes(char label) {
        return new ArrayList<>(nodes.get(new Node(label)));
    }

    public void addEdge(char fromLabel, char toLabel) {
        Node from = new Node(fromLabel);
        Node to = new Node(toLabel);
        addNode(fromLabel);
        addNode(toLabel);
        nodes.get(from).add(to);
        Collections.sort(nodes.get(from));
    }

    public void removeEdge(char fromLabel, char toLabel) {
        Node from = new Node(fromLabel);
        Node to = new Node(toLabel);
        nodes.get(from).remove(to);
    }

    public boolean hasIncomingEdges(char label) {
        for (Entry<Node,List<Node>> e : nodes.entrySet()) {
            for (Node n : e.getValue()) {
                if (n.getLabel() == label) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Character> getStart() {
        List<Character> startNodes = new ArrayList<>();
        for (Node v : nodes.keySet()) {
            char label = v.getLabel();
            if (!hasIncomingEdges(label)) {
                startNodes.add(label);
            }
        }
        Collections.sort(startNodes);
        return startNodes;
    }

    public List<Node> getStartNodes() {
        List<Node> startNodes = new ArrayList<>();
        for (Node v : nodes.keySet()) {
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
