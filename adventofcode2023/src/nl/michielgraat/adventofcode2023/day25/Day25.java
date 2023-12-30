package nl.michielgraat.adventofcode2023.day25;

import java.util.HashMap;
import java.util.List;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day25 extends AocSolver {

    protected Day25(String filename) {
        super(filename);
    }

    private Graph readGraph(List<String> input) {
        Graph graph = new Graph(new HashMap<>());
        for (String line : input) {
            String start = line.substring(0, line.indexOf(":"));
            String[] dests = line.substring(line.indexOf(":") + 2).split(" ");
            graph.addVertex(start);
            for (String dest : dests) {
                graph.addVertex(dest);
                graph.addEdge(start, dest);
            }
        }
        return graph;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "part 2";
    }

    @Override
    protected String runPart1(final List<String> input) {
        Graph g = readGraph(input);
        while (g.karger() != 3) {
            // No 3 cuts, so reset.
            g = readGraph(input);
        }
        return String.valueOf(Graph.result);
    }

    public static void main(String... args) {
        new Day25("day25.txt");
    }
}