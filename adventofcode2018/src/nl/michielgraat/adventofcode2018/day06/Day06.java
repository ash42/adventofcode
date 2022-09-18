package nl.michielgraat.adventofcode2018.day06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day06 {

    private static final String FILENAME = "day06.txt";

    private int manhattanDistance(int x1, int x2, int y1, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private List<Coordinate> getCoordinates(List<String> lines) {
        List<Coordinate> coordinates = new ArrayList<>();
        int nr = 1;
        for (String line : lines) {
            String[] parts = line.split(", ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            coordinates.add(new Coordinate(nr++, x, y));
        }
        return coordinates;
    }

    private int[][] getGrid(List<Coordinate> coordinates, int maxX, int maxY) {
        int max = Math.max(maxX, maxY) + 2;
        int[][] grid = new int[max][max];
        for (Coordinate c : coordinates) {
            grid[c.x][c.y] = c.n;
        }
        return grid;
    }

    private void removeInfiniteAreas(int[][] grid, int maxX, int maxY) {
        for (int edgeX = 0; edgeX <= maxX + 1; edgeX++) {
            int cur = grid[edgeX][0];
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
            int cur = grid[edgeX][maxY + 1];
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
            int cur = grid[0][edgeY];
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
            int cur = grid[maxX + 1][edgeY];
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

    private void fillGrid(int[][] grid, List<Coordinate> coordinates, int maxX, int maxY) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                int min = Integer.MAX_VALUE;
                for (Coordinate cur : coordinates) {
                    int distance = manhattanDistance(x, cur.x, y, cur.y);
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

    private int getLargestArea(int[][] grid, List<Coordinate> coordinates, int maxX, int maxY) {
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

    private int getAreaNearestToAll(int[][] grid, List<Coordinate> coordinates, int maxX, int maxY) {
        int total = 0;
        for (int x = 0; x <= maxX + 1; x++) {
            for (int y = 0; y <= maxY + 1; y++) {
                int totalDistance = 0;
                for (Coordinate c : coordinates) {
                    int distance = manhattanDistance(x, c.x, y, c.y);
                    totalDistance += distance;
                }
                if (totalDistance < 10000) {
                    total++;
                }
            }
        }
        return total;
    }

    public int runPart2(List<String> lines) {
        List<Coordinate> coordinates = getCoordinates(lines);
        int maxX = coordinates.stream().max(Comparator.comparing(c -> c.x)).get().x;
        int maxY = coordinates.stream().max(Comparator.comparing(c -> c.y)).get().y;
        int[][] grid = getGrid(coordinates, maxX, maxY);
        return getAreaNearestToAll(grid, coordinates, maxX, maxY);
    }

    public int runPart1(List<String> lines) {
        List<Coordinate> coordinates = getCoordinates(lines);
        int maxX = coordinates.stream().max(Comparator.comparing(c -> c.x)).get().x;
        int maxY = coordinates.stream().max(Comparator.comparing(c -> c.y)).get().y;
        int[][] grid = getGrid(coordinates, maxX, maxY);
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

    public Coordinate(int n, int x, int y) {
        this.n = n;
        this.x = x;
        this.y = y;
    }
}