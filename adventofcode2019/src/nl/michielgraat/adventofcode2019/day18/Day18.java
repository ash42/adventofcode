package nl.michielgraat.adventofcode2019.day18;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day18 extends AocSolver {

    protected Day18(final String filename) {
        super(filename);
    }

    private char[][] getMaze(final List<String> input) {
        final char[][] maze = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final String currentRow = input.get(y);
            for (int x = 0; x < currentRow.length(); x++) {
                maze[y][x] = currentRow.charAt(x);
            }
        }
        return maze;
    }

    private void addSections(final Position start, final char[][] maze) {
        final int x = start.x();
        final int y = start.y();
        maze[y - 1][x - 1] = '@';
        maze[y - 1][x] = '#';
        maze[y - 1][x + 1] = '@';
        maze[y][x - 1] = '#';
        maze[y][x] = '#';
        maze[y][x + 1] = '#';
        maze[y + 1][x - 1] = '@';
        maze[y + 1][x] = '#';
        maze[y + 1][x + 1] = '@';
    }

    private Position getStart(final char[][] maze) {
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                if (maze[y][x] == '@') {
                    return new Position(x, y, '@');
                }
            }
        }
        throw new NoSuchElementException("No start found");
    }

    private List<Position> getStarts(final char[][] maze) {
        final List<Position> starts = new ArrayList<>();
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                if (maze[y][x] == '@') {
                    starts.add(new Position(x, y, '@'));
                }
            }
        }
        return starts;
    }

    private List<Position> getNeighbours(final Position p, final char[][] maze) {
        final List<Position> neighbours = new ArrayList<>();
        if (p.x() < maze[0].length - 1 && maze[p.y()][p.x() + 1] != '#') {
            neighbours.add(new Position(p.x() + 1, p.y(), maze[p.y()][p.x() + 1]));
        }
        if (p.x() > 0 && maze[p.y()][p.x() - 1] != '#') {
            neighbours.add(new Position(p.x() - 1, p.y(), maze[p.y()][p.x() - 1]));
        }
        if (p.y() < maze[0].length - 1 && maze[p.y() + 1][p.x()] != '#') {
            neighbours.add(new Position(p.x(), p.y() + 1, maze[p.y() + 1][p.x()]));
        }
        if (p.y() > 0 && maze[p.y() - 1][p.x()] != '#') {
            neighbours.add(new Position(p.x(), p.y() - 1, maze[p.y() - 1][p.x()]));
        }

        return neighbours;
    }

    private boolean isDoor(final Position p) {
        return Character.isLetter(p.type()) && Character.isUpperCase(p.type());
    }

    private boolean isKey(final Position p) {
        return Character.isLetter(p.type()) && Character.isLowerCase(p.type());
    }

    private Map<Position, Integer> getReachableKeys(final Position start, final List<Character> keys,
            final char[][] maze) {
        final Map<Position, Integer> result = new HashMap<>();
        final Map<Position, Integer> distances = new HashMap<>();
        final Deque<Position> queue = new ArrayDeque<>();
        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            final Position current = queue.pop();
            for (final Position n : getNeighbours(current, maze)) {
                if (!distances.keySet().contains(n)) {
                    distances.put(n, distances.get(current) + 1);
                    if (!isDoor(n) || (isDoor(n) && keys.contains(Character.toLowerCase(n.type())))) {
                        if (isKey(n) && !keys.contains(n.type())) {
                            result.put(n, distances.get(n));
                        } else {
                            queue.add(n);
                        }
                    }
                }
            }
        }

        return result;
    }

    private Map<Position, Map<Position, Integer>> getReachableKeysForPositions(final List<Position> starts,
            final List<Character> keys, final char[][] maze) {
        final Map<Position, Map<Position, Integer>> result = new HashMap<>();
        starts.forEach(s -> result.put(s, getReachableKeys(s, keys, maze)));
        return result;
    }

    private int getMinimumDistance(final List<Position> starts, final List<Character> keys,
            final Map<PositionKeys, Integer> visited,
            final char[][] maze) {
        // Sort the positions, to prevent cache misses.
        Collections.sort(starts);
        final PositionKeys state = new PositionKeys(starts, keys);
        if (visited.containsKey(state)) {
            return visited.get(state);
        }

        final Map<Position, Map<Position, Integer>> reachableKeysFromPositions = getReachableKeysForPositions(starts,
                keys,
                maze);
        int distance = 0;

        for (final Entry<Position, Map<Position, Integer>> e : reachableKeysFromPositions.entrySet()) {
            final Position startPoint = e.getKey();
            final Map<Position, Integer> reachableKeys = e.getValue();
            if (!reachableKeys.isEmpty()) {
                distance = Integer.MAX_VALUE;
                for (final Entry<Position, Integer> entry : reachableKeys.entrySet()) {
                    final Position keyPosition = entry.getKey();
                    int keyDistance = entry.getValue();
                    final List<Character> newKeyList = keys.stream().collect(Collectors.toList());
                    newKeyList.add(keyPosition.type());
                    // Sort to prevent cache misses.
                    Collections.sort(newKeyList);
                    final List<Position> nextStarts = starts.stream().collect(Collectors.toList());
                    nextStarts.remove(startPoint);
                    nextStarts.add(keyPosition);
                    keyDistance += getMinimumDistance(nextStarts, newKeyList, visited, maze);
                    distance = Math.min(distance, keyDistance);
                }
            }
        }

        visited.put(state, distance);
        return distance;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] maze = getMaze(input);
        if (getStarts(maze).size() == 1) {
            addSections(getStart(maze), maze);
        }
        return String.valueOf(getMinimumDistance(getStarts(maze), new ArrayList<>(),
                new HashMap<>(), maze));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] maze = getMaze(input);
        return String.valueOf(getMinimumDistance(getStarts(maze), new ArrayList<>(), new HashMap<>(), getMaze(input)));
    }

    public static void main(final String... args) {
        new Day18("day18.txt");
    }
}