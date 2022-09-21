package nl.michielgraat.adventofcode2018.day06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day06 {

    private static final String FILENAME = "day06.txt";

    private int manhattanDistance(final int x1, final int x2, final int y1, final int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private List<Coordinate> getCoordinates(final List<String> lines) {
        final List<Coordinate> coordinates = new ArrayList<>();
        int nr = 1;
        for (final String line : lines) {
            final String[] parts = line.split(", ");
            final int x = Integer.parseInt(parts[0]);
            final int y = Integer.parseInt(parts[1]);
            coordinates.add(new Coordinate(nr++, x, y));
        }
        return coordinates;
    }

    private int[][] getGrid(final List<Coordinate> coordinates, final int maxX, final int maxY) {
        final int max = Math.max(maxX, maxY) + 2;
        final int[][] grid = new int[max][max];
        for (final Coordinate c : coordinates) {
            grid[c.x][c.y] = c.n;
        }
        return grid;
    }

    private void removeInfiniteAreas(final int[][] grid, final int maxX, final int maxY) {
        for (int edgeX = 0; edgeX <= maxX + 1; edgeX++) {
            final int cur = grid[edgeX][0];
            if (cur != 0) {
                for (int y = 0; y <= maxY + 1; y++) {
                    for (int x = 0; x <= maxX + 1; x++) {
                        if (grid[x][y] == cur) {
                            grid[x][y] = 0;
                        }
                    }
                }
            }
        }
        for (int edgeX = 0; edgeX <= maxX + 1; edgeX++) {
            final int cur = grid[edgeX][maxY + 1];
            if (cur != 0) {
                for (int y = 0; y <= maxY + 1; y++) {
                    for (int x = 0; x <= maxX + 1; x++) {
                        if (grid[x][y] == cur) {
                            grid[x][y] = 0;
                        }
                    }
                }
            }
        }
        for (int edgeY = 0; edgeY <= maxY + 1; edgeY++) {
            final int cur = grid[0][edgeY];
            if (cur != 0) {
                for (int y = 0; y <= maxY + 1; y++) {
                    for (int x = 0; x <= maxX + 1; x++) {
                        if (grid[x][y] == cur) {
                            grid[x][y] = 0;
                        }
                    }
                }
            }
        }
        for (int edgeY = 0; edgeY <= maxY + 1; edgeY++) {
            final int cur = grid[maxX + 1][edgeY];
            if (cur != 0) {
                for (int y = 0; y <= maxY + 1; y++) {
                    for (int x = 0; x <= maxX + 1; x++) {
                        if (grid[x][y] == cur) {
                            grid[x][y] = 0;
                        }
                    }
                }
            }
        }
    }

    private void fillGrid(final int[][] grid, final List<Coordinate> coordinates, final int maxX, final int maxY) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                int min = Integer.MAX_VALUE;
                for (final Coordinate cur : coordinates) {
                    final int distance = manhattanDistance(x, cur.x, y, cur.y);
                    if (distance < min) {
                        grid[x][y] = cur.n;
                        min = distance;
                    } else if (distance == min) {
                        grid[x][y] = 0;
                    }
                }
            }
        }
        removeInfiniteAreas(grid, maxX, maxY);
    }

    private int getLargestArea(final int[][] grid, final List<Coordinate> coordinates, final int maxX, final int maxY) {
        int max = 0;
        for (int c = 1; c <= coordinates.size(); c++) {
            int total = 0;
            for (int x = 0; x <= maxX; x++) {
                for (int y = 0; y <= maxY; y++) {
                    if (grid[x][y] == c) {
                        total++;
                    }
                }
            }
            if (total > max) {
                max = total;
            }
        }
        return max;
    }

    private int getAreaNearestToAll(final int[][] grid, final List<Coordinate> coordinates, final int maxX,
            final int maxY) {
        int total = 0;
        for (int x = 0; x <= maxX + 1; x++) {
            for (int y = 0; y <= maxY + 1; y++) {
                int totalDistance = 0;
                for (final Coordinate c : coordinates) {
                    final int distance = manhattanDistance(x, c.x, y, c.y);
                    totalDistance += distance;
                }
                if (totalDistance < 10000) {
                    total++;
                }
            }
        }
        return total;
    }

    public int runPart2(final List<String> lines) {
        final List<Coordinate> coordinates = getCoordinates(lines);
        final int maxX = coordinates.stream().max(Comparator.comparing(c -> c.x)).get().x;
        final int maxY = coordinates.stream().max(Comparator.comparing(c -> c.y)).get().y;
        final int[][] grid = getGrid(coordinates, maxX, maxY);
        return getAreaNearestToAll(grid, coordinates, maxX, maxY);
    }

    public int runPart1(final List<String> lines) {
        final List<Coordinate> coordinates = getCoordinates(lines);
        final int maxX = coordinates.stream().max(Comparator.comparing(c -> c.x)).get().x;
        final int maxY = coordinates.stream().max(Comparator.comparing(c -> c.y)).get().y;
        final int[][] grid = getGrid(coordinates, maxX, maxY);
        fillGrid(grid, coordinates, maxX, maxY);
        return getLargestArea(grid, coordinates, maxX, maxY);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day06().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day06().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}

class Coordinate {
    int n;
    int x;
    int y;

    public Coordinate(final int n, final int x, final int y) {
        this.n = n;
        this.x = x;
        this.y = y;
    }
}