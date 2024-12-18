package nl.michielgraat.adventofcode2024.day18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day18 extends AocSolver {

    protected Day18(final String filename) {
        super(filename);
    }

    private int[][] getGrid(final int size, final int nrBytes, final List<String> input) {
        final int[][] grid = new int[size][size];
        for (int i = 0; i <= nrBytes; i++) {
            final String line = input.get(i);
            grid[Integer.parseInt(line.split(",")[1])][Integer.parseInt(line.split(",")[0])] = 1;
        }
        return grid;
    }

    private Map<Position, Integer> dijkstra(final Position start, final List<Position> positions, final int[][] grid) {
        final PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final int dist = distances.get(current);
            final List<Position> neighbours = current.getNeighbours(grid);
            for (final Position n : neighbours) {
                int ndist = dist;
                ndist++;
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
        }

        return distances;
    }

    private String findFirstBlockingByte(final List<String> input) {
        final Position start = new Position(0, 0);
        final Position end = new Position(70, 70);
        // Do a binary search, start in the middle between 1025 and the last byte (we
        // can skip the first 1024, because in part 1 we established that there is a
        // path at byte 1024.)
        int currentStart = 1025;
        int middle = currentStart + Math.round((input.size() - 1025) / 2);
        while (true) {
            Map<Position, Integer> distances = dijkstra(start, new ArrayList<>(), getGrid(71, middle, input));
            if (distances.get(end) == null) {
                // There is no path between start and end for byte 'middle', so check if there
                // *is* a path one before the middle. If so, we have the byte which blocks a
                // path between start and end.
                distances = dijkstra(start, new ArrayList<>(), getGrid(71, middle - 1, input));
                if (distances.get(end) != null) {
                    break;
                } else {
                    // Also no path, so we have to look at a lower byte.
                    middle = currentStart + Math.round((middle - currentStart) / 2);
                }
            } else {
                // There is a path, so we have to look at a higher byte.
                currentStart = middle;
                middle += Math.round((input.size() - middle) / 2);
            }
        }
        return input.get(middle);
    }

    @Override
    protected String runPart2(final List<String> input) {
        return findFirstBlockingByte(input);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Position start = new Position(0, 0);
        final Position end = new Position(70, 70);
        final Map<Position, Integer> distances = dijkstra(start, new ArrayList<>(), getGrid(71, 1024, input));
        return String.valueOf(distances.get(end));
    }

    public static void main(final String... args) {
        new Day18("day18.txt");
    }
}