package nl.michielgraat.adventofcode2021.day15;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day15 {

    private static final String FILENAME = "day15.txt";

    private int[][] parseGrid(final List<String> lines) {
        final int[][] grid = new int[lines.size()][lines.get(0).length()];

        int j = 0;
        for (final String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                grid[j][i] = Integer.parseInt(String.valueOf(line.charAt(i)));
            }
            j++;
        }
        return grid;
    }

    private int[][] createLargerGrid(final List<String> lines) {
        final int[][] initial = parseGrid(lines);
        final int[][] larger = new int[initial.length * 5][initial[0].length * 5];

        for (int y = 0; y < larger.length; y++) {
            for (int x = 0; x < larger.length; x++) {
                final int initialValue = initial[y % initial.length][x % initial[0].length];
                final int addedFromX = x / initial[0].length;
                final int addedFromY = y / initial.length;
                int val = initialValue + addedFromX + addedFromY;
                val = (val % 10) + val / 10;
                larger[y][x] = val;
            }
        }
        return larger;
    }

    private int[][] createInitialLengthsGrid(final int[][] grid, final int initialX, final int initialY) {
        final int[][] initial = new int[grid.length][grid[0].length];
        for (int i = 0; i < initial.length; i++) {
            for (int j = 0; j < initial[0].length; j++) {
                initial[i][j] = Integer.MAX_VALUE;
            }
        }
        initial[initialY][initialX] = 0;
        return initial;
    }

    private boolean isValid(final Coordinate c, final int[][] grid) {
        return c.x >= 0 && c.y >= 0 && c.x < grid[0].length && c.y < grid.length;
    }

    private PriorityQueue<Coordinate> createPriorityQueue(final Coordinate start, final int[][] grid) {
        final PriorityQueue<Coordinate> queue = new PriorityQueue<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (x == start.x && y == start.y) {
                    queue.add(start);
                } else {
                    queue.add(new Coordinate(x, y, Integer.MAX_VALUE));
                }
            }
        }
        return queue;
    }

    private int dijkstra(Coordinate current, final int[][] grid) {
        final int destX = grid[0].length - 1;
        final int destY = grid.length - 1;
        final PriorityQueue<Coordinate> queue = createPriorityQueue(current, grid);
        final int[][] distances = createInitialLengthsGrid(grid, current.x, current.y);
        while (!queue.isEmpty()) {
            final int x = current.x;
            final int y = current.y;
            final int currentDistance = current.distance;
            final List<Coordinate> neighbours = new ArrayList<>();
            neighbours.add(new Coordinate(x - 1, y));
            neighbours.add(new Coordinate(x + 1, y));
            neighbours.add(new Coordinate(x, y - 1));
            neighbours.add(new Coordinate(x, y + 1));

            for (final Coordinate neighbour : neighbours) {
                if (isValid(neighbour, grid) && queue.contains(neighbour)) {
                    final int distance = currentDistance + grid[neighbour.y][neighbour.x];
                    if (distance < distances[neighbour.y][neighbour.x]) {
                        distances[neighbour.y][neighbour.x] = distance;
                        queue.remove(neighbour);
                        neighbour.distance = distance;
                        queue.add(neighbour);
                    }
                }
            }
            queue.remove(current);
            if (!queue.isEmpty()) {
                current = queue.poll();
            }
        }
        return distances[destY][destX];
    }

    private long runPart2(final List<String> lines) {
        final int[][] grid = createLargerGrid(lines);
        return dijkstra(new Coordinate(0, 0, 0), grid);
    }

    private long runPart1(final List<String> lines) {
        return dijkstra(new Coordinate(0, 0, 0), parseGrid(lines));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day15().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day15().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Coordinate implements Comparable<Coordinate> {
    int x;
    int y;
    int distance = Integer.MAX_VALUE;

    Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    Coordinate(final int x, final int y, final int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Coordinate other = (Coordinate) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(final Coordinate o) {
        return this.distance - o.distance;
    }
}