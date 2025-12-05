package nl.michielgraat.adventofcode2025.day04;

import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day04 extends AocSolver {
    
    protected Day04(final String filename) {
        super(filename);
    }

    private int[][] parseGrid(final List<String> input) {
        final int[][] grid = new int[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x) == '@' ? 1 : 0;
            }
        }
        return grid;
    }
    private boolean hasRoll(final int x, final int y, final int[][] grid) {
        return x >= 0 && y >= 0 && y < grid.length && x < grid[y].length && grid[y][x] == 1;
    }

    private int countRollsAround(final int x, final int y, final int[][] grid) {
        int count = 0;
        count += hasRoll(x - 1, y, grid) ? 1 : 0;
        count += hasRoll(x + 1, y, grid) ? 1 : 0;
        count += hasRoll(x, y - 1, grid) ? 1 : 0;
        count += hasRoll(x, y + 1, grid) ? 1 : 0;
        count += hasRoll(x - 1, y - 1, grid) ? 1 : 0;
        count += hasRoll(x - 1, y + 1, grid) ? 1 : 0;
        count += hasRoll(x + 1, y - 1, grid) ? 1 : 0;
        count += hasRoll(x + 1, y + 1, grid) ? 1 : 0;
        return count;
    }

    private int countRolls(final int[][] grid) {
        int total = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 1) {
                    if (countRollsAround(x, y, grid) < 4) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    private int[][] removeRolls(final int[][] grid) {
        final int[][] newGrid = new int[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 1) {
                    if (countRollsAround(x, y, grid) < 4) {
                        newGrid[y][x] = 0;
                    } else {
                        newGrid[y][x] = 1;
                    }
                } else {
                    newGrid[y][x] = 0;
                }
            }
        }
        return newGrid;
    }

    private int getNrOfRemovableRolls(int[][] grid) {
        int total = 0;
        int removed = 0;
        int nrLoops = 0;
        do {
            nrLoops++;
            final int nrRolls = Arrays.stream(grid).flatMapToInt(x -> Arrays.stream(x)).filter(i -> i == 1).sum();
            grid = removeRolls(grid);
            removed = nrRolls - Arrays.stream(grid).flatMapToInt(x -> Arrays.stream(x)).filter(i -> i == 1).sum();
            total += removed;
        } while (removed != 0);
        System.out.println(nrLoops);
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getNrOfRemovableRolls(parseGrid(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(countRolls(parseGrid(input)));
    }

    public static void main(final String... args) {
        new Day04("day04.txt");
    }
}
