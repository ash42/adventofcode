package nl.michielgraat.adventofcode2023.day25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

public record Graph(Map<Vertex, List<Vertex>> adjVertices) {

    public static long result = 0;

    public void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    public void removeVertex(String label) {
        Vertex v = new Vertex(label);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));
    }

    private void removeRemaining(Vertex oldV1, Vertex oldV2) {
        for (Entry<Vertex, List<Vertex>> entry : adjVertices.entrySet()) {
            entry.getValue().remove(oldV1);
            entry.getValue().remove(oldV2);
        }
        removeVertex(oldV1.label());
        removeVertex(oldV2.label());
    }

    public void addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }

    public void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    public List<Vertex> getAdjVertices(String label) {
        return adjVertices.get(new Vertex(label));
    }

    private void combine(Vertex v1, Vertex v2) {
        // Remove the old edges between v1 and v2
        removeEdge(v1.label(), v2.label());
        removeEdge(v2.label(), v1.label());
        // Combines the other edges from v1 and v2 into one.
        List<Vertex> edges1 = adjVertices.get(v1);
        List<Vertex> edges2 = adjVertices.get(v2);
        Set<Vertex> destinations = new HashSet<>();
        destinations.addAll(edges1);
        destinations.addAll(edges2);
        // Build the new label
        String newLabel = v1.label() + "-" + v2.label();
        addVertex(newLabel);
        for (Vertex e : destinations) {
            addEdge(newLabel, e.label());
        }
        // Now remove every remaining reference to v1 or v2.
        removeRemaining(v1, v2);
    }

    public long getNrVerticesInGroup(int groupNr) {
        System.out.println(adjVertices.keySet().size());
        Vertex v = adjVertices.keySet().toArray(new Vertex[adjVertices.keySet().size()])[groupNr];
        return v.label().split("-").length;
    }

    private Graph getCopy() {
        Map<Vertex, List<Vertex>> cmap = new HashMap<>();
        cmap.putAll(adjVertices);
        return new Graph(cmap);
    }

    public long karger() {
        // Store the original configuration of this graph.
        Graph copy = getCopy();

        // First combine random vertices until we have only two vertices left.
        while (adjVertices.keySet().size() > 2) {
            Random random = new Random();
            int r = random.ints(0, adjVertices.keySet().size()).findAny().getAsInt();
            Vertex v = adjVertices.keySet().toArray(new Vertex[adjVertices.keySet().size()])[r];
            int r2 = random.ints(0, adjVertices.get(v).size()).findAny().getAsInt();
            Vertex v2 = adjVertices.get(v).get(r2);

            combine(v, v2);
        }

        // Now count the number of edges which did exist in the original graph but do
        // not exist in the current.
        // Combined vertices have the format <old vertex label1>-<old vertex
        // label2>-<old vertex label3> etc.
        Vertex v1 = adjVertices.keySet().toArray(new Vertex[adjVertices.keySet().size()])[0];
        Vertex v2 = adjVertices.keySet().toArray(new Vertex[adjVertices.keySet().size()])[1];
        int nrCuts = 0;
        // Just count old connections between the first vertex group and the second...
        for (String part : v1.label().split("-")) {
            List<Vertex> vertices = copy.getAdjVertices(part);
            for (String part2 : v2.label().split("-")) {
                if (vertices.contains(new Vertex(part2))) {
                    nrCuts++;
                }
            }
        }
        // ...and the other way around.
        for (String part : v2.label().split("-")) {
            List<Vertex> vertices = copy.getAdjVertices(part);
            for (String part2 : v1.label().split("-")) {
                if (vertices.contains(new Vertex(part2))) {
                    nrCuts++;
                }
            }
        }
        
        if (nrCuts == 3) {
            // Number of cuts is 3, so store the result
            long size1 = v1.label().split("-").length;
            long size2 = v2.label().split("-").length;
            result = size1 * size2;
        }
        return nrCuts;
    }
}
