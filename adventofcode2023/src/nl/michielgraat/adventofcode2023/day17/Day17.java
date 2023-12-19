package nl.michielgraat.adventofcode2023.day17;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day17 extends AocSolver {

    protected Day17(String filename) {
        super(filename);
    }

    private int[][] readGrid(final List<String> input) {
        int[][] grid = new int[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x) - '0';
            }
        }
        return grid;
    }

    private int dijkstra(int[][] grid, boolean part1) {
        Queue<Element> queue = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        int endX = grid[grid.length - 1].length - 1;
        int endY = grid.length - 1;

        Node eastStart = new Node(1, 0, 1, Element.EAST);
        Node southStart = new Node(0, 1, 1, Element.SOUTH);
        queue.add(new Element(eastStart, grid[0][1]));
        queue.add(new Element(southStart, grid[1][0]));

        while (!queue.isEmpty()) {
            final Element current = queue.poll();
            if (visited.contains(current.getNode())) {
                continue;
            }
            visited.add(current.getNode());
            if (current.getNode().x() == endX && current.getNode().y() == endY
                    && (part1 || current.getNode().blocks() >= 4)) {
                return current.getHeatLoss();
            }

            queue.addAll(part1 ? current.getNeighbours(grid) : current.getNeighboursForPart2(grid));

        }

        return 0;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(dijkstra(readGrid(input), false));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(dijkstra(readGrid(input), true));
    }

    public static void main(String... args) {
        new Day17("day17.txt");
    }
}
