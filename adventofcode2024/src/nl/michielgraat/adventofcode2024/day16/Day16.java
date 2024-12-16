package nl.michielgraat.adventofcode2024.day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day16 extends AocSolver {

    protected Day16(final String filename) {
        super(filename);
    }

    private char[][] getGrid(final List<String> input) {
        final char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x);
            }
        }
        return grid;
    }

    private List<Position> parseGrid(final List<String> input) {
        final List<Position> positions = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                positions.add(new Position(x, y, Position.NORTH));
                positions.add(new Position(x, y, Position.EAST));
                positions.add(new Position(x, y, Position.SOUTH));
                positions.add(new Position(x, y, Position.WEST));
            }
        }
        return positions;
    }

    private Optional<Position> getCharacterPosition(final char c, final char[][] grid, final int direction) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == c) {
                    return Optional.of(new Position(x, y, direction));
                }
            }
        }
        return Optional.empty();
    }

    private Position getStart(final char[][] grid) {
        return getCharacterPosition('S', grid, Position.EAST).get();
    }

    private List<Position> getEnds(final char[][] grid) {
        final Position n = getCharacterPosition('E', grid, Position.NORTH).get();
        final Position e = getCharacterPosition('E', grid, Position.EAST).get();
        final Position s = getCharacterPosition('E', grid, Position.SOUTH).get();
        final Position w = getCharacterPosition('E', grid, Position.WEST).get();
        final List<Position> ends = new ArrayList<>();
        ends.add(n);
        ends.add(e);
        ends.add(s);
        ends.add(w);
        return ends;
    }

    private Map<Position, Integer> dijkstra(final Position start, final List<Position> positions, final char[][] grid) {
        final PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final int dist = distances.get(current);
            final Optional<Position> neighbour = current.getNeighbours(grid);
            final List<Position> turns = current.getTurns();
            if (neighbour.isPresent()) {
                final Position n = neighbour.get();
                int ndist = dist;
                ndist++;
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
            for (final Position t : turns) {
                int ndist = dist;
                ndist += 1000;
                if (ndist < distances.getOrDefault(t, Integer.MAX_VALUE)) {
                    distances.put(t, ndist);
                    queue.add(t);
                }
            }
        }

        return distances;
    }

    private void findCoordinatesOnShortestPaths(final Position current, final Position start, final Position end,
            final Map<Position, Integer> distances, final char[][] grid, final Set<Coordinate> coordinates) {
        final Coordinate c = new Coordinate(current.x(), current.y());
        coordinates.add(c);
        final int distanceToStart = distances.get(current);
        if (current.equals(start)) {
            return;
        } else {
            // Add the direct neighbour if the distance to start is smaller than the current
            // one
            final Optional<Position> opNeighbour = current.getNeighbourReversed(grid);
            final List<Position> nextPositions = new ArrayList<>();
            if (opNeighbour.isPresent() && distances.get(opNeighbour.get()) < distanceToStart) {
                nextPositions.add(opNeighbour.get());
            }

            // Add the turn with the shortest distance to start (but only if that distance
            // is smaller than the current one)
            final List<Position> turns = current.getTurns();
            final int distance1 = distances.get(turns.get(0));
            final int distance2 = distances.get(turns.get(1));
            if (distance1 < distance2 && distance1 < distanceToStart) {
                nextPositions.add(turns.get(0));
            } else if (distance2 < distanceToStart) {
                nextPositions.add(turns.get(1));
            }

            for (final Position p : nextPositions) {
                findCoordinatesOnShortestPaths(p, start, end, distances, grid, coordinates);
            }
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] grid = getGrid(input);
        final List<Position> positions = parseGrid(input);
        final Position start = getStart(grid);
        final List<Position> ends = getEnds(grid);
        final Map<Position, Integer> result = dijkstra(start, positions, grid);
        // Get the end with the lowest score
        final int min = ends.stream().mapToInt(e -> result.get(e)).min().getAsInt();
        final Position realEnd = result.entrySet().stream().filter(e -> e.getValue().equals(min)).map(Map.Entry::getKey)
                .findFirst().get();
        final Set<Coordinate> paths = new HashSet<>();
        findCoordinatesOnShortestPaths(realEnd, start, realEnd, result, grid, paths);
        return String.valueOf(paths.size());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] grid = getGrid(input);
        final List<Position> positions = parseGrid(input);
        final Position start = getStart(grid);
        final List<Position> ends = getEnds(grid);
        final Map<Position, Integer> result = dijkstra(start, positions, grid);
        return String.valueOf(ends.stream().mapToInt(e -> result.get(e)).min().getAsInt());
    }

    public static void main(final String... args) {
        new Day16("day16.txt");
    }
}

record Coordinate(int x, int y) {
};
