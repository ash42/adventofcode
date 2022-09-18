package nl.michielgraat.adventofcode2018.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day07 {

    private static final String FILENAME = "day07.txt";

    private Graph buildGraph(List<String> lines) {
        Graph g = new Graph();
        for (String line : lines) {
            String[] words = line.split(" ");
            String before = words[1];
            String after = words[7];
            g.addEdge(before.charAt(0), after.charAt(0));
        }
        return g;
    }

    /**
     * Implements Kahn's algorithm for topological sorting.
     * 
     * Does an extra sort on the set of all nodes with no
     * incoming edge (s), to make sure nodes get processed
     * in the correct order.
     * 
     * @param g The graph to perform Kahn's algorithm on.
     * @return The sorted result as a String.
     */
    private String kahn(Graph g) {
        List<Character> s = new ArrayList<>();
        List<Character> l = new ArrayList<>();
        s.addAll(g.getStart());
        while (!s.isEmpty()) {
            char current = s.remove(0);
            l.add(current);
            List<Node> connected = g.getConnectedNodes(current);
            for (Node n : connected) {
                g.removeEdge(current, n.getLabel());
                if (!g.hasIncomingEdges(n.getLabel())) {
                    s.add(n.getLabel());
                }
            }
            Collections.sort(s);
        }
        return l.stream().map(String::valueOf).collect(Collectors.joining());
    }

    /**
     * Builds a list of workers.
     * 
     * @param nr The number of workers.
     * @return A list with the specified number of workers.
     */
    private List<Worker> buildWorkerList(int nr) {
        List<Worker> workers = new ArrayList<>();
        for (int i = 1; i <= nr; i++) {
            workers.add(new Worker(i));
        }
        return workers;
    }

    /**
     * For every worker that has finished its work, get the nodes connected to the
     * node the work is finished on and remove the edge between the finished node
     * and the connected nodes. If a connected node does not have any incoming
     * edges, it is ready to be workers on, so add it to the work list.
     * 
     * @param graph         The graph.
     * @param workers       The workers.
     * @param finishedNodes The finished nodes.
     * @param workList      The list of nodes which are ready to be worked on.
     */
    private void handleFinishedWorkers(Graph graph, List<Worker> workers, List<Character> finishedNodes,
            List<Node> workList) {
        for (Worker w : workers) {
            if (w.finished()) {
                List<Node> connected = graph.getConnectedNodes(w.getNode().getLabel());
                finishedNodes.add(w.getNode().getLabel());
                for (Node n : connected) {
                    graph.removeEdge(w.getNode().getLabel(), n.getLabel());
                    if (!graph.hasIncomingEdges(n.getLabel())) {
                        workList.add(n);
                    }
                }
                w.setNode(null);
            }
        }
    }

    /**
     * Distribute work to workers which are idle (that is, do not have a node to
     * work on).
     * 
     * @param workers  The workers.
     * @param workList The list of nodes which are ready to be worked on.
     */
    private void distributeWork(List<Worker> workers, List<Node> workList) {
        for (Worker w : workers) {
            if (w.idle() && !workList.isEmpty()) {
                Node current = workList.remove(0);
                w.setNode(current);
            }
        }
    }

    /**
     * Gets the number of seconds needed to work through the graph.
     * 
     * @param graph The graph to work on.
     * @return The number of seconds needed wo work through the graph.
     */
    private int getNumberOfSecondsNeeded(Graph graph) {
        List<Worker> workers = buildWorkerList(5);
        int nrOfNodes = graph.getNodeCount();
        List<Character> finishedNodes = new ArrayList<>();
        List<Node> workList = graph.getStartNodes();

        int second = 0;
        while (finishedNodes.size() < nrOfNodes || workers.stream().anyMatch(w -> !w.idle())) {
            //1. Distribute available work to idle workers (workers which do not have a node to work on)
            distributeWork(workers, workList);
            //2. Work.
            workers.forEach(Worker::work);
            //3. Handle the finished workers.
            handleFinishedWorkers(graph, workers, finishedNodes, workList);
            second++;
        }
        return second;
    }

    private int runPart2(List<String> lines) {
        Graph graph = buildGraph(lines);
        return getNumberOfSecondsNeeded(graph);
    }

    private String runPart1(List<String> lines) {
        Graph g = buildGraph(lines);
        return kahn(g);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day07().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day07().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
