package nl.michielgraat.adventofcode2019.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day15 extends AocSolver {

    protected Day15(final String filename) {
        super(filename);
    }

    private Map<Position, Integer> dijkstra(final Position start, final List<Position> positions) {
        final PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final int dist = distances.get(current);
            final List<Position> neighbours = current.getNeighbours(positions);
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

    private int runDroid(final int input, final IntcodeComputer droid) {
        droid.addInput(input);
        droid.run();
        return (int) droid.readOutput();
    }

    private void getMaze(final Position start, final IntcodeComputer droid, final List<Position> visitedPositions) {
        if (!visitedPositions.contains(new Position(start.x(), start.y() - 1, Status.EMPTY))) {
            final Position next = new Position(start.x(), start.y() - 1, Status.getStatus(runDroid(1, droid)));
            visitedPositions.add(next);
            if (next.status() != Status.WALL) {
                getMaze(next, droid, visitedPositions);
                runDroid(2, droid);
            }
        }
        if (!visitedPositions.contains(new Position(start.x(), start.y() + 1, Status.EMPTY))) {
            final Position next = new Position(start.x(), start.y() + 1, Status.getStatus(runDroid(2, droid)));
            visitedPositions.add(next);
            if (next.status() != Status.WALL) {
                getMaze(next, droid, visitedPositions);
                runDroid(1, droid);
            }
        }
        if (!visitedPositions.contains(new Position(start.x() - 1, start.y(), Status.EMPTY))) {
            final Position next = new Position(start.x() - 1, start.y(), Status.getStatus(runDroid(3, droid)));
            visitedPositions.add(next);
            if (next.status() != Status.WALL) {
                getMaze(next, droid, visitedPositions);
                runDroid(4, droid);
            }
        }
        if (!visitedPositions.contains(new Position(start.x() + 1, start.y(), Status.EMPTY))) {
            final Position next = new Position(start.x() + 1, start.y(), Status.getStatus(runDroid(4, droid)));
            visitedPositions.add(next);
            if (next.status() != Status.WALL) {
                getMaze(next, droid, visitedPositions);
                runDroid(3, droid);
            }
        }

    }

    private List<Position> buildMaze(final List<String> input) {
        final List<Position> maze = new ArrayList<>();
        final Position start = new Position(0, 0, Status.EMPTY);
        maze.add(start);
        getMaze(start, new IntcodeComputer(input), maze);
        return maze;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Position> maze = buildMaze(input);
        final Position start = maze.stream().filter(p -> p.status() == Status.OXYGEN_SYSTEM).findFirst()
                .orElseThrow(NoSuchElementException::new);
        final Map<Position, Integer> distances = dijkstra(start, maze);
        return String
                .valueOf(distances.values().stream().mapToInt(d -> d).max().orElse(Integer.MAX_VALUE));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Position> maze = buildMaze(input);
        final Map<Position, Integer> distances = dijkstra(new Position(0, 0, Status.EMPTY), maze);
        return String.valueOf(distances.entrySet().stream().filter(e -> e.getKey().status() == Status.OXYGEN_SYSTEM)
                .collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElse(Integer.MAX_VALUE));
    }

    public static void main(final String... args) {
        new Day15("day15.txt");
    }
}
