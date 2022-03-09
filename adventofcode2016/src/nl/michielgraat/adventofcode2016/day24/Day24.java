package nl.michielgraat.adventofcode2016.day24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day24 {

    private static final String FILENAME = "day24.txt";

    public int dijkstra(final Position start, final Position end, final char[][] grid) {
        final Queue<Position> queue = new LinkedList<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final int dist = distances.get(current);
            final List<Position> neighbours = current.getNeighbours(grid);
            for (final Position neighbour : neighbours) {
                final int ndist = dist + 1;
                if (ndist < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, ndist);
                    queue.add(neighbour);

                }
            }
        }
        return distances.entrySet().stream().filter(e -> e.getKey().equals(end)).collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElse(Integer.MAX_VALUE);
    }

    private List<Position> getPoints(final List<String> lines) {
        final List<Position> points = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                final char c = line.charAt(j);
                if (Character.isDigit(c)) {
                    points.add(new Position(j, i, c - '0'));
                }
            }

        }
        return points;
    }

    private char[][] getGrid(final List<String> lines) {
        final char[][] grid = new char[lines.get(0).length()][lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                final char c = line.charAt(j);
                grid[j][i] = c;
            }

        }
        return grid;
    }

    private void getDistances(final List<Position> points, final char[][] grid) {
        for (int i = 0; i < points.size(); i++) {
            final Position p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                final Position p2 = points.get(j);
                final int distance = dijkstra(p1, p2, grid);
                p1.addDistanceTo(p2, distance);
                p2.addDistanceTo(p1, distance);
            }
        }
    }

    private void getPermutations(final List<Position> original, final int element, final List<List<Position>> perms) {
        for (int i = element; i < original.size(); i++) {
            java.util.Collections.swap(original, i, element);
            getPermutations(new ArrayList<>(original), element + 1, perms);
            java.util.Collections.swap(original, element, i);
        }
        if (element == original.size() - 1) {
            perms.add(original);
        }
    }

    private List<List<Position>> getPermutations(final List<Position> points, final Position start) {
        points.remove(start);
        final List<List<Position>> permutations = new ArrayList<>();
        getPermutations(points, 0, permutations);
        return permutations;
    }

    private int getShortestDistance(final Position start, final List<List<Position>> permutations,
            final boolean returnToStart) {
        int minLength = Integer.MAX_VALUE;
        for (final List<Position> perm : permutations) {
            int length = 0;
            for (int i = 0; i < perm.size(); i++) {
                final Position p = perm.get(i);
                if (i == 0) {
                    length += start.getDistances().get(p);
                } else {
                    length += p.getDistances().get(perm.get(i - 1));
                }
            }
            if (returnToStart) {
                length += perm.get(perm.size() - 1).getDistances().get(start);
            }
            if (length < minLength) {
                minLength = length;
            }
        }
        return minLength;
    }

    public int runPart2(final List<String> lines) {
        final char[][] grid = getGrid(lines);
        final List<Position> points = getPoints(lines);
        getDistances(points, grid);
        final Position start = points.stream().filter(p -> p.val == 0).collect(Collectors.toList()).get(0);
        return getShortestDistance(start, getPermutations(points, start), true);
    }

    public int runPart1(final List<String> lines) {
        final char[][] grid = getGrid(lines);
        final List<Position> points = getPoints(lines);
        getDistances(points, grid);
        final Position start = points.stream().filter(p -> p.val == 0).collect(Collectors.toList()).get(0);
        return getShortestDistance(start, getPermutations(points, start), false);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day24().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day24().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
