package nl.michielgraat.adventofcode2024.day06;

import java.util.List;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day06 extends AocSolver {

    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    protected Day06(final String filename) {
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

    private boolean hasLeftArea(final int x, final int y, final char[][] grid) {
        return x < 0 || x >= grid[0].length || y < 0 || y >= grid.length;
    }

    private Position getGuardPosition(final char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '^') {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    private int getNextY(final int y, final int dir) {
        switch (dir) {
            case LEFT:
            case RIGHT:
                return y;
            case UP:
                return y - 1;
            default:
                return y + 1;
        }
    }

    private int getNextX(final int x, final int dir) {
        switch (dir) {
            case UP:
            case DOWN:
                return x;
            case LEFT:
                return x - 1;
            default:
                return x + 1;
        }
    }

    private boolean isNextPosObstruction(final int x, final int y, final char[][] grid) {
        return grid[y][x] == '#';
    }

    private void markPath(final int x, final int y, int dir, final char[][] grid) {
        grid[y][x] = 'X';
        int nextX = getNextX(x, dir);
        int nextY = getNextY(y, dir);
        if (hasLeftArea(nextX, nextY, grid)) {
            return;
        }
        while (isNextPosObstruction(nextX, nextY, grid)) {
            dir = (dir + 1) % 4;
            nextX = getNextX(x, dir);
            nextY = getNextY(y, dir);
        }
        markPath(nextX, nextY, dir, grid);
    }

    private int countDistinctPositions(final char[][] grid) {
        int total = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 'X')
                    total++;
            }
        }
        return total;
    }

    private boolean hasCycle(final int x, final int y, int d, final char[][] grid, final boolean[][][] path) {
        if (path[x][y][d]) {
            return true;
        } else {
            path[x][y][d] = true;
            int nextX = getNextX(x, d);
            int nextY = getNextY(y, d);
            if (hasLeftArea(nextX, nextY, grid)) {
                return false;
            }
            while (isNextPosObstruction(nextX, nextY, grid)) {
                d = (d + 1) % 4;
                nextX = getNextX(x, d);
                nextY = getNextY(y, d);
            }
            return hasCycle(nextX, nextY, d, grid, path);
        }
    }

    private int countCycleObstructions(final char[][] grid, final char[][] path, final int startX, final int startY) {
        int total = 0;
        for (int y = 0; y < path.length; y++) {
            for (int x = 0; x < path[y].length; x++) {
                if (path[y][x] == 'X') {
                    grid[y][x] = '#';
                    if (hasCycle(startX, startY, 0, grid, new boolean[grid[0].length][grid.length][4])) {
                        total++;
                    }
                    grid[y][x] = '.';
                }
            }
        }

        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] grid = getGrid(input);
        final char[][] pathGrid = getGrid(input);
        final Position guard = getGuardPosition(pathGrid);
        markPath(guard.x(), guard.y(), 0, pathGrid);
        return String.valueOf(countCycleObstructions(grid, pathGrid, guard.x(), guard.y()));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] grid = getGrid(input);
        final Position guard = getGuardPosition(grid);
        markPath(guard.x(), guard.y(), 0, grid);
        return String.valueOf(countDistinctPositions(grid));

    }

    public static void main(final String... args) {
        new Day06("day06.txt");
    }
}

record Position(int x, int y) {
}
