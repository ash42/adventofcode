package nl.michielgraat.adventofcode2022.day14;

import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day14 extends AocSolver {

    private static final char AIR = '.';
    private static final char ROCK = '#';
    private static final char SAND = 'o';
    private static final char START = '+';

    private int getMaxY(final char[][] grid) {
        int maxY = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] != AIR && y > maxY) {
                    maxY = y;
                }
            }
        }
        return maxY;
    }

    private char[][] buildGrid(final List<String> input) {
        final char[][] grid = new char[300][1000];
        for (int y = 0; y < grid.length; y++) {
            Arrays.fill(grid[y], AIR);
        }
        grid[0][500] = START;
        for (final String line : input) {
            final String[] coordinates = line.split(" -> ");
            for (int i = 0; i < coordinates.length - 1; i++) {
                final int x1 = Integer.parseInt(coordinates[i].split(",")[0]);
                final int y1 = Integer.parseInt(coordinates[i].split(",")[1]);
                final int x2 = Integer.parseInt(coordinates[i + 1].split(",")[0]);
                final int y2 = Integer.parseInt(coordinates[i + 1].split(",")[1]);
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                        grid[y][x] = ROCK;
                    }
                }
            }
        }
        return grid;
    }

    private void addFloor(final char[][] grid, final int floorY) {
        for (int x = 0; x < grid[0].length; x++) {
            grid[floorY][x] = ROCK;
        }
    }

    private boolean isBlocked(final char[][] grid, final int x, final int y) {
        return grid[y][x] != AIR;
    }

    private boolean pour(final char[][] grid, final int x, final int y, final int maxY) {
        if (y >= maxY) {
            // Reached bottom, stop pouring.
            return false;
        }

        if (grid[y + 1][x] == AIR) {
            grid[y][x] = AIR;
            grid[y + 1][x] = SAND;
            return pour(grid, x, y + 1, maxY);
        }

        if (isBlocked(grid, x, y + 1)) {
            if (!isBlocked(grid, x - 1, y + 1)) {
                grid[y][x] = AIR;
                grid[y + 1][x - 1] = SAND;
                return pour(grid, x - 1, y + 1, maxY);
            } else if (!isBlocked(grid, x + 1, y + 1)) {
                grid[y][x] = AIR;
                grid[y + 1][x + 1] = SAND;
                return pour(grid, x + 1, y + 1, maxY);
            }
        }
        // If at (500,0) we reached the source, there is no more room to pour more sand,
        // stop pouring. Else continue.
        return !(x == 500 && y == 0);
    }

    protected Day14(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] grid = buildGrid(input);
        final int maxY = getMaxY(grid) + 2;
        addFloor(grid, maxY);
        int nrSand = 0;
        while (pour(grid, 500, 0, maxY)) {
            nrSand++;
        }
        //Add one for the source itself, as pour(500,0) is false.
        return String.valueOf(nrSand + 1);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] grid = buildGrid(input);
        final int maxY = getMaxY(grid);
        int nrSand = 0;
        while (pour(grid, 500, 0, maxY)) {
            nrSand++;
        }
        return String.valueOf(nrSand);
    }

    public static void main(final String... args) {
        new Day14("day14.txt");
    }
}
