package nl.michielgraat.adventofcode2022.day08;

import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day08 extends AocSolver {

    protected Day08(final String filename) {
        super(filename);
    }

    private int[][] buildGrid(final List<String> input) {
        final int[][] grid = new int[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = Character.getNumericValue(line.charAt(x));
            }
        }
        return grid;
    }

    private boolean isVisibleLeft(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        for (int x2 = x - 1; x2 >= 0; x2--) {
            if (grid[y][x2] >= height) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleRight(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        for (int x2 = x + 1; x2 < grid[0].length; x2++) {
            if (grid[y][x2] >= height) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleUp(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        for (int y2 = y - 1; y2 >= 0; y2--) {
            if (grid[y2][x] >= height) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisibleDown(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        for (int y2 = y + 1; y2 < grid[0].length; y2++) {
            if (grid[y2][x] >= height) {
                return false;
            }
        }
        return true;
    }

    private boolean isVisible(final int x, final int y, final int[][] grid) {
        if (x == 0 || y == 0 || x == grid[0].length - 1 || y == grid.length - 1) {
            return true;
        } else {
            return isVisibleLeft(x, y, grid) || isVisibleRight(x, y, grid) || isVisibleUp(x, y, grid)
                    || isVisibleDown(x, y, grid);
        }
    }

    private int getNrOfVisibleTrees(final int[][] grid) {
        int total = 2 * (grid.length - 2) + 2 * grid[0].length;
        for (int y = 1; y < grid.length - 1; y++) {
            for (int x = 1; x < grid[0].length - 1; x++) {
                if (isVisible(x, y, grid)) {
                    total++;
                }
            }
        }
        return total;
    }

    private int getViewingDistanceLeft(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        int total = 0;
        for (int x2 = x - 1; x2 >= 0; x2--) {
            total++;
            if (grid[y][x2] >= height) {
                return total;
            }
        }
        return total;
    }

    private int getViewingDistanceRight(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        int total = 0;
        for (int x2 = x + 1; x2 < grid[0].length; x2++) {
            total++;
            if (grid[y][x2] >= height) {
                return total;
            }
        }
        return total;
    }

    private int getViewingDistanceUp(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        int total = 0;
        for (int y2 = y - 1; y2 >= 0; y2--) {
            total++;
            if (grid[y2][x] >= height) {
                return total;
            }
        }
        return total;
    }

    private int getViewingDistanceDown(final int x, final int y, final int[][] grid) {
        final int height = grid[y][x];
        int total = 0;
        for (int y2 = y + 1; y2 < grid[0].length; y2++) {
            total++;
            if (grid[y2][x] >= height) {
                return total;
            }
        }
        return total;
    }

    private int getHighestViewingDistance(final int[][] grid) {
        int highest = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                highest = Math.max(highest, getViewingDistanceLeft(x, y, grid) * getViewingDistanceRight(x, y, grid)
                        * getViewingDistanceUp(x, y, grid) * getViewingDistanceDown(x, y, grid));
            }
        }
        return highest;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getHighestViewingDistance(buildGrid(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getNrOfVisibleTrees(buildGrid(input)));
    }

    public static void main(final String... args) {
        new Day08("day08.txt");
    }
}
