package nl.michielgraat.adventofcode2024.day20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day20 extends AocSolver {

    protected Day20(final String filename) {
        super(filename);
    }

    private char[][] getGrid(final List<String> input) {
        final char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < grid.length; y++) {
            final String line = input.get(y);
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x] = line.charAt(x);
            }
        }
        return grid;
    }

    private Map<Position, Integer> dijkstra(final Position start, final char[][] grid) {
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

    private Optional<Position> getPositionFor(final char name, final char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == name) {
                    return Optional.of(new Position(x, y));
                }
            }
        }
        return Optional.empty();
    }

    private int calculateNrSingleCheats(final Position current, final Position start, final Position end,
            final Map<Position, Integer> distances, final char[][] grid, final int minDifference) {
        if (current.equals(end)) {
            return 0;
        } else {
            final List<Position> endPositions = current.getShortCuts(grid);
            int total = 0;
            for (final Position endPosition : endPositions) {
                if (distances.get(endPosition) > distances.get(current)
                        && distances.get(endPosition) - distances.get(current) - 2 >= minDifference) {
                    total++;
                }
            }
            return total;
        }
    }

    private int calculateTotalNrSingleCheats(final char[][] grid, final Position start, final Position end,
            final Map<Position, Integer> distances) {
        Position previous = null;
        Position current = start;
        int total = 0;
        while (!current.equals(end)) {
            total += calculateNrSingleCheats(current, start, end, distances, grid, 100);
            final List<Position> neighbours = current.getNeighbours(grid);
            for (final Position neighbour : neighbours) {
                if (!neighbour.equals(previous)) {
                    previous = current;
                    current = neighbour;
                    break;
                }
            }
        }
        return total;
    }

    private int calculateNrCheats(final Position current, final int maxDistance, final int minSecondsSaved,
            final Position end, final Map<Position, Integer> distances, final char[][] grid) {
        if (current.equals(end)) {
            return 0;
        } else {
            // Get all shortcuts with minimum distance to the current position of 2 and a
            // maximum distance of 20.
            final List<Position> endPositions = current.getAllShortCutsWithMaxDistance(maxDistance, grid, distances);
            int total = 0;
            for (final Position endPosition : endPositions) {
                // We want a minimum saving of minSecondsSaved, that is:
                // - Normally the distance between the current position and the end (that is, on
                // the normal path without cheating) is the distance from start to end minus the
                // distance from start to the current position.
                final int normalDistance = distances.get(end) - distances.get(current);
                // - Using cheats we save a certain amount, that is the manhattan distance
                // between the current position and the end position of the short cut (as we
                // have calculated the short cuts in this way).
                final int manhattanDistance = current.getManhattanDistance(endPosition.x(), endPosition.y());
                // - Of course, to get the new distance, we have to add the distance from the
                // end position of the short cut to the end of the path to the manhattan
                // distance.
                final int newDistance = manhattanDistance + distances.get(end) - distances.get(endPosition);
                if (normalDistance - newDistance >= minSecondsSaved) {
                    total++;
                }
            }
            return total;
        }
    }

    private int calculateTotalNrMultipleCheats(final char[][] grid, final Position start, final Position end,
            final Map<Position, Integer> distances) {
        Position previous = null;
        Position current = start;
        int total = 0;
        while (!current.equals(end)) {
            total += calculateNrCheats(current, 20, 100, end, distances, grid);
            final List<Position> neighbours = current.getNeighbours(grid);
            for (final Position neighbour : neighbours) {
                if (!neighbour.equals(previous)) {
                    previous = current;
                    current = neighbour;
                    break;
                }
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] grid = getGrid(input);
        final Position start = getPositionFor('S', grid).get();
        final Position end = getPositionFor('E', grid).get();
        final Map<Position, Integer> distances = dijkstra(start, grid);
        final int total = calculateTotalNrMultipleCheats(grid, start, end, distances);
        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] grid = getGrid(input);
        final Position start = getPositionFor('S', grid).get();
        final Position end = getPositionFor('E', grid).get();
        final Map<Position, Integer> distances = dijkstra(start, grid);
        final int total = calculateTotalNrSingleCheats(grid, start, end, distances);
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day20("day20.txt");
    }
}
