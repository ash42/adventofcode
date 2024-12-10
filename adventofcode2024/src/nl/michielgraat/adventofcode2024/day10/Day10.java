package nl.michielgraat.adventofcode2024.day10;

import java.util.List;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day10 extends AocSolver {

    protected Day10(final String filename) {
        super(filename);
    }

    private int[][] getGrid(final List<String> input) {
        final int[][] grid = new int[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '.') {
                    grid[y][x] = -1;
                } else {
                    grid[y][x] = line.charAt(x) - '0';
                }
            }
        }
        return grid;
    }

    private boolean inBounds(final int x, final int y, final int[][] grid) {
        return x >= 0 && y >= 0 && y < grid.length && x < grid[y].length;
    }

    private int calculateScore(final int x, final int y, final int[][] grid, final boolean[][] found,
            final boolean part1) {
        if (grid[y][x] == 9) {
            if (!part1 || !found[y][x]) {
                found[y][x] = true;
                return 1;
            } else {
                return 0;
            }
        } else {
            int total = 0;
            final int next = grid[y][x] + 1;
            if (inBounds(x + 1, y, grid) && grid[y][x + 1] == next) {
                total += calculateScore(x + 1, y, grid, found, part1);
            }
            if (inBounds(x - 1, y, grid) && grid[y][x - 1] == next) {
                total += calculateScore(x - 1, y, grid, found, part1);
            }
            if (inBounds(x, y + 1, grid) && grid[y + 1][x] == next) {
                total += calculateScore(x, y + 1, grid, found, part1);
            }
            if (inBounds(x, y - 1, grid) && grid[y - 1][x] == next) {
                total += calculateScore(x, y - 1, grid, found, part1);
            }
            return total;
        }
    }

    private int calculateTotalScore(final int[][] grid, final boolean part1) {
        int total = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 0) {
                    total += calculateScore(x, y, grid, new boolean[grid.length][grid[0].length], part1);
                }
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final int[][] grid = getGrid(input);
        return String.valueOf(calculateTotalScore(grid, false));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final int[][] grid = getGrid(input);
        return String.valueOf(calculateTotalScore(grid, true));
    }

    public static void main(final String... args) {
        new Day10("day10.txt");
    }
}
