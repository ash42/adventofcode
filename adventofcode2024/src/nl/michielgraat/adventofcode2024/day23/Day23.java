package nl.michielgraat.adventofcode2024.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day23 extends AocSolver {

    protected Day23(final String filename) {
        super(filename);
    }

    private List<Node> parseInput(final List<String> input) {
        final List<Node> result = new ArrayList<>();
        for (final String line : input) {
            final Node node1 = new Node(line.split("-")[0]);
            final Node node2 = new Node(line.split("-")[1]);

            if (!result.contains(node1)) {
                result.add(node1);
            }
            if (!result.contains(node2)) {
                result.add(node2);
            }
            result.get(result.indexOf(node1)).addAdjacentNode(node2);
            result.get(result.indexOf(node2)).addAdjacentNode(node1);
        }
        return result;
    }

    private Set<List<Node>> findTriangles(final List<Node> nodes) {
        final Set<List<Node>> triangles = new HashSet<>();
        // Loop over every node, this is "current". Fetch its adjacent nodes and finally
        // the adjacent nodes to these ones. These adjacent-adjacent nodes should also
        // be an adjacent node to current, if so, we have a triangle.
        for (final Node current : nodes) {
            for (Node adj : current.getAdjacentNodes()) {
                // Fetch from original list, because only there all adjacent nodes to this node
                // are stored.
                adj = nodes.get(nodes.indexOf(adj));
                for (Node adjAdj : adj.getAdjacentNodes()) {
                    // As above.
                    adjAdj = nodes.get(nodes.indexOf(adjAdj));
                    if (current.getAdjacentNodes().contains(adjAdj)) {
                        final List<Node> triangle = new ArrayList<>();
                        triangle.add(current);
                        triangle.add(adj);
                        triangle.add(adjAdj);
                        // Sort for easy reading when outputting
                        Collections.sort(triangle);
                        triangles.add(triangle);
                    }
                }
            }
        }
        return triangles;
    }

    private int getNrTTriangles(final List<Node> nodes) {
        final Set<List<Node>> triangles = findTriangles(nodes);
        int totalTTriangles = 0;
        for (final List<Node> triangle : triangles) {
            for (final Node node : triangle) {
                if (node.getName().startsWith("t")) {
                    totalTTriangles++;
                    break;
                }
            }
        }
        return totalTTriangles;
    }

    private List<Node> findMaximum(final List<Node> nodes) {
        int maximum = 0;
        List<Node> maxClique = new ArrayList<>();
        // Start a new clique per node. For all other nodes, check if they are adjacent
        // to all the nodes we have in this clique, if so, add. Keep track of which
        // clique has the biggest size, also store its composition.
        for (final Node node : nodes) {
            final List<Node> clique = new ArrayList<>();
            clique.add(node);
            for (final Node n2 : nodes) {
                if (!n2.equals(node)) {
                    boolean mayBeAdded = true;
                    for (final Node cNode : clique) {
                        if (!cNode.getAdjacentNodes().contains(n2)) {
                            mayBeAdded = false;
                            break;
                        }
                    }
                    if (mayBeAdded) {
                        clique.add(n2);
                    }
                }
            }
            if (clique.size() > maximum) {
                maximum = clique.size();
                maxClique = clique;
            }
        }
        return maxClique;
    }

    private String getPassword(final List<Node> nodes) {
        Collections.sort(nodes);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            final Node node = nodes.get(i);
            sb.append(node.getName());
            if (i < nodes.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @Override
    protected String runPart2(final List<String> input) {
        return getPassword(findMaximum(parseInput(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getNrTTriangles(parseInput(input)));
    }

    public static void main(final String... args) {
        new Day23("day23.txt");
    }
}
